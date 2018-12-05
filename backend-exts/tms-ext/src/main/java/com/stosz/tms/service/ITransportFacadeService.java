package com.stosz.tms.service;

import com.stosz.plat.common.ResultBean;
import com.stosz.tms.dto.OrderStateInfo;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.dto.TransportResponse;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.ParcelOrderStateEnum;
import com.stosz.tms.vo.TrackingTaskAndDetailInfoVo;

public interface ITransportFacadeService {

	String url = "/tms/remote/ITransportFacadeService";

	/**
	 * 物流下单,创建包裹
	 * @param transportRequest
	 * @return
	 */
	 TransportResponse addTransportOrder(TransportRequest transportRequest);

	/**
	 * 订单状态发生变化,通知TMS是否抓取物流轨迹
	 * @return
	 */
	 TransportTrackResponse notifyLogisticsIsFetch(OrderStateInfo orderStateInfo);


	/**
	 * 查询物流任务以及物流任务详情列表信息
	 * @param orderId 订单ID
	 * @return 查找成功则返回信息，失败则返回null
	 */
	TrackingTaskAndDetailInfoVo queryTaskAndDetailInvo(int orderId);


	/**
	 * 根据订单ID更新包裹的订单状态
	 * @param orderId
	 * @param orderStateEnum
	 * @return
	 */
	ResultBean updateParcelStatusByOrderId(int orderId, ParcelOrderStateEnum orderStateEnum);

}
