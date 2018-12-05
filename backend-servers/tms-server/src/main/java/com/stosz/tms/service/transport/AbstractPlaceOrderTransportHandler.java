package com.stosz.tms.service.transport;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.stosz.plat.common.ResultBean;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.model.api.ShippingExtend;

@Service
public abstract class AbstractPlaceOrderTransportHandler extends AbstractTransportHandler {

	/**
	 * 物流下单接口
	 * @param orderRequest
	 * @return
	 * @throws Exception
	 */
	public abstract TransportOrderResponse transportPlaceOrder(TransportOrderRequest orderRequest, ShippingExtend extendInfo) throws Exception;


	/**
	 * 校验参数是否合法
	 * @param orderRequest
	 * @param extendInfo
	 * @return
	 */
	public final ResultBean<Boolean> validateParam(TransportOrderRequest orderRequest, ShippingExtend extendInfo) {
		ResultBean<Boolean> resultBean = new ResultBean<>();
		try {
			this.validate(orderRequest, extendInfo);
			resultBean.setCode(ResultBean.OK);
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " validateParam() Exception={}", e);
			String message = e.getMessage();
			resultBean.setCode(ResultBean.FAIL);
			resultBean.setDesc(message);
		}
		return resultBean;
	}

	/**
	 * 获取到收件人的详细地址
	 * @param linkDto
	 * @return
	 */
	protected String getDetailAddress(OrderLinkDto linkDto) {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.hasText(linkDto.getCountry()))
			builder.append(linkDto.getCountry());
		if (StringUtils.hasText(linkDto.getProvince()))
			builder.append(linkDto.getProvince());
		if (StringUtils.hasText(linkDto.getCity()))
			builder.append(linkDto.getCity());
		if (StringUtils.hasText(linkDto.getArea()))
			builder.append(linkDto.getArea());
		if (StringUtils.hasText(linkDto.getAddress()))
			builder.append(linkDto.getAddress());
		return builder.toString();
	}
	
	public String getReviceFrontName(OrderLinkDto orderLinkDto) {
		StringBuilder builder=new StringBuilder();
		if(StringUtils.hasText(orderLinkDto.getFirstName())) {
			builder.append(orderLinkDto.getFirstName());
		}
		if(StringUtils.hasText(orderLinkDto.getLastName())) {
			builder.append(orderLinkDto.getLastName());
		}
		return builder.toString();
	}
	
	public String getReviceSuffixName(OrderLinkDto orderLinkDto) {
		StringBuilder builder=new StringBuilder();
		if(StringUtils.hasText(orderLinkDto.getLastName())) {
			builder.append(orderLinkDto.getLastName());
		}
		if(StringUtils.hasText(orderLinkDto.getFirstName())) {
			builder.append(orderLinkDto.getFirstName());
		}
		return builder.toString();
	}

	/**
	 * 校验参数是否合法
	 * @param orderRequest
	 * @return
	 */
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

		OrderLinkDto orderLinkDto = orderRequest.getOrderLinkDto();
		Assert.notNull(orderLinkDto, "收件人信息不能为空");
		Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
		Assert.hasText(orderLinkDto.getCity(), "收件人城市不能为空");
		Assert.hasText(orderLinkDto.getProvince(), "收件人省份不能为空");
		Assert.hasText(orderLinkDto.getCountry(), "收件人国家不能为空");

		Assert.hasText(orderLinkDto.getFirstName(), "收件人姓氏不能为空");
		Assert.hasText(orderLinkDto.getLastName(), "收件人姓名不能为空");
		Assert.hasText(orderLinkDto.getAddress(), "收件人地址不能为空");
		Assert.hasText(orderLinkDto.getZipcode(), "收件人邮编不能为空");

		Assert.notNull(orderRequest, "物流下单请求对象不能为空");
		Assert.notNull(orderRequest.getPayState(), "订单状态不能为空");
		Assert.notNull(orderRequest.getOrderAmount(), "订单金额不能为空");

		Assert.notEmpty(orderRequest.getOrderDetails(), "订单详情列表不能为空");
		orderRequest.getOrderDetails().forEach(item -> {
			Assert.notNull(item.getSku(), "产品SKU不能为空");
			Assert.notNull(item.getOrderDetailQty(), "单项SKU件数不能为空");
			Assert.notNull(item.getPrice(), "单项SKU单价不能为空");
			Assert.notNull(item.getTotalAmount(), "单项SKU总价不能为空");
		});
	}

}
