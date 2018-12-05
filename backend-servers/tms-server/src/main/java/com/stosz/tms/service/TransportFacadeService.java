package com.stosz.tms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Sets;
import com.stosz.plat.common.ResultBean;
import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.ext.service.IProductService;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.chain.TransportHandlerChain;
import com.stosz.tms.chain.impl.CheckTransportHandler;
import com.stosz.tms.chain.impl.SpecialTransportFilter;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.OrderStateInfo;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderResponse;
import com.stosz.tms.dto.TransportRequest;
import com.stosz.tms.dto.TransportResponse;
import com.stosz.tms.dto.TransportTrackResponse;
import com.stosz.tms.enums.OrderStateEnum;
import com.stosz.tms.enums.ParcelOrderStateEnum;
import com.stosz.tms.enums.ParcelStateEnum;
import com.stosz.tms.enums.SyncStatusEnum;
import com.stosz.tms.mapper.ShippingStoreRelationMapper;
import com.stosz.tms.mapper.TrackingTaskDetailMapper;
import com.stosz.tms.mapper.TrackingTaskMapper;
import com.stosz.tms.model.AssignTransportRequest;
import com.stosz.tms.model.AssignTransportResponse;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingParcelDetail;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskDetail;
import com.stosz.tms.service.transport.TransportHandlerFactory;
import com.stosz.tms.vo.TrackingTaskAndDetailInfoVo;

@Service("transportFacadeService")
public class TransportFacadeService implements ITransportFacadeService {

    private Logger logger = LoggerFactory.getLogger(TransportFacadeService.class);

    @Autowired
    private TransportHandlerFactory handlerFactory;

    @Autowired
    private ShippingWayService shippingWayService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IStorehouseService storehouseService;

    @Autowired
    private ShippingParcelService shippingParcelService;

    @Autowired
    private TrackingTaskService trackingTaskService;

    @Autowired
    private PlaceOrderService placeOrderService;

    @Resource
    private ShippingStoreRelationMapper shippingStoreRelationMapper;

    @Resource
    private TrackingTaskMapper trackingTaskMapper;

    @Resource
    private TrackingTaskDetailMapper taskDetailMapper;

    @Resource
    private ExpressSheetService expressSheetService;

    @Override
    public TransportResponse addTransportOrder(TransportRequest request) {
        TransportResponse transportResponse = verifyTransportOrder(request);
        if (TransportResponse.FAIL.equals(transportResponse.getCode())) {
            return transportResponse;
        }
        int orderCount = shippingParcelService.queryParcelCount(request.getOrderId());
        if (orderCount > 0) {
            return transportResponse.fail("订单重复");
        }
        try {
            ShippingParcel shippingParcel = transferTransportParcel(request);
            shippingParcelService.addParcelAndDetails(shippingParcel);
            // 指派物流公司
            transportResponse = this.assignTransportShipping(request, shippingParcel);
            if (shippingParcel.getSyncStatus().equals(SyncStatusEnum.STAYSYNC.ordinal())) {// 如果需要推送信息到物流承运商
                ShippingParcel shippingParcelDb = shippingParcelService.getById(shippingParcel.getId());
                placeOrderService.asyncPlaceOrderHandle(shippingParcelDb);
            }
        } catch (Exception e) {
            logger.error("addTransportOrder() orderNo={},orderId={} Exception={}", request.getOrderNo(), request.getOrderId(), e);
            transportResponse.fail("包裹插入数据数据库失败");
        }
        return transportResponse;
    }

    /**
     * 指派物流公司 后修改数据库状态
     *
     * @param request
     * @return
     */
    public TransportResponse assignTransportShipping(TransportRequest request, ShippingParcel shippingParcel) {
        TransportResponse transportResponse = new TransportResponse();
        AssignTransportResponse response = this.assignTransportShippingWay(request, shippingParcel);
        // 更新包裹的指派状态
        ShippingParcel updateParcel = new ShippingParcel();
        updateParcel.setId(shippingParcel.getId());
        if (AssignTransportResponse.FAIL.equals(response.getCode())) {
            transportResponse.setCode(response.getCode());
            transportResponse.setErrorMsg(response.getErrorMsg());
            updateParcel.setParcelState(ParcelStateEnum.ASSIGN_FAIL.getType());
            updateParcel.setAssignErrorMsg(response.getErrorMsg());
        } else {
            transportResponse.setCode(response.getCode());
            transportResponse.setShippingCode(response.getShippingCode());
            transportResponse.setShippingName(response.getShippingName());
            transportResponse.setShippingWayId(response.getShippingWayId());
            transportResponse.setTrackingNo(response.getTrackingNo());

            updateParcel.setScheduleId(response.getScheduleId());
            updateParcel.setShippingWayId(response.getShippingWayId());
            updateParcel.setShippingAliasCode(response.getShippingCode());
            updateParcel.setShippingAliasName(response.getShippingName());
            updateParcel.setTrackNo(response.getTrackingNo());
            updateParcel.setCode(response.getLogisticsCode());
            updateParcel.setParcelState(ParcelStateEnum.ASSIGN_SUCCESS.getType());
            updateParcel.setAssignErrorMsg("");
            updateParcel.setProductType(response.getProductType().ordinal());

            if (AssignTransportResponse.SUCCESS.equals(response.getCode())) {// 无须同步
                updateParcel.setSyncStatus(SyncStatusEnum.DOWITHOUT.ordinal());
                shippingParcel.setSyncStatus(SyncStatusEnum.DOWITHOUT.ordinal());
            } else if (AssignTransportResponse.SUCCESS_AFTER_NOTIFY.equals(response.getCode())) {// 需要同步数据
                updateParcel.setSyncStatus(SyncStatusEnum.STAYSYNC.ordinal());
                shippingParcel.setSyncStatus(SyncStatusEnum.STAYSYNC.ordinal());
            }

            /**TransportResponse Code=success,需要同步返回面单信息
             * 如果获取面单信息失败，则更改响应Code 为success_after_notify,异步等待回写物流面单信息*/
            if (TransportResponse.SUCCESS.equals(transportResponse.getCode())) {
                ResultBean<HashMap<String, Object>> resultBean = expressSheetService.assignSyncExpressSheet(shippingParcel);
                if (ResultBean.OK.equals(resultBean.getCode())) {// 面单信息获取成功
                    updateParcel.setParcelState(ParcelStateEnum.PUSHORDER_SUCCSS.getType());// 推送订单成功
                    transportResponse.setSheetDataMap(resultBean.getItem());
                } else {
                    transportResponse.setCode(TransportResponse.SUCCESS_AFTER_NOTIFY);
                    transportResponse.setErrorMsg(resultBean.getDesc());

                    updateParcel.setParcelState(ParcelStateEnum.PUSHORDER_FAIL.getType());// 推送订单失败,面单信息缺失
                    updateParcel.setAssignErrorMsg(resultBean.getDesc());
                }
            }
        }
        shippingParcelService.updateSelective(updateParcel);
        return transportResponse;
    }

    /**
     * 获取面单信息
     *
     * @param shippingParcel
     * @return
     */
    public TransportResponse assignSyncSheet(ShippingParcel shippingParcel) {

        TransportResponse transportResponse = new TransportResponse();

        transportResponse.setShippingCode(shippingParcel.getShippingAliasCode());
        transportResponse.setShippingName(shippingParcel.getShippingAliasName());
        transportResponse.setShippingWayId(shippingParcel.getShippingWayId());
        transportResponse.setTrackingNo(shippingParcel.getTrackNo());
        /**TransportResponse Code=success,需要同步返回面单信息
         * 如果获取面单信息失败，则更改响应Code 为success_after_notify,异步等待回写物流面单信息*/
        if (TransportResponse.SUCCESS.equals(transportResponse.getCode())) {
            ResultBean<HashMap<String, Object>> resultBean = expressSheetService.assignSyncExpressSheet(shippingParcel);
            if (ResultBean.OK.equals(resultBean.getCode())) {// 面单信息获取成功
                shippingParcel.setParcelState(ParcelStateEnum.PUSHORDER_SUCCSS.getType());// 推送订单成功
                transportResponse.setSheetDataMap(resultBean.getItem());
            } else {
                transportResponse.setCode(TransportResponse.SUCCESS_AFTER_NOTIFY);
                transportResponse.setErrorMsg(resultBean.getDesc());

                shippingParcel.setParcelState(ParcelStateEnum.PUSHORDER_FAIL.getType());// 推送订单失败,面单信息缺失
                shippingParcel.setAssignErrorMsg(resultBean.getDesc());
            }
        }
        shippingParcelService.updateSelective(shippingParcel);
        return transportResponse;
    }

    /**
     * 指派物流公司
     *
     * @param request
     * @return
     */
    private AssignTransportResponse assignTransportShippingWay(TransportRequest request, ShippingParcel shippingParcel) {
        AssignTransportResponse transportResponse = new AssignTransportResponse();
        try {
            // 仓库ID
            Integer wareHouseId = request.getWarehouseId();
            // 区域ID
            Integer zoneId = request.getZoneId();
            // 查询仓库信息
            Wms wms = storehouseService.wmsTransferRequest(wareHouseId);
            if (wms == null) {
                logger.info("addTransportOrder() 指派物流失败,订单Id={},仓库ID={},区域Id={},仓库不存在", request.getOrderId(), wareHouseId, zoneId);
                return transportResponse.fail(String.format("仓库ID=%s,仓库不存在", wareHouseId));
            }
            request.setWarehouseZoneId(wms.getZoneId());
            // 查询产品的所有标签
            Set<String> skuSet = request.getOrderDetails().stream().map(TransportOrderDetail::getSku).collect(Collectors.toSet());
            Map<String, List<LabelObject>> productLables = productService.queryLabelBySkuList(new ArrayList<>(skuSet));

            // 根据仓库ID 区域ID 产品标签 获取到可用的物流方式
            ResultBean<List<ShippingStoreRel>> resultBean = shippingWayService.queryUsableShippingWay(request, productLables);
            if (!ResultBean.OK.equals(resultBean.getCode())) {
                return transportResponse.fail(resultBean.getDesc());
            }
            List<ShippingStoreRel> shippingWayList = resultBean.getItem();
            if (ArrayUtils.isEmpty(shippingWayList)) {
                logger.info("addTransportOrder() 指派物流失败,订单Id={},仓库ID={},区域Id={},未匹配上物流标签", request.getOrderId(), wareHouseId, zoneId);
                return transportResponse.fail(String.format("未匹配仓库Id=%s,区域ID=%s,未匹配上物流标签", wareHouseId, zoneId));
            }
            // 获取所有可用的物流方式 根据排程sorted排序,优先 1 仓库+物流方式匹配排程,
            // 2仓库+物流方式匹配排程模板,3任意仓+物流方式匹配排程,4任意仓+物流方式匹配排程模板
            List<ShippingStoreRel> shippingStoreRels = shippingWayService.queryAssignShipping(shippingWayList, wareHouseId);
            if (ArrayUtils.isEmpty(shippingStoreRels)) {
                logger.info("addTransportOrder() 指派物流失败,订单Id={},仓库ID={},区域Id={},物流方式未设置排程", request.getOrderId(), wareHouseId, zoneId);
                return transportResponse.fail(String.format("仓库Id=%s,区域ID=%s,对应的物流方式未设置排程", wareHouseId, zoneId));
            }
            // handler 处理链
            TransportHandlerChain transportHandlerChain = new TransportHandlerChain(shippingStoreRels, handlerFactory);

            // 用一个链式检查所有排程单次都已经满了后，需要清空，从新开始
            CheckTransportHandler checkTransportHandler = SpringContextHolder.getBean(CheckTransportHandler.class);
            checkTransportHandler.setShippingStoreRels(shippingStoreRels);
            transportHandlerChain.addFirstFilter(checkTransportHandler);

            // 特殊处理 优先指派
            SpecialTransportFilter thailandSpecialTransportHandler = SpringContextHolder.getBean(SpecialTransportFilter.class);
            thailandSpecialTransportHandler.setProductLables(productLables);
            thailandSpecialTransportHandler.setShippingStoreRels(shippingStoreRels);
            transportHandlerChain.addFirstFilter(thailandSpecialTransportHandler);

            // request
            AssignTransportRequest assignTransportRequest = new AssignTransportRequest();
            assignTransportRequest.setTransportRequest(request);
            transportResponse = transportHandlerChain.doAssignChain(assignTransportRequest);
        } catch (Exception e) {
            transportResponse.setCode(TransportResponse.FAIL);
            logger.error("assignTransportShippingWay() orderId={},orderNo={} Exception={}", request.getOrderId(), request.getOrderNo(), e);
        }
        return transportResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransportTrackResponse notifyLogisticsIsFetch(OrderStateInfo orderStateInfo) {
        final OrderStateEnum orderStateEnum = orderStateInfo.getOrderStateEnum();
        TransportTrackResponse response = new TransportTrackResponse();

        if (!OrderStateEnum.deliver.name().equals(orderStateEnum.display())) {
            logger.error("订单{}，状态处于{}，不能开启抓取轨迹", orderStateInfo.getOrderId(), orderStateInfo.getOrderStateEnum());
            response.setCode(TransportTrackResponse.FAIL);
            response.setErrorMsg("订单状态不为不可抓取轨迹");
        }
        response.setCode(TransportTrackResponse.SUCCESS);
        orderDeliverDispose(orderStateInfo);

        return response;
    }

    @Override
    public TrackingTaskAndDetailInfoVo queryTaskAndDetailInvo(int orderId) {
        TrackingTask paramBean = new TrackingTask();
        paramBean.setOrderId(orderId);

        final List<TrackingTask> trackingTasks = trackingTaskMapper.find(paramBean);

        if (CollectionUtils.isNullOrEmpty(trackingTasks))
            return null;

        final TrackingTask trackingTask = trackingTasks.get(0);

        final Integer trackingTaskId = trackingTask.getId();

        TrackingTaskDetail detailParamBean = new TrackingTaskDetail();
        detailParamBean.setTrackingTaskId(Long.valueOf(trackingTaskId));
        detailParamBean.setStart(0);
        detailParamBean.setLimit(Integer.MAX_VALUE);

        final List<TrackingTaskDetail> taskDetails = taskDetailMapper.select(detailParamBean);

        TrackingTaskAndDetailInfoVo taskAndDetailInfoVo = new TrackingTaskAndDetailInfoVo();
        taskAndDetailInfoVo.setTrackingTask(trackingTask);
        taskAndDetailInfoVo.setTrackingTaskDetails(taskDetails);

        return taskAndDetailInfoVo;
    }

    @Override
    public ResultBean updateParcelStatusByOrderId(int orderId, ParcelOrderStateEnum orderStateEnum) {
        final ShippingParcel shippingParcel = shippingParcelService.getByOrderId(orderId);

        ResultBean resultBean = new ResultBean();

        resultBean.setCode(ResultBean.OK);

        if (shippingParcel == null) {
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setDesc("根据订单ID查找包裹失败");
        } else {
            ShippingParcel updateBean = new ShippingParcel();
            updateBean.setId(shippingParcel.getId());
            updateBean.setState(orderStateEnum.getId());

            final int i = shippingParcelService.updateSelective(updateBean);
            if (i <= 0) {
                resultBean.setCode(ResultBean.FAIL);
                resultBean.setDesc("更新包裹订单状态失败");
            }
        }
        return resultBean;
    }

    /**
     * 处理订单未发货状态时间，根据订单创建物流任务
     *
     * @param orderId
     */
    private void orderWaitDeliverDispose(int orderId) {
        final ShippingParcel shippingParcel = shippingParcelService.selectByOrderId(orderId);

        final Integer shippingWayId = shippingParcel.getShippingWayId();
        Assert.notNull(shippingWayId, "包裹订单数据错误，无法找到物流线路ID");

        Set<Integer> shippingWayIdSet = new HashSet<>();
        shippingWayIdSet.add(shippingWayId);
        final List<ShippingWay> shippingWays = shippingWayService.queryByIds(shippingWayIdSet);
        Assert.notEmpty(shippingWays, "包裹订单数据错误，物流线路ID数据错误");

        final ShippingWay shippingWay = shippingWays.get(0);

        TrackingTask trackingTask = new TrackingTask();
        trackingTask.setShippingParcelId(shippingParcel.getId());
        trackingTask.setShippingWayId(shippingWayId);
        trackingTask.setApiCode(shippingWay.getShippingCode());
        trackingTask.setOrderId(orderId);
        trackingTask.setTrackNo(shippingParcel.getTrackNo());
        trackingTask.setComplete(0);

        Assert.isTrue(trackingTaskService.inserSelective(trackingTask) > 0, "保存物流任务失败");
    }

    /**
     * 处理订单已发货状态，根据订单ID更新物流任务信息
     */

    public void orderDeliverDispose(OrderStateInfo orderStateInfo) {
        final Integer orderId = orderStateInfo.getOrderId();
        TrackingTask trackingTask = trackingTaskService.selectByOrderId(orderId);
        // 物流别名Code
        final String shippingWayCode = orderStateInfo.getShippingWayCode();
        // 物流别名那么
        final String shippingWayName = orderStateInfo.getShippingWayName();
        // 运单号
        final String trackNo = orderStateInfo.getTrackNo();
        // 重量
        final BigDecimal weight = orderStateInfo.getWeight();
        Integer shippingWayId = null;
        if (!StringUtils.isEmpty(shippingWayCode)) {
            final ShippingStoreRel storeRel = shippingStoreRelationMapper.selectStoreRelByCode(shippingWayCode);
            if (storeRel != null)
                shippingWayId = storeRel.getShippingWayId();
        }
        if (shippingWayId == null && !StringUtils.isEmpty(shippingWayName)) {
            final ShippingStoreRel storeRel = shippingStoreRelationMapper.selectStoreRelByName(shippingWayName);
            if (storeRel != null)
                shippingWayId = storeRel.getShippingWayId();
        }
        Assert.notNull(shippingWayId, "查询物流线路与仓库管理失败");

        final List<ShippingWay> shippingWays = shippingWayService.queryByIds(Sets.newHashSet(shippingWayId));
        Assert.notEmpty(shippingWays, "查询物流线路失败");
        final ShippingWay shippingWay = shippingWays.get(0);

        final ShippingParcel shippingParcel = shippingParcelService.selectByOrderId(orderId);
        if (trackingTask != null) {
            TrackingTask updateTrackingTask = new TrackingTask();
            updateTrackingTask.setId(trackingTask.getId());
            updateTrackingTask.setTrackNo(trackNo);
            updateTrackingTask.setApiCode(shippingWay.getShippingCode());
            updateTrackingTask.setShippingWayId(shippingWay.getId());
            Assert.isTrue(trackingTaskService.updateSelective(updateTrackingTask) > 0, "更新物流任务失败");
        } else {
            trackingTask = new TrackingTask();
            trackingTask.setShippingParcelId(shippingParcel.getId());
            trackingTask.setShippingWayId(shippingWayId);
            trackingTask.setApiCode(shippingWay.getShippingCode());
            trackingTask.setOrderId(orderId);
            trackingTask.setTrackNo(trackNo);
            trackingTask.setFetchCount(0);
            trackingTask.setCreateAt(new Date());
            trackingTask.setComplete(0);
            Assert.isTrue(trackingTaskService.inserSelective(trackingTask) > 0, "保存物流任务失败");
        }

        ShippingParcel updateShiipingParcel = new ShippingParcel();
        updateShiipingParcel.setId(shippingParcel.getId());
        updateShiipingParcel.setTrackNo(trackNo);
        updateShiipingParcel.setShippingWayId(shippingWay.getId());
        updateShiipingParcel.setShippingAliasCode(shippingWayCode);
        updateShiipingParcel.setShippingAliasName(shippingWayName);
        updateShiipingParcel.setState(2);
        if (weight != null)
            updateShiipingParcel.setWeight(weight);
        Assert.isTrue(shippingParcelService.updateSelective(updateShiipingParcel) > 0, "更新包裹信息失败");
    }

    private ShippingParcel transferTransportParcel(TransportRequest transportRequest) {
        ShippingParcel shippingParcel = new ShippingParcel();
        shippingParcel.setOrderId(transportRequest.getOrderId());
        shippingParcel.setOrderAmount(transportRequest.getOrderAmount());
        shippingParcel.setOrderRealAmount(transportRequest.getOrderAmount());
        shippingParcel.setGoodsQty(transportRequest.getGoodsQty());
        shippingParcel.setOrderType(transportRequest.getOrderTypeEnum().ordinal());
        shippingParcel.setPayState(transportRequest.getPayState());
        shippingParcel.setOrderDate(transportRequest.getOrderDate());
        shippingParcel.setCurrencyCode(transportRequest.getCurrencyCode());
        shippingParcel.setWarehouseId(transportRequest.getWarehouseId());
        shippingParcel.setZoneId(transportRequest.getZoneId());
        shippingParcel.setOrderNo(transportRequest.getOrderNo());

        OrderLinkDto orderLinkDto = transportRequest.getOrderLinkDto();
        shippingParcel.setFirstName(orderLinkDto.getFirstName());
        shippingParcel.setLastName(orderLinkDto.getLastName());
        shippingParcel.setTelphone(orderLinkDto.getTelphone());
        shippingParcel.setEmail(orderLinkDto.getEmail());
        shippingParcel.setProvince(orderLinkDto.getProvince());
        shippingParcel.setCity(orderLinkDto.getCity());
        shippingParcel.setAddress(orderLinkDto.getAddress());
        shippingParcel.setZipcode(orderLinkDto.getZipcode());
        shippingParcel.setCountryCode(orderLinkDto.getCountryCode());
        shippingParcel.setCountry(orderLinkDto.getCountry());
        shippingParcel.setCustomerId(orderLinkDto.getCustomerId());
        shippingParcel.setArea(orderLinkDto.getArea());
        shippingParcel.setSyncStatus(SyncStatusEnum.DOWITHOUT.ordinal());
        shippingParcel.setWeight(new BigDecimal(0));
        shippingParcel.setIsSettlemented(0);
        shippingParcel.setOrderRemark(transportRequest.getRemark());
        shippingParcel.setServiceRemark(transportRequest.getServiceRemark());
        shippingParcel.setState(1);// 已创建
        shippingParcel.setParcelState(ParcelStateEnum.CREATE.ordinal());// 包裹状态 新建

        List<ShippingParcelDetail> shippingParcelDetails = transportRequest.getOrderDetails().stream().map(detail -> {
            ShippingParcelDetail parcelDetail = new ShippingParcelDetail();
            parcelDetail.setOrderItemId(detail.getOrderItemId());
            parcelDetail.setSku(detail.getSku());
            parcelDetail.setProductTitle(detail.getProductTitle());
            parcelDetail.setProductTitleEn(detail.getProductNameEN());
            parcelDetail.setOrderDetailQty(detail.getOrderDetailQty());
            parcelDetail.setPrice(detail.getPrice());
            parcelDetail.setTotalAmount(detail.getTotalAmount());
            parcelDetail.setInlandhscode(detail.getInlandHsCode());
            parcelDetail.setForeignhscode(detail.getForeignHsCode());
            return parcelDetail;
        }).collect(Collectors.toList());

        shippingParcel.setParcelDetails(shippingParcelDetails);
        return shippingParcel;
    }

    /**
     * 校验物流下单的参数
     *
     * @param transportRequest
     * @return
     */
    public TransportResponse verifyTransportOrder(TransportRequest transportRequest) {
        TransportResponse transportResponse = new TransportResponse();
        try {
            OrderLinkDto orderLinkDto = transportRequest.getOrderLinkDto();
            Assert.notNull(orderLinkDto, "收件人信息不能为空");
            Assert.hasText(orderLinkDto.getTelphone(), "收件人电话不能为空");
            Assert.notNull(orderLinkDto.getCity(), "收件人城市不能为空");
            Assert.notNull(orderLinkDto.getProvince(), "收件人省份不能为空");
            Assert.notNull(orderLinkDto.getCountry(), "收件人国家不能为空");

            Assert.notNull(orderLinkDto.getFirstName(), "收件人姓氏不能为空");
            Assert.notNull(orderLinkDto.getLastName(), "收件人姓名不能为空");

            Assert.hasText(orderLinkDto.getAddress(), "收件人地址不能为空");
            Assert.notNull(orderLinkDto.getZipcode(), "收件人邮编不能为空");

            Assert.notNull(transportRequest, "物流下单请求对象不能为空");
            Assert.notNull(transportRequest.getPayState(), "订单支付状态不能为空");
            Assert.notNull(transportRequest.getOrderAmount(), "订单金额不能为空");
            Assert.notNull(transportRequest.getOrderTypeEnum(), "订单类型不能为空");
            Assert.hasText(transportRequest.getOrderNo(), "订单号不能为空");
            Assert.notNull(transportRequest.getOrderId(), "订单ID不能为空");
            Assert.notNull(transportRequest.getZoneId(), "区域ID不能为空");
            Assert.notNull(transportRequest.getWarehouseId(), "仓库ID不能为空");

            Assert.notEmpty(transportRequest.getOrderDetails(), "订单详情列表不能为空");
            transportRequest.getOrderDetails().forEach(item -> {
                Assert.notNull(item.getSku(), "产品SKU不能为空");
                Assert.notNull(item.getOrderDetailQty(), "单项SKU件数不能为空");
                Assert.notNull(item.getPrice(), "单项SKU单价不能为空");
                Assert.notNull(item.getTotalAmount(), "单项SKU总价不能为空");
            });
            // 参数校验成功
            transportResponse.setCode(TransportOrderResponse.SUCCESS);
        } catch (Exception e) {
            logger.info("verifyTransportOrder() orderNo={},Exception={}", transportRequest.getOrderId(), e);
            transportResponse.setCode(TransportOrderResponse.FAIL);
            transportResponse.setErrorMsg(e.getMessage());
        }
        return transportResponse;
    }

}
