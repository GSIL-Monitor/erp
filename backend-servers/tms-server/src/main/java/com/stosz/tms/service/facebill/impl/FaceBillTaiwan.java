package com.stosz.tms.service.facebill.impl;

import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.taiwan.LandingCode;
import com.stosz.tms.service.facebill.IFaceBillArrange;
import com.stosz.tms.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/24 14:18
 */
public class FaceBillTaiwan implements IFaceBillArrange {

    private Logger logger = LoggerFactory.getLogger(FaceBillTaiwan.class);

    @Autowired
    private IZoneService zoneService;

    @Override
    public ResultBean<HashMap<String, Object>> getFaceBill(ShippingParcel shippingParcel) {
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

    /**
     * 获取台湾的着陆码
     *
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
     *
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
}