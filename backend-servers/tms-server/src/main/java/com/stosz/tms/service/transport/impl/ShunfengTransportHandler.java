package com.stosz.tms.service.transport.impl;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.enums.PayTypeEnum;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.model.shunfeng.AddedService;
import com.stosz.tms.model.shunfeng.Cargo;
import com.stosz.tms.model.shunfeng.Cargos;
import com.stosz.tms.model.shunfeng.Order;
import com.stosz.tms.service.transport.AbstractPlaceOrderTransportHandler;
import com.stosz.tms.utils.DecimalUtils;
import com.stosz.tms.utils.HttpClientUtils;
import com.stosz.tms.utils.JsonUtils;
import com.stosz.tms.utils.shunfeng.ShunfengUtil;

/**
 * 顺丰 物流接口
 * xml传递
 * @author xiepengcheng
 * @version [1.0,2017年12月11日]
 */
@Component
public class ShunfengTransportHandler extends AbstractPlaceOrderTransportHandler {

	private Logger logger = LoggerFactory.getLogger(ShunfengTransportHandler.class);
	private static final String SUCCESS = "OK";//接口成功响应
	private static final String ERROR = "ERR";//接口失败响应

	// todo 测试报文请求头部
//	private static final String HEAD = "<?xml version='1.0' encoding='UTF-8'?>\n<Request service=\"OrderService\" lang=\"zh-CN\">\n<Head>BSPdevelop</Head><Body>";
	//todo 生产报文请求头部
	private static final String HEAD = "<?xml version='1.0' encoding='UTF-8'?>\n<Request service=\"OrderService\" lang=\"zh-CN\">\n<Head>7550026054</Head><Body>";

	//报文请求尾部
	private static final String END = "</Body>\n</Request>";

	@Override
	public TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception {
		TransportOrderResponse response = new TransportOrderResponse(true);
		String requestBody = null;
		String responseBody = null;
		String xmlstr = null;
		String verfyCode = null;
		try{
			xmlstr = this.getRequestXmlStr(orderRequest, extendInfo);
			verfyCode = this.generateVerfyCode(xmlstr,extendInfo);

			Map<String, Object> param = new HashMap();
			param.put("xml",xmlstr);
			param.put("verifyCode",verfyCode);
			requestBody = JsonUtils.toJson(param);

			responseBody = HttpClientUtils.doPost(extendInfo.getInterfaceUrl(), param, "UTF-8");
			Map<String,String> result = this.resolveResponseToMap(responseBody);

			if(result.get("status").equals(SUCCESS)){
				response.setCode(TransportOrderResponse.SUCCESS);
				response.setTrackingNo(result.get("result")+"");
			}else {
				response.setCode(TransportOrderResponse.FAIL);
				response.setErrorMsg(result.get("result"));
			}
		}catch (Exception e){
			logger.info("ShunfengTransportHandler code={},intefaceUrl={},Exception={}", getCode(), extendInfo.getInterfaceUrl(), e);
		}finally {
			logService.addLog(getCode(),orderRequest.getOrderNo(), extendInfo.getInterfaceUrl(), requestBody, responseBody);
		}

		return response;
	}

	/**
	 * 生成xml报文
	 */
	private String getRequestXmlStr(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
        String xmlstr = null;
        Order order = new Order();
		Cargos cargos = new Cargos();

		List<Cargo> list = new ArrayList<>();
		AddedService addedService = new AddedService();
        try{
			OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();

            order.setOrderid(orderRequest.getOrderNo());//客户订单号
            order.setJ_company(extendInfo.getSender());//寄件公司名称
            order.setJ_contact(extendInfo.getSenderContactName());//寄件公司联系人
            order.setJ_tel(extendInfo.getSenderTelphone());//寄件方联系人电话
            order.setJ_mobile(extendInfo.getSenderTelphone());//寄件方联系人手机
            order.setJ_shippercode("CN");//TODO 寄件方国家或城市代码
            order.setJ_country(extendInfo.getSenderCountry());// 寄方国家
            order.setJ_province(extendInfo.getSenderProvince());// 寄件方省
            order.setJ_city(extendInfo.getSenderCity());//寄件方城市
            order.setJ_county(extendInfo.getSenderTown());// 区
			order.setJ_address(extendInfo.getSenderAddress());//寄件方详细地址
            order.setJ_post_code(extendInfo.getSenderZipcode());//寄件方邮编
            order.setD_deliverycode(orderLinkDto.getCountryCode());//到件方代码
            order.setD_country(orderLinkDto.getCountry());//到方国家
			order.setD_contact(orderLinkDto.getLastName()+orderLinkDto.getFirstName());//到方联系人
			order.setD_tel(orderLinkDto.getTelphone());//到方联系电话
			order.setD_mobile(orderLinkDto.getTelphone());//手机号
			order.setD_province(orderLinkDto.getProvince());//到方省
			order.setD_city(orderLinkDto.getCity());//到方市
			order.setD_county(orderLinkDto.getArea());//到方区
			order.setD_address(orderLinkDto.getAddress());//到方地址
			order.setD_post_code(orderLinkDto.getZipcode());//到方邮编
//			order.setCustid("0650000001");// 顺丰月结卡号
			order.setPay_method(PayTypeEnum.sendpay.getType());//付款方式
			order.setParcel_quantity(1);//包裹数
			order.setCargo_total_weight(1d);//重量
			order.setDeclared_value(new BigDecimal(DecimalUtils.formateDecimal(orderRequest.getOrderAmount(), "#.###")));
			order.setDeclared_value_currency("USD");
			order.setRemark("test remark");
			for(TransportOrderDetail detail:orderRequest.getOrderDetails()){
				Cargo cargo = new Cargo();
				cargo.setName(orderRequest.getRemark());//获取名称
				cargo.setAmount(new BigDecimal(DecimalUtils.formateDecimal(detail.getTotalAmount(), "#.###")));
				cargo.setCount(detail.getOrderDetailQty());
				cargo.setUnit("box");//单位
				cargo.setWeight(1d);//重量
				cargo.setCurrency("USD");//币种
				cargo.setSource_area("CHINA");//原产国
				list.add(cargo);
			}

			addedService.setName("COD");//TODO 增值服务 ？
			addedService.setValue("3.33");//TODO 金额
			addedService.setValue1("11111");//TODO 代收货款卡号

			cargos.setCargos(list);
			order.setCargos(cargos);
			order.setAddedService(addedService);

			StringWriter writer = new StringWriter();
			JAXBContext jc = JAXBContext.newInstance(Order.class);
			Marshaller ma = jc.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去掉生成xml的默认报文头
			ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ma.marshal(order, writer);
			StringBuilder sb = new StringBuilder();
			sb.append(HEAD);
			sb.append(writer.toString());
			sb.append(END);
			String returnStr = sb.toString().replace("<Cargos>","").replace("</Cargos>","");
			return returnStr;
        }catch (Exception e){
            logger.trace(e.getMessage());
        }
        return null;
	}

	/**
	 * 生成验证码
	 * @return
	 */
	private static String generateVerfyCode(String xmlstr,ShippingExtend extendInfo){
		String verifyCode = ShunfengUtil.md5EncryptAndBase64(xmlstr + extendInfo.getMd5Key());
		return verifyCode;
	}

	@Override
	protected void validate(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		Assert.notNull(extendInfo, "配置信息不能为空");
		Assert.hasText(extendInfo.getInterfaceUrl(), "接口地址为空,请检查");
		Assert.hasText(extendInfo.getSender(), "寄件公司不能为空");
		Assert.hasText(extendInfo.getSenderContactName(), "寄件联系人不能为空");
		Assert.hasText(extendInfo.getSenderTelphone(), "寄件联系人电话不能为空");
		Assert.hasText(extendInfo.getSenderAddress(), "寄件地址不能为空");
		Assert.hasText(extendInfo.getSenderTown(), "寄件区/县不能为空");
		Assert.hasText(extendInfo.getSenderProvince(), "寄件省份不能为空");
		Assert.hasText(extendInfo.getSenderCountry(), "寄件国家不能为空");
		Assert.hasText(extendInfo.getSenderZipcode(), "寄件邮编不能为空");
		Assert.hasText(extendInfo.getMd5Key(), "秘钥不能为空");

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Assert.notNull(orderLinkDto, "收件人信息不能为空");
		Assert.notNull(orderLinkDto.getCountryCode(), "收件人国家编码不能为空");
		Assert.notNull(orderLinkDto.getFirstName(), "收件人姓不能为空");
		Assert.notNull(orderLinkDto.getLastName(), "收件人名不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.hasText(orderLinkDto.getCity(), "收件人城市不能为空");
		Assert.hasText(orderLinkDto.getProvince(), "收件人省份不能为空");
		Assert.hasText(orderLinkDto.getCountry(), "收件人国家不能为空");
		Assert.hasText(orderLinkDto.getZipcode(), "收件人邮编不能为空");

		Assert.notNull(orderRequest, "物流下单请求对象不能为空");
		Assert.notNull(orderRequest.getPayState(), "订单状态不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");
		Assert.hasText(orderRequest.getOrderNo(), "订单号不能为空");

		Assert.notEmpty(orderRequest.getOrderDetails(), "订单详情列表不能为空");
		orderRequest.getOrderDetails().forEach(item -> {
			Assert.notNull(item.getTotalAmount(), "单项明细总价不能为空");
		});
	}


	@Override
	public HandlerTypeEnum getCode() {
		return HandlerTypeEnum.SHUNFENG;
	}

	public HashMap<String, String> resolveResponseToMap(String response) {
		Assert.notNull(response,"返回为空isNull");
		Assert.isTrue(!"".equals(response),"返回为空");
		HashMap<String, String> resultMap = new HashMap<>();
		SAXReader saxReader = new SAXReader();
		Map<String,Object> result = new HashMap<>();
		try {

			Document doc = DocumentHelper.parseText(response);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Element head = rootElt.element("Head");
			String headValue = head.getStringValue();
			if(headValue.equals(SUCCESS)){
				Element orderResponse = rootElt.element("Body").element("OrderResponse");
				Attribute mailnoAtrr = orderResponse.attribute("mailno");
				result.put("status",SUCCESS);
				result.put("result",mailnoAtrr.getStringValue());
			}else {
				Element error = rootElt.element("ERROR");
				result.put("status",ERROR);
				result.put("result",error.getStringValue());
			}
			return resultMap;
		} catch (DocumentException e) {
			logger.trace(e.getMessage());
		}
		return null;
	}



}
