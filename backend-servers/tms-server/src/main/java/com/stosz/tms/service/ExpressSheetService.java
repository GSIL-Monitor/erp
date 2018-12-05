package com.stosz.tms.service;

import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.tms.dto.Parameter;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.taiwan.LandingCode;
import com.stosz.tms.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 面单信息Service
 * @author feiheping
 * @version [1.0,2018年1月17日]
 */
@Service
public class ExpressSheetService {

	private Logger logger = LoggerFactory.getLogger(ExpressSheetService.class);

	@Autowired
	private IZoneService zoneService;

	private ShippingService shippingService;

	/**
	 * 针对物流不需要抛送订单的情况，整理面单信息
	 * TransportResponse Code=success,需要同步返回面单信息
	 * 如果获取面单信息失败，则更改响应Code 为success_after_notify,异步等待回写物流面单信息
	 * @param transportResponse
	 * @param shippingParcel
	 * @return
	 */
	public ResultBean<HashMap<String, Object>> assignSyncExpressSheet(ShippingParcel shippingParcel) {
		// 目前同步返回面单信息的，只有配送区域为台湾才需要特殊处理
		ResultBean<HashMap<String, Object>> resultBean = getTaiWanLandingCode(shippingParcel);
		return resultBean;
	}

	/**
	 * 针对物流需要抛送订单的情况，整理面单信息
	 * TransportResponse Code=success_after_notify,异步需要返回面单信息
	 */
	public ResultBean<HashMap<String, Object>> assignAsyncExpressSheet(ShippingParcel shippingParcel) {
		ResultBean<HashMap<String, Object>> resultBean = getTaiWanLandingCode(shippingParcel);
		if (!ResultBean.OK.equals(resultBean.getCode())) {
			return resultBean;
		}
		Shipping shipping = shippingService.queryShippingByWayId(shippingParcel.getShippingWayId());
		if (shipping == null) {
			resultBean.setCode(ResultBean.FAIL);
			resultBean.setDesc(String.format("物流方式：%s,对应的物流商不存在", shippingParcel.getShippingWayId()));
			return resultBean;
		}
		HashMap<String, Object> sheetDataMap = resultBean.getItem();
		String shippingCode = shipping.getShippingCode();
		String responseData = shippingParcel.getResponseData();
		// # TODO 目前只判断了NIM物流，带加入其它物流面单判断
		try {
			if (HandlerTypeEnum.NIM.code().equals(shippingCode)) {// 如果是NiM物流
				fillSheetByNimShipping(sheetDataMap, responseData);
			}
			resultBean.setCode(ResultBean.OK);
			resultBean.setDesc("面单信息获取成功");
		} catch (Exception e) {
			resultBean.setCode(ResultBean.FAIL);
			resultBean.setDesc(e.getMessage());
			logger.error("assignExpressSheet() 获取面单信息失败,shippingCode={} Exception={}", shippingCode, e);
		}
		return resultBean;
	}

	/**
	 * 获取NIM的面单信息
	 * @param resultBean
	 * @param sheetDataMap
	 * @param responseData
	 */
	private void fillSheetByNimShipping(HashMap<String, Object> sheetDataMap, String responseData) {
		String dest = null;
		String zone = null;
		String route = null;
		String bcNo = null;
		String bcRunNo = null;
		String nimZoneNumber = null;
		if (StringUtils.hasText(responseData)) {
			Parameter<String, Object> hashMap = JsonUtils.jsonToObject(responseData, Parameter.class);
			nimZoneNumber = hashMap.getString("nim_zone_number");
			dest = hashMap.getString("dest");
			zone = hashMap.getString("zone");
			route = hashMap.getString("route");
			if (hashMap.get("barcode") instanceof ArrayList) {
				ArrayList<HashMap<String, String>> barCodeList = (ArrayList<HashMap<String, String>>) hashMap.get("barcode");
				HashMap<String, String> bcCodeMap = barCodeList.get(0);
				bcNo = bcCodeMap.get("bc_no");
				bcRunNo = bcCodeMap.get("bc_run_no");
			}
		}
		sheetDataMap.put("GWF3", dest);// dest
		sheetDataMap.put("GWF5", nimZoneNumber);// 泰国NIM物流的nim_zone_number字段
		sheetDataMap.put("GWF8", zone);// GWF8 泰国NIM物流的zone字段
		sheetDataMap.put("GWF9", route); // GWF9 泰国NIM物流的route字段
		sheetDataMap.put("GWF6", bcNo);// GWF6 泰国NIM物流的bc_no字段
		sheetDataMap.put("GWF7", bcRunNo);// GWF7 泰国NIM物流的bc_run_no字段

		Assert.hasText(dest, "dest不允许为空");
		Assert.hasText(nimZoneNumber, "nim_zone_number不允许为空");
		Assert.hasText(bcNo, "bcNo不允许为空");
		Assert.hasText(bcRunNo, "bc_run_no 不允许为空");
	}

	/**
	 * 获取台湾的着陆码
	 * @param shippingParcel
	 * @return
	 */
	private ResultBean<HashMap<String, Object>> getTaiWanLandingCode(ShippingParcel shippingParcel) {
		ResultBean<HashMap<String, Object>> resultBean = new ResultBean<>();
		try {
			// 配送的区域ID
			Integer zoneId = shippingParcel.getZoneId();
			Zone zone = zoneService.getByCode("TW");
			if (zone == null) {
				resultBean.setCode(ResultBean.FAIL);
				resultBean.setDesc("台湾区域未配置");
				return resultBean;
			}
			HashMap<String, Object> sheetDataMap = new HashMap<>();
			if (zone.getId().equals(zoneId)) {// 如果是台湾区域，需要查询着陆码
				String detailAddress = getDetailAddress(shippingParcel);
				LandingCode landingCode = this.getLandingCodeByTaiWan(detailAddress);
				sheetDataMap.put("GWF2", landingCode.getTetradCode());
				sheetDataMap.put("GWF4", landingCode.getBrevityCode());

				if (!StringUtils.hasText(landingCode.getTetradCode())) {
					resultBean.setCode(ResultBean.FAIL);
					resultBean.setDesc("台湾着陆码获取失败");
					return resultBean;
				}
				if (!StringUtils.hasText(landingCode.getBrevityCode())) {
					resultBean.setCode(ResultBean.FAIL);
					resultBean.setDesc("台湾着陆二字码获取失败");
					return resultBean;
				}
			}
			resultBean.setCode(ResultBean.OK);
			resultBean.setItem(sheetDataMap);
		} catch (Exception e) {
			logger.error("getTaiWanLandingCode() parcelId={}, Exception={}", shippingParcel.getId(), e);
			resultBean.setCode(ResultBean.FAIL);
			resultBean.setDesc("获取台湾物流着陆码异常");
		}
		return resultBean;
	}

	/**
	 * 通过API获取台湾着陆码
	 * @param address
	 * @return
	 */
	private LandingCode getLandingCodeByTaiWan(String address) {
		LandingCode landingCode = new LandingCode();
		try {
			String value = URLEncoder.encode(address, "UTF-8");
			String response = HttpClientUtils.get("http://is1fax.hct.com.tw:8080/GET_ERSTNO/Addr_Compare_Simp.aspx?addr=" + value, "UTF-8");
			Document doc = Jsoup.parse(response);
			Elements elements = doc.getElementsByTag("body");
			if (ArrayUtils.isEmpty(elements)) {
				return landingCode;
			}
			String bodyContent = elements.get(0).html();
			String[] items = bodyContent.split("<br>");
			if (items.length > 2) {
				landingCode.setAddress(items[0].split("：")[1].trim());
				landingCode.setTetradCode(items[1].split("：")[1].trim());
				landingCode.setBrevityCode(items[2].split("：")[1].trim());
				if (items.length > 3) {
					landingCode.setBrevityAddress(items[3].split("：")[1].trim());
				}
			}

		} catch (Exception e) {
			logger.error("getLandingCodeByTaiWan() Exception={}", e);
		}
		return landingCode;
	}

	protected String getDetailAddress(ShippingParcel shippingParcel) {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.hasText(shippingParcel.getCountry()))
			builder.append(shippingParcel.getCountry());
		if (StringUtils.hasText(shippingParcel.getProvince()))
			builder.append(shippingParcel.getProvince());
		if (StringUtils.hasText(shippingParcel.getCity()))
			builder.append(shippingParcel.getCity());
		if (StringUtils.hasText(shippingParcel.getArea()))
			builder.append(shippingParcel.getArea());
		if (StringUtils.hasText(shippingParcel.getAddress()))
			builder.append(shippingParcel.getAddress());
		return builder.toString();
	}

	public static void main(String[] args) {
		// ExpressSheetService expressSheetService = new ExpressSheetService();
		// LandingCode landingCode =
		// expressSheetService.getLandingCodeByTaiWan("台湾省台北县基隆市大武仑工业区武训街12号");
		// logger.info(JsonUtil.toJson(landingCode));

		String json = "{\"bill_no\":\"6851801000027\",\"dest\":\"DC ชลบุรี\",\"nim_zone_number\":\"18\",\"barcode\":[{\"bc_no\":\"6841800000227\",\"bc_run_no\":\"1/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000234\",\"bc_run_no\":\"2/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000241\",\"bc_run_no\":\"3/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000258\",\"bc_run_no\":\"4/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000265\",\"bc_run_no\":\"5/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000272\",\"bc_run_no\":\"6/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000289\",\"bc_run_no\":\"7/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000296\",\"bc_run_no\":\"8/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000302\",\"bc_run_no\":\"9/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000319\",\"bc_run_no\":\"10/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000326\",\"bc_run_no\":\"11/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000333\",\"bc_run_no\":\"12/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000340\",\"bc_run_no\":\"13/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000357\",\"bc_run_no\":\"14/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000364\",\"bc_run_no\":\"15/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000371\",\"bc_run_no\":\"16/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000388\",\"bc_run_no\":\"17/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000395\",\"bc_run_no\":\"18/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000401\",\"bc_run_no\":\"19/20\",\"product_desc\":\"??\"},{\"bc_no\":\"6841800000418\",\"bc_run_no\":\"20/20\",\"product_desc\":\"??\"}],\"zone\":\"\",\"route\":\"\"}";
		Parameter<String, Object> hashMap = JsonUtils.jsonToObject(json, Parameter.class);
		String nimZoneNumber = hashMap.getString("nim_zone_number");
		String dest = hashMap.getString("dest");
		String zone = hashMap.getString("zone");
		String route = hashMap.getString("route");

		if (hashMap.get("barcode") instanceof ArrayList) {
			ArrayList<HashMap<String, String>> barCodeList = (ArrayList<HashMap<String, String>>) hashMap.get("barcode");
			HashMap<String, String> bcCodeMap = barCodeList.get(0);
			String bcNo = bcCodeMap.get("bc_no");
			String bcRunNo = bcCodeMap.get("bc_run_no");
		}


	}
}
