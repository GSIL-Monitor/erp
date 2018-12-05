package com.stosz.order.service.asyn;

import com.stosz.order.ext.dto.OrderSaveDto;
import com.stosz.order.ext.model.OrdersAddition;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.mapper.order.OrdersAdditionMapper;
import com.stosz.order.mapper.order.OrdersItemsMapper;
import com.stosz.order.mapper.order.OrdersLinkMapper;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Resource(shareable = false)
public class OrderAsynService {

    @Autowired
    private OrdersItemsMapper ordersItemsMapper;
    @Autowired
    private OrdersLinkMapper ordersLinkMapper;
    @Autowired
    private OrdersAdditionMapper ordersAdditionMapper;

    @Autowired
    private IProductService productService;


    private volatile  static OrderAsynService instance;

    public static final int orderAsynWorkThreadNum = 2;

    private static ExecutorService executorService= Executors.newFixedThreadPool(orderAsynWorkThreadNum, r -> {
        final Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(true);
        return thread;
    });

    private  static LinkedBlockingQueue<OrderSaveDto> logQueue =new LinkedBlockingQueue<>(100000);


    public static final Logger logger = LoggerFactory.getLogger(OrderAsynService.class);


    public static void saveOrderSaveTask(OrderSaveDto orderSaveDto)
    {
        if(null != orderSaveDto)
        {
            logQueue.offer(orderSaveDto);
        }
    }



    /**
     * 获取异步服务的单例；
     * @return
     */
    public static OrderAsynService getInstance()
    {
        if(null == instance)
        {
            synchronized (OrderAsynService.class){
                if(null == instance) instance= new OrderAsynService();
            }
        }
        return instance;
    }


    private OrderAsynService()
    {

        IntStream.range(0, orderAsynWorkThreadNum).forEach(value -> executorService.execute(()->{
            while (true){
                try {
                    //会阻塞至有消息
                    OrderSaveDto orderSaveDto = logQueue.take();
                    if(null == orderSaveDto || orderSaveDto.getOrderId()<=0) continue;
                    processOrderSaveDto(orderSaveDto);


                } catch (InterruptedException e) {
                    logger.error("异步处理订单相关信息异常"+e.getMessage(),e);
                }
            }
        }));

    }

    private void processOrderSaveDto(OrderSaveDto orderSaveDto) {

        Long orderId = orderSaveDto.getOrderId();
        //1,处理订单项
        if(CollectionUtils.isNotNullAndEmpty(orderSaveDto.getOrderItemInfoList())) saveOrderItems(orderSaveDto.getOrderItemInfoList(),orderId);
        //2,处理联系信息
        if(null!= orderSaveDto.getOrderLinkInfo()) saveOrderLink(orderSaveDto.getOrderLinkInfo(),orderId);
        //3，处理附加信息
        if(null!= orderSaveDto.getOrderWebInfo()) saveOrderAddition(orderSaveDto.getOrderWebInfo(),orderId);
        //4,黑名单信息 todo

        //5,重复数量信息 todo


    }

    private void saveOrderAddition(OrderSaveDto.OrderWebInfo orderWebInfo, Long orderId) {

        Optional.of(orderWebInfo)
                .map(webInfo->
                {
                    OrdersAddition orderAddtion = new OrdersAddition();

                    orderAddtion.setOrdersId(orderId);
                    orderAddtion.setUserAgent(webInfo.getUserAgent());
                    orderAddtion.setOrderFrom(webInfo.getOrderFrom());
                    orderAddtion.setIp(webInfo.getIp());
                    orderAddtion.setIpName("");//todo  可以通过程序进行远程获取；
                    orderAddtion.setWebDomain(webInfo.getWebDomain());

                    return orderAddtion;
                })
                .ifPresent(orderAdditionInfo->ordersAdditionMapper.insert(orderAdditionInfo));
    }

    private void saveOrderLink(OrderSaveDto.OrderLinkInfo orderLinkInfo, Long orderId) {

        Optional.of(orderLinkInfo)
                .map(linInfo->
                {
                    OrdersLink ordersLink = new OrdersLink();

                    ordersLink.setOrdersId(orderId);
                    ordersLink.setFirstName(linInfo.getFirstName());
                    ordersLink.setLastName(linInfo.getLastName());
                    ordersLink.setCountry(linInfo.getCountry());
                    ordersLink.setTelphone(linInfo.getTelphone());
                    ordersLink.setEmail(linInfo.getEmail());
                    ordersLink.setProvince(linInfo.getProvince());
                    ordersLink.setCity(linInfo.getCity());
                    ordersLink.setArea(linInfo.getArea());
                    ordersLink.setAddress(linInfo.getAddress());
                    ordersLink.setZipcode(linInfo.getZipcode());

                    return ordersLink;
                })
                .ifPresent(orderLink-> ordersLinkMapper.insert(orderLink));


    }

    private void saveOrderItems(List<OrderSaveDto.OrderItemInfo> orderItemInfoList, Long orderId) {


        List<OrdersItem> orderItemsList = orderItemInfoList.stream()
                .map(orderItemInfo ->
                {
                    OrdersItem orderItems = new OrdersItem();
                    orderItems.setOrdersId(orderId);

                    orderItems.setSpu(orderItemInfo.getSpu());
                    orderItems.setQty(orderItemInfo.getQty());
                    orderItems.setSingleAmount(orderItemInfo.getSingleAmount());
                    orderItems.setTotalAmount(orderItemInfo.getTotalAmount());
                    orderItems.setProductId(orderItemInfo.getProductId());

                    Product product = productService.getBySpu(orderItemInfo.getSpu());
                    orderItems.setProductTitle(null == product?"":product.getTitle());
                    orderItems.setProductImageUrl(null == product? "":product.getMainImageUrl());
                    orderItems.setSku(orderItemInfo.getSku());

                    orderItems.setForeignTitle(orderItemInfo.getForeignTitle());
                    orderItems.setForeignTotalAmount(orderItemInfo.getForeignTotalAmount());
                    orderItems.setAttrNameArray(orderItemInfo.getAttrNameArray());
                    orderItems.setAttrValueArray(orderItemInfo.getAttrValueArray());

                    return orderItems;
                })
                .collect(Collectors.toList());


        Optional.ofNullable(orderItemsList).ifPresent(items-> ordersItemsMapper.batchInsert(items));


    }


}