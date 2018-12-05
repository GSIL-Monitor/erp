package com.stosz.tms.service;

import com.google.common.collect.Lists;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.BeanMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.ext.service.IStorehouseService;
import com.stosz.tms.dto.*;
import com.stosz.tms.enums.OrderTypeEnum;
import com.stosz.tms.enums.ParcelStateEnum;
import com.stosz.tms.enums.SyncStatusEnum;
import com.stosz.tms.mapper.*;
import com.stosz.tms.model.*;
import com.stosz.tms.vo.ShippingParcelDetailVo;
import com.stosz.tms.vo.ShippingParcelListVo;
import com.stosz.tms.vo.ShippingParcelMatchingExportVo;
import com.stosz.tms.vo.ShippingParcelTaskListVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShippingParcelService {

    private Logger logger = LoggerFactory.getLogger(ShippingParcelService.class);

    @Resource
    private ShippingParcelMapper mapper;

    @Resource
    private ShippingWayMapper shippingWayMapper;

    @Autowired
    private TrackingTaskMapper taskMapper;

    @Resource
    private TrackingTaskDetailMapper trackingTaskDetailMapper;

    @Resource
    private ShippingParcelDetailMapper parcelDetailMapper;

    @Resource
    private IStockService stockService;

    @Resource
    private IStorehouseService storehouseService;

    @Autowired
    private TransportFacadeService transportFacadeService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ShippingParcelDetailService parcelDetailService;

    @Autowired
    private PlaceOrderService placeOrderService;

    public int count(ShippingParcel shippingParcel) {
        return mapper.count(shippingParcel);
    }

    public List<ShippingParcelListVo> queryList(ShippingParcel shippingParcel) {

        // if (!StringUtils.hasText(shippingParcel.getOrderBy())) {
        // shippingParcel.setOrderBy(" update_at desc,create_at");
        // }

        final List<ShippingParcel> shippingParcels = mapper.queryList(shippingParcel);

        final List<ShippingParcelListVo> shippingParcelListVos = BeanMapper.mapList(shippingParcels, ShippingParcelListVo.class);

        Set<Integer> shippingWayIds = shippingParcels.stream().filter(item -> item.getShippingWayId() != null).map(ShippingParcel::getShippingWayId)
                .collect(Collectors.toSet());

        if (ArrayUtils.isEmpty(shippingWayIds))
            return shippingParcelListVos;

        List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);

        final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

        shippingParcelListVos.forEach(e -> {
            final Integer shippingWayId = e.getShippingWayId();

            final List<ShippingWay> wayList = shippingWayMapById.get(shippingWayId);

            // 没有找到数据
            if (wayList == null || wayList.isEmpty())
                return;

            final ShippingWay shippingWay = wayList.get(0);

            e.setShippingWayName(shippingWay.getShippingWayName());
        });

        return shippingParcelListVos;
    }

    /**
     * 获取物流轨迹列表
     *
     * @param id
     * @return
     */
    public List<ShippingParcelTaskListVo> selectTaskList(Integer id) {
        TrackingTask taskParamBean = new TrackingTask();
        taskParamBean.setShippingParcelId(id);

        final List<TrackingTask> trackingTasks = taskMapper.find(taskParamBean);

        if (CollectionUtils.isNullOrEmpty(trackingTasks))
            return Collections.emptyList();

        final TrackingTask trackingTask = trackingTasks.get(0);

        final Integer taskId = trackingTask.getId();

        TrackingTaskDetail detailParamBean = new TrackingTaskDetail();
        detailParamBean.setTrackingTaskId(Long.valueOf(taskId));

        final List<TrackingTaskDetail> taskDetails = trackingTaskDetailMapper.select(detailParamBean);

        if (CollectionUtils.isNullOrEmpty(taskDetails))
            return Collections.emptyList();

        return BeanMapper.mapList(taskDetails, ShippingParcelTaskListVo.class);
    }

    public ShippingParcelDetailVo selectDetail(Integer id) {
        final ShippingParcel shippingParcel = mapper.queryById(id);
        return BeanMapper.map(shippingParcel, ShippingParcelDetailVo.class);
    }

    public ShippingParcel selectByOrderId(int orderId) {
        final ShippingParcel paramBean = new ShippingParcel();
        paramBean.setOrderId(orderId);

        final List<ShippingParcel> shippingParcels = mapper.queryList(paramBean);
        Assert.notEmpty(shippingParcels, "通过orderId查找包裹失败");

        return shippingParcels.get(0);
    }

    public List<ShippingParcelMatchingExportVo> matchingExport(ShippingParcelMatchingDto matchingDto) {
        ShippingParcel paramBean = new ShippingParcel();

        final Integer dataType = matchingDto.getDataType();

        final String dataList = matchingDto.getDataList();

        final String[] dataArr = dataList.split("\n");

        if (dataType == 1) {
            paramBean.setTrackNoList(Arrays.asList(dataArr));
        } else if (dataType == 2) {
            paramBean.setOrderNoList(Arrays.asList(dataArr));
        }

        final List<ShippingParcel> shippingParcels = mapper.queryList(paramBean);

        if (CollectionUtils.isNullOrEmpty(shippingParcels))
            return Collections.emptyList();

        final List<Integer> parcelIds = shippingParcels.stream().map(ShippingParcel::getId).collect(Collectors.toList());

        // 商品详情数据
        ShippingParcelDetail parcelDetailParamBean = new ShippingParcelDetail();
        parcelDetailParamBean.setParcelIdList(parcelIds);
        final List<ShippingParcelDetail> shippingParcelDetails = parcelDetailMapper.queryList(parcelDetailParamBean);
        final Map<Integer, List<ShippingParcelDetail>> parcelDetailMapByParcelId = shippingParcelDetails.stream()
                .collect(Collectors.groupingBy(ShippingParcelDetail::getParcelId));

        // 物流轨迹数据
        TrackingTask taskParamBean = new TrackingTask();
        taskParamBean.setParcelIdList(parcelIds);
        final List<TrackingTask> trackingTasks = taskMapper.find(taskParamBean);
        final Map<Integer, List<TrackingTask>> taskMapByParcelId = trackingTasks.stream()
                .collect(Collectors.groupingBy(TrackingTask::getShippingParcelId));

        // 物流方式数据
        final Set<Integer> shippingWayIds = shippingParcels.stream().map(ShippingParcel::getShippingWayId).collect(Collectors.toSet());
        final List<ShippingWay> shippingWays = shippingWayMapper.queryByIds(shippingWayIds);
        final Map<Integer, List<ShippingWay>> shippingWayMapById = shippingWays.stream().collect(Collectors.groupingBy(ShippingWay::getId));

        final List<Integer> wmsIdList = new ArrayList<>(shippingParcels.stream().map(ShippingParcel::getWarehouseId).collect(Collectors.toSet()));

        final List<Wms> wmss = storehouseService.findWmsByIds(wmsIdList);
        final Map<Integer, List<Wms>> wmsMapById = wmss.stream().collect(Collectors.groupingBy(Wms::getId));

        final List<ShippingParcelMatchingExportVo> matchingExportVos = Lists.newArrayList();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");

        shippingParcels.forEach(e -> {
            ShippingParcelMatchingExportVo matchingExportVo = new ShippingParcelMatchingExportVo();

            final Integer id = e.getId();
            final Integer shippingWayId = e.getShippingWayId();
            final Integer wmsId = e.getWarehouseId();

            final List<ShippingParcelDetail> parcelDetailList = parcelDetailMapByParcelId.get(id);
            final List<TrackingTask> trackingTaskList = taskMapByParcelId.get(id);
            final List<ShippingWay> shippingWayList = shippingWayMapById.get(shippingWayId);
            final List<Wms> wmsList = wmsMapById.get(wmsId);

            matchingExportVo.setTrackNo(e.getTrackNo());
            matchingExportVo.setOrderNo(e.getOrderNo());

            if (CollectionUtils.isNotNullAndEmpty(parcelDetailList)) {
                final List<String> productTitleList = parcelDetailList.stream().map(ShippingParcelDetail::getProductTitle)
                        .collect(Collectors.toList());
                final List<Integer> orderDetailQtyList = parcelDetailList.stream().map(ShippingParcelDetail::getOrderDetailQty)
                        .collect(Collectors.toList());
                final List<String> skuList = parcelDetailList.stream().map(ShippingParcelDetail::getSku).collect(Collectors.toList());

                matchingExportVo.setProductTitle(StringUtils.join(productTitleList, ","));
                matchingExportVo.setOrderDetailQty(StringUtils.join(orderDetailQtyList, ","));
                matchingExportVo.setSku(StringUtils.join(skuList, ","));
            }

            if (CollectionUtils.isNotNullAndEmpty(wmsList)) {
                matchingExportVo.setWmsName(wmsList.get(0).getName());
            }

            matchingExportVo.setGoodsQty(e.getGoodsQty());

            switch (e.getState()) {
                case 1:
                    matchingExportVo.setState("已创建");
                    break;
                case 2:
                    matchingExportVo.setState("已发货");
                    break;
                case 3:
                    matchingExportVo.setState("拒收");
                    break;
                case 4:
                    matchingExportVo.setState("取消");
                    break;
                case 5:
                    matchingExportVo.setState("确认收货");
                    break;
                default:
                    matchingExportVo.setState("未知状态");
                    break;
            }

            if (CollectionUtils.isNotNullAndEmpty(shippingWayList)) {
                final ShippingWay shippingWay = shippingWayList.get(0);
                matchingExportVo.setShippingName(shippingWay.getShippingWayName());
            }

            matchingExportVo.setTrackStatus(e.getTrackStatus());
            matchingExportVo.setClassifyStatus(e.getClassifyStatus());

            switch (e.getIsSettlemented()) {
                case 0:
                    matchingExportVo.setIsSettlemented("未结款");
                    break;
                case 1:
                    matchingExportVo.setIsSettlemented("已结款");
                    break;
                default:
                    matchingExportVo.setIsSettlemented("未知状态");
                    break;
            }

            matchingExportVo.setCreateAt(df.format(e.getCreateAt()));

            if (CollectionUtils.isNotNullAndEmpty(trackingTaskList)) {
                final Date receiveTime = trackingTaskList.get(0).getReceiveTime();
                if (receiveTime != null)
                    matchingExportVo.setReceiveTime(df.format(receiveTime));
            }

            if (e.getShipmentsTime() != null)
                matchingExportVo.setShipmentsTime(df.format(e.getShipmentsTime()));

            matchingExportVos.add(matchingExportVo);
        });

        return matchingExportVos;
    }

    /**
     * 新增包裹
     *
     * @param shippingParcel
     * @return
     */
    public int addParcel(ShippingParcel shippingParcel) {
        int count = mapper.add(shippingParcel);
        String parcelNo = StringUtil.generateNo("PA", shippingParcel.getId());
        ShippingParcel updateParcel = new ShippingParcel();
        updateParcel.setParcelNo(parcelNo);
        updateParcel.setId(shippingParcel.getId());
        count = mapper.updateSelective(updateParcel);
        return count;
    }

    /**
     * 修改包裹信息
     *
     * @param shippingParcel
     * @return
     */
    public int updateSelective(ShippingParcel shippingParcel) {
        return mapper.updateSelective(shippingParcel);
    }

    /**
     * 重新指派物流公司
     *
     * @return
     */
    public ResultBean<String> onceAgainAssignShipping(Integer parcelId) {
        ResultBean<String> resultBean = new ResultBean<>();
        ShippingParcel shippingParcel = mapper.queryById(parcelId);
        Assert.notNull(shippingParcel, "包裹ID[" + parcelId + "]不存在");
        // 获取到包裹的状态
        Integer parcelState = shippingParcel.getParcelState();
        Assert.isTrue(ParcelStateEnum.ASSIGN_FAIL.getType().equals(parcelState), "包裹的状态为指派失败,才能重新指派");

        List<ShippingParcelDetail> parcelDetaiList = parcelDetailMapper.queryParcelDetail(parcelId);
        TransportRequest request = parcelTransferRequest(shippingParcel, parcelDetaiList);

        TransportResponse response;
        if (ParcelStateEnum.ASSIGN_FAIL.getType().equals(parcelState))
            response = transportFacadeService.assignTransportShipping(request, shippingParcel);
        else
            response = transportFacadeService.assignSyncSheet(shippingParcel);

        if (TransportResponse.FAIL.equals(response.getCode())) {
            resultBean.setCode(ResultBean.FAIL);
            resultBean.setDesc(response.getErrorMsg());
            return resultBean;
        }
        if (shippingParcel.getSyncStatus().equals(SyncStatusEnum.STAYSYNC.ordinal())) {// 如果需要推送信息到物流承运商
            ShippingParcel shippingParcelDb = this.getById(shippingParcel.getId());
            placeOrderService.asyncPlaceOrderHandle(shippingParcelDb);
        }
        try {
            // 通知订单指派物流成功
            com.stosz.order.ext.dto.TransportRequest transportRequest = new com.stosz.order.ext.dto.TransportRequest();
            transportRequest.setCode(response.getCode());
            transportRequest.setShippingCode(response.getShippingCode());
            transportRequest.setShippingName(response.getShippingName());
            transportRequest.setShippingWayId(response.getShippingWayId());
            transportRequest.setTrackingNo(response.getTrackingNo());
            transportRequest.setOrderId(shippingParcel.getOrderId());
            com.stosz.order.ext.dto.TransportResponse orderReponse = orderService.reAssignmentLogistics(transportRequest);
            logger.info("onceAgainAssignShipping() code={},errormsg={}", orderReponse.getCode(), orderReponse.getErrorMsg());
        } catch (Exception e) {
            logger.info("onceAgainAssignShipping  通知订单失败() parcelId={},orderId={}, Exception={}", parcelId, shippingParcel.getOrderId(), e);
        }
        // 重新指派物流公司成功
        resultBean.setCode(ResultBean.OK);
        return resultBean;
    }

    /**
     * 手动通知订单回写运单信息
     *
     * @return
     */
    public ResultBean<String> onceAgainNotifyOrder(Integer parcelId) {
        ResultBean<String> resultBean = new ResultBean<>();
        ShippingParcel shippingParcel = mapper.queryById(parcelId);
        Assert.notNull(shippingParcel, "包裹ID[" + parcelId + "]不存在");
        // 获取到包裹的状态
        Integer parcelState = shippingParcel.getParcelState();
        Integer syncStatus = StringUtil.nvl(shippingParcel.getSyncStatus(), -1);
        Assert.isTrue(ParcelStateEnum.PUSHORDER_FAIL.getType().equals(parcelState), "包裹的状态为推送订单失败，才能发起推送");
        Assert.isTrue(SyncStatusEnum.SYNC_SUCCESS.ordinal() == syncStatus, "同步状态为成功，才能发起推送");

        ShippingParcel updateParcel = new ShippingParcel();
        ResultBean<HashMap<String, Object>> expressResultBean = placeOrderService.invokeExpressAndNotifyOrder(shippingParcel);
        if (ResultBean.OK.equals(expressResultBean.getCode())) {
            updateParcel.setParcelState(ParcelStateEnum.PUSHORDER_SUCCSS.getType());
            updateParcel.setAssignErrorMsg("");
            resultBean.setCode(ResultBean.OK);
        } else {
            updateParcel.setParcelState(ParcelStateEnum.PUSHORDER_FAIL.getType());
            updateParcel.setAssignErrorMsg(expressResultBean.getDesc());

            resultBean.setCode(ResultBean.FAIL);
            resultBean.setDesc(expressResultBean.getDesc());
        }
        mapper.updateSelective(updateParcel);
        return resultBean;
    }

    /**
     * 根据订单号/订单ID查询是否存在
     *
     * @param orderId
     * @param
     * @return
     */
    public int queryParcelCount(Integer orderId) {
        return mapper.queryParcelCount(orderId);
    }

    /**
     * 添加包裹
     *
     * @param shippingParcel
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "tmsTransactionManager", rollbackFor = Exception.class)
    public int addParcelAndDetails(ShippingParcel shippingParcel) {
        int count = mapper.add(shippingParcel);
        if (count > 0) {
            String parcelNo = StringUtil.generateNo("PA", shippingParcel.getId());
            ShippingParcel parcel = new ShippingParcel();
            parcel.setParcelNo(parcelNo);
            parcel.setId(shippingParcel.getId());
            mapper.updateSelective(parcel);
            // 订单详情
            List<ShippingParcelDetail> parcelDetails = shippingParcel.getParcelDetails();
            parcelDetails.forEach(item -> {
                item.setParcelId(shippingParcel.getId());
            });
            count = parcelDetailService.addParcelDetails(parcelDetails);
            Assert.isTrue(count > 0, "包裹明细入库失败");
        }
        return count;
    }

    /**
     * 根据ID查询物流包裹
     *
     * @param parcelId
     * @return
     */
    public ShippingParcel getById(Integer parcelId) {
        return mapper.queryById(parcelId);
    }

    /**
     * 根据订单ID查询物流包裹
     *
     * @param orderId
     * @return
     */
    public ShippingParcel getByOrderId(Integer orderId) {
        return mapper.queryByOrderId(orderId);
    }

    private TransportRequest parcelTransferRequest(ShippingParcel shippingParcel, List<ShippingParcelDetail> parcelDetaiList) {
        TransportRequest request = new TransportRequest();
        request.setGoodsQty(shippingParcel.getGoodsQty());
        request.setCurrencyCode(shippingParcel.getCurrencyCode());
        request.setOrderAmount(shippingParcel.getOrderAmount());
        request.setOrderDate(shippingParcel.getOrderDate());
        request.setOrderId(shippingParcel.getOrderId());
        request.setOrderNo(shippingParcel.getOrderNo());
        request.setPayState(shippingParcel.getPayState());
        request.setRemark(shippingParcel.getOrderRemark());
        request.setWarehouseId(shippingParcel.getWarehouseId());
        request.setZoneId(shippingParcel.getZoneId());
        request.setOrderTypeEnum(OrderTypeEnum.fromId(shippingParcel.getOrderType()));
        request.setPayState(shippingParcel.getPayState());
        request.setWeight(shippingParcel.getWeight());

        OrderLinkDto orderLinkDto = new OrderLinkDto();
        orderLinkDto.setAddress(shippingParcel.getAddress());
        orderLinkDto.setArea(shippingParcel.getArea());
        orderLinkDto.setCity(shippingParcel.getCity());
        orderLinkDto.setCountry(shippingParcel.getCountry());
        orderLinkDto.setCountryCode(shippingParcel.getCountryCode());
        orderLinkDto.setCustomerId(shippingParcel.getCustomerId());
        orderLinkDto.setEmail(shippingParcel.getEmail());
        orderLinkDto.setFirstName(shippingParcel.getFirstName());
        orderLinkDto.setLastName(shippingParcel.getLastName());
        orderLinkDto.setProvince(shippingParcel.getProvince());
        orderLinkDto.setTelphone(shippingParcel.getTelphone());
        orderLinkDto.setZipcode(shippingParcel.getZipcode());
        request.setOrderLinkDto(orderLinkDto);

        List<TransportOrderDetail> orderDetails = parcelDetaiList.stream().map(item -> {
            TransportOrderDetail orderDetail = new TransportOrderDetail();
            orderDetail.setForeignHsCode(item.getForeignhscode());
            orderDetail.setInlandHsCode(item.getInlandhscode());
            orderDetail.setOrderDetailQty(item.getOrderDetailQty());
            orderDetail.setOrderItemId(item.getOrderItemId());
            orderDetail.setPrice(item.getPrice());
            orderDetail.setProductNameEN(item.getProductTitle());
            orderDetail.setProductTitle(item.getProductTitle());
            orderDetail.setSku(item.getSku());
            orderDetail.setTotalAmount(item.getTotalAmount());
            orderDetail.setTotalWeight(item.getTotalAmount());
            orderDetail.setUnit(item.getUnit());
            orderDetail.setWeight(item.getWeight());
            return orderDetail;
        }).collect(Collectors.toList());
        request.setOrderDetails(orderDetails);
        return request;
    }
}
