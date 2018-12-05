package com.stosz.order.service;

import com.google.common.collect.Lists;
import com.stosz.order.ext.dto.BatchImportResult;
import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.order.ext.dto.OrdersQuestionDto;
import com.stosz.order.ext.enums.OrderQuestionStatusEnum;
import com.stosz.order.ext.enums.OrderQuestionTypeEnum;
import com.stosz.order.ext.model.*;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import com.stosz.order.mapper.order.OrdersMapper;
import com.stosz.order.mapper.order.OrdersQuestionMapper;
import com.stosz.order.util.LocalDateTimeUtil;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @auther carter
 * create time    2017-12-06
 */
@Service
public class OrdersQuestionService extends OrderBaseService {
    private static final Logger logger = LoggerFactory.getLogger(OrdersQuestionService.class);

    @Autowired
    private OrdersQuestionMapper ordersQuestionMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersLinkMapper ordersLinkMapper;

    @Autowired
    private OrdersItemsMapper ordersItemsMapper;


    public OrdersQuestion findByOrdersId(Integer ordersId) {
        return ordersQuestionMapper.findByOrdersId(ordersId);
    }


    public List<OrdersQuestionDO> find(OrdersQuestionDto ordersQuestionDto) {
        // common  check
        checkOrdersQuestionDto(ordersQuestionDto);
        //query db;
        List<OrdersQuestionDO> ordersQuestionList = ordersQuestionMapper.find(ordersQuestionDto);


        //获取订单行信息
        Set<Integer> orderIds = ordersQuestionList.stream().map(o -> o.getOrdersId().intValue()).collect(Collectors.toSet());
        List<OrdersItem> ois = ordersItemsMapper.findByOrderIds(Lists.newArrayList(orderIds));

        //<orderId,List<OrdersItem>>
        Map<Long, List<OrdersItem>> orderItemsMap = ois.stream().collect(groupingBy(OrdersItem::getOrdersId));


        //订单列表页，标题展示
        ordersQuestionList.stream().forEach(e -> {
            List<OrdersItem> ordersItems = Optional.ofNullable(orderItemsMap.get(Long.valueOf(e.getOrdersId()))).orElse(Lists.newLinkedList());
            //<spu, List<OrdersItem>>
            Map<String, List<OrdersItem>> spuMap = ordersItems.stream().collect(groupingBy(OrdersItem::getSpu));
            List<OrderItemSpuDTO> orderItemSpuDTOList = new ArrayList<>();
            for (String spu : spuMap.keySet()) {
                List<OrdersItem> oi = spuMap.get(spu);
                List<OrderItemSpuDTO.OrderItemSku> skuList = new ArrayList<>();
                if (oi != null && oi.size() != 0) {
                    for (OrdersItem o : oi) {
                        OrderItemSpuDTO.OrderItemSku sku = new OrderItemSpuDTO.OrderItemSku();
                        sku.setQty(o.getQty());
                        sku.setAttr(o.getAttrTitleArray());
                        sku.setSku(o.getSku());
                        skuList.add(sku);
                    }
                    orderItemSpuDTOList.add(new OrderItemSpuDTO(spu, oi.get(0).getProductTitle(), skuList));
                }

            }
            e.setSpuList(orderItemSpuDTOList);

        });


        return ordersQuestionList;
    }

    public Integer count(OrdersQuestionDto ordersQuestionDto) {
        // common  check
        checkOrdersQuestionDto(ordersQuestionDto);
        //query db;
        return ordersQuestionMapper.count(ordersQuestionDto);
    }

    /**
     * TODO 数据校验需要review
     *
     * @param content
     * @return
     * @throws IOException
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BatchImportResult _import(String content) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(content));


        /**
         * 支持解析的格式
         * 2017/01/01，2017/01/1，2017/1/01，2017/1/1
         * 2017-01-01，2017-01-1，2017-1-01，2017-1-1
         */
//        DateTimeFormatter defaultFmt = DateTimeFormatter.ofPattern("yyyy/M/d H:m:s");
//        DateTimeFormatter mainFmt = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s");

        UserDto user = ThreadLocalUtils.getUser();
        BatchImportResult importResult = new BatchImportResult();

        int totalCount = 0, failCount = 0, successCount = 0;
        LocalDateTime logisticDeliveryTime;
        String transNo;
        OrderQuestionTypeEnum questionTypeEnum;
        String questionDetail;

        Orders order;
        OrdersQuestion existedOrdersQuestion, ordersQuestion;
        OrdersLink ordersLink;

        String line = "";
        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty())
                continue;

            ++totalCount;

            String[] arr = line.split("\t");
            if (arr.length < 4) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport("格式不正确", line));
                importResult.setHasWrong(true);
                continue;
            }


            try {

                Assert.hasText(arr[0], "时间为空");
                Assert.hasText(arr[1], "运单号为空");
                Assert.hasText(arr[2], "问题类型为空");
                Assert.hasText(arr[3], "问题描述为空");

//                String dateTimeStr = arr[0] + " 00:00:00";
//
//                if (dateTimeStr.indexOf("/") > 0) {
//                    logisticDeliveryTime = LocalDateTime.parse(dateTimeStr, defaultFmt);
//                } else if (dateTimeStr.indexOf("-") > 0) {
//                    logisticDeliveryTime = LocalDateTime.parse(dateTimeStr, mainFmt);
//                } else {
//                    throw new RuntimeException("不支持当前时间格式解析: " + dateTimeStr);
//                }

                logisticDeliveryTime = LocalDateTimeUtil.toLocalDateTime(arr[0]);
                transNo = arr[1];
                questionTypeEnum = OrderQuestionTypeEnum.fromDisplay(arr[2]);
                questionDetail = arr[3];
            } catch (Exception e) {
                logger.info("{},格式解析错误：{}", line, e.getMessage());
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport("格式不正确", line));
                importResult.setHasWrong(true);
                continue;
            }

            //检查问题类型
            if (Objects.isNull(questionTypeEnum)) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(transNo, "问题类型错误，只可用：联系不上,地址错误,拒收,退货,换货,其他"));
                continue;
            }

            // 检查重复
            existedOrdersQuestion = ordersQuestionMapper.findByTransNo(transNo);
            if (Objects.nonNull(existedOrdersQuestion)) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(transNo, "已在问题件列表，请检查是否重复导入"));
                continue;
            }

            // 检查运单
            order = ordersMapper.findByTrackingNo(transNo);
            if (Objects.isNull(order)) {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport(transNo, "未找到此运单，请检查运单号是否正确"));
                continue;
            }

            ordersQuestion = new OrdersQuestion();

            ordersQuestion.setTransNo(order.getTrackingNo());
            ordersQuestion.setQuestionType(questionTypeEnum);
            ordersQuestion.setQuestionDetail(questionDetail);

            ordersQuestion.setLogisticDeliveryTime(logisticDeliveryTime);
            ordersQuestion.setLogisticId(order.getLogisticsId());
            ordersQuestion.setLogisticName(order.getLogisticsName());

            ordersQuestion.setCreatorId(user.getId());
            ordersQuestion.setCreator(user.getLastName());
            ordersQuestion.setDepartmentId(user.getDeptId());
            ordersQuestion.setDepartmentInfo(user.getDeptName());
            ordersQuestion.setOrderNo(order.getId().toString());
            ordersQuestion.setOrdersId(Long.valueOf(order.getId()));
            ordersQuestion.setOrderInnerTitle(order.getOrderInnerTitle());//
            ordersQuestion.setTotalAmount(order.getConfirmAmount());//
            ordersQuestion.setZoneId(order.getZoneId());
            ordersQuestion.setZoneName(order.getZoneName());//
            ordersQuestion.setSendTime(order.getDeliveryTime());

            //通过订单ID获取收货人信息
            ordersLink = ordersLinkMapper.getById(order.getId());
            if (Objects.nonNull(ordersLink)) {
                ordersQuestion.setCustomerId(ordersLink.getCustomerId());
                ordersQuestion.setCustomerEmail(ordersLink.getEmail());
                ordersQuestion.setCustomerName(ordersLink.getFirstName());
                ordersQuestion.setCustomerPhone(ordersLink.getTelphone());
            }

            Integer row = ordersQuestionMapper.insert(ordersQuestion);

            if (row > 0) {
                ++successCount;
            } else {
                ++failCount;
                importResult.getFailList().add(new BatchImportResult.FailedImport("保存失败", line));
            }
        }


        importResult.setTotalCount(totalCount);
        importResult.setSuccessCount(successCount);
        importResult.setFailCount(failCount);

        return importResult;
    }

    /**
     * 问题件处理
     *
     * @param orderQuestionIdArray
     * @param dealMemo
     * @param dealEventEnumName
     * @return
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deal(String[] orderQuestionIdArray, String dealMemo, String dealEventEnumName) {

        OrderQuestionStatusEnum questionStatus = OrderQuestionStatusEnum.valueOf(dealEventEnumName);

        Assert.notEmpty(orderQuestionIdArray, "问题件id不能为空");
        Assert.hasText(dealMemo, "备注不能为空");
        Assert.notNull(questionStatus, "处理结果枚举类型不存在");

        int row = ordersQuestionMapper.updateDealStatus(orderQuestionIdArray, questionStatus.ordinal(), dealMemo, MBox.getLoginUserId(), MBox.getLoginUserName());

        if (questionStatus == OrderQuestionStatusEnum.SureReject) {
            // TODO 停止抓取物流
        }

        return row;
    }


    private void checkOrdersQuestionDto(OrdersQuestionDto ordersQuestionDto) {
        Assert.notNull(ordersQuestionDto, "问题件查询参数不能为空！");

        if (Objects.nonNull(ordersQuestionDto.getInputTimeEnd()) && Objects.nonNull(ordersQuestionDto.getInputTimeEnd())) {
            Assert.isTrue(!ordersQuestionDto.getInputTimeBegin().isAfter(ordersQuestionDto.getInputTimeEnd()), "问题件查询参数录入时间endTime不能早于beginTime");
        }

        if (Objects.nonNull(ordersQuestionDto.getHandleTimeBegin()) && Objects.nonNull(ordersQuestionDto.getHandleTimeEnd())) {
            Assert.isTrue(!ordersQuestionDto.getHandleTimeBegin().isAfter(ordersQuestionDto.getHandleTimeEnd()), "问题件查询参数处理时间endTime不能早于beginTime");
        }

        if (Objects.nonNull(ordersQuestionDto.getInputTimeBegin()) && Objects.nonNull(ordersQuestionDto.getHandleTimeBegin())) {
            Assert.isTrue(!ordersQuestionDto.getInputTimeBegin().isAfter(ordersQuestionDto.getHandleTimeBegin()), "问题件查询参数处理时间endTime不能早于录入的beginTime");
        }
    }
}
