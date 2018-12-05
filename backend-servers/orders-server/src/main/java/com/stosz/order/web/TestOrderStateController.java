package com.stosz.order.web;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.exception.FsmException;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.fsm.xml.EventNode;
import com.stosz.fsm.xml.FsmRootNode;
import com.stosz.order.ext.dto.OrderSaveDto;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.model.BlackList2;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.mq.OrderStateModel;
import com.stosz.order.service.OrderService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-11-17
 * 测试订单状态机页面；
 */
@Controller("TestOrderStateController")
public class TestOrderStateController {

    private static final Logger logger = LoggerFactory.getLogger(TestOrderStateController.class);



    @Autowired
    private OrderService orderService;

    @Autowired
    private FsmProxyService<Orders> ordersFsmProxyService;

    private static FsmRootNode fsmRootNode =initFsmRootNode();

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher2;



    @RequestMapping("/src/main/webapp/test/testShell")
    @ResponseBody
    public RestResult testShell(HttpServletRequest request ,    @Autowired ProjectConfigService projectConfigService) {
        RestResult restResult = new RestResult();


        String systemName = CommonUtils.getStringValue(request.getParameter("sysName"));
        StringBuffer stringBuffer = new StringBuffer();
        new Thread(()->{


            try {
                //执行脚本
//            Process process = Runtime.getRuntime().exec(cmdString);
                ProcessBuilder processBuilder = new ProcessBuilder("/home/erp/src/deploy.sh",systemName);
                logger.info("执行脚本： "+processBuilder.command());
                Process process = processBuilder.start();
                process.waitFor();

                //拿到执行结果；
                String ls_1;
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()));
                while ((ls_1=bufferedReader.readLine()) != null)
                {
                    logger.info(ls_1);
                    stringBuffer.append(ls_1);
                }
                bufferedReader.close();
                process.waitFor();



            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
            }


        }).start();



        restResult.setDesc(stringBuffer.toString());
        return restResult;
    }

    @RequestMapping("/src/main/webapp/test/testp")
    @ResponseBody
    public RestResult testp(HttpServletRequest request ,    @Autowired ProjectConfigService projectConfigService) {
        RestResult restResult = new RestResult();

//        restResult.setDesc(projectConfigService.getProjectErpImageHttpPrefix());
        return restResult;
    }


        @RequestMapping("/src/main/webapp/test/testOrderStatusChange")
    @ResponseBody
    public RestResult testOrderStatusChange()
    {
        OrderStateModel orderStatusChangeMessage  = new OrderStateModel();
        orderStatusChangeMessage.setOrder_no(Arrays.asList(
                "1171212183629228888",
                "1171212183706503222",
                "1171212183840193838",
                "1171212184234618883",
                "1171213105128423225",
                "1171213113113152222"
        ).get(new Random().nextInt(5)));
        /**订单状态 0 待审*******************/
//        orderStatusChangeMessage.setStatus(Arrays.asList(
//                OrderStateEnum.auditPass.ordinal(),
//                OrderStateEnum.waitPurchase.ordinal(),
//                OrderStateEnum.question.ordinal(),
//                OrderStateEnum.sign.ordinal(),
//                OrderStateEnum.rejection.ordinal(),
//                OrderStateEnum.repeat.ordinal()
//        ).get(new Random().nextInt(5)));
        /**运单号*******************/
        orderStatusChangeMessage.setTrack_number(UUID.randomUUID().toString()) ;
        /**物流公司名称*******************/
        orderStatusChangeMessage.setLogistics_name(Arrays.asList(
                "顺丰",
                "顺丰2",
                "顺丰3",
                "顺丰6",
                "顺丰7",
                "PI"
        ).get(new Random().nextInt(5)));
        /**运单产生时间*******************/
        orderStatusChangeMessage.setTrack_time(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        /**备注*******************/
        orderStatusChangeMessage.setRemark("testMemo");

        rabbitMQPublisher.saveMessage(OrderStateModel.messageType, RabbitMQConfig.method_insert,orderStatusChangeMessage);

        return new RestResult();
    }

    @RequestMapping("/src/main/webapp/test/testFsm")
    @ResponseBody
    public RestResult testFsmLog(HttpServletRequest request)
    {


        Integer orderId = CommonUtils.getIntValue(request.getParameter("orderId"));

        String eventName = OrderEventEnum.create.name();

        String memo = "testxxxxx";


        Orders instance = orderService.findOrderById(orderId);

        OrderEventEnum orderEventEnum = OrderEventEnum.fromName(eventName);
        ordersFsmProxyService.processEvent(instance,orderEventEnum,memo);

        return new RestResult();
    }

    @RequestMapping("/src/main/webapp/test/testEnum")
    @ResponseBody
    public RestResult testEnum()
    {
        RestResult restResult = new RestResult();

        BlackList2 blackList2 = new BlackList2();
        blackList2.setId(1);
        blackList2.setContent("xxxx");
        blackList2.setCreator("carter");
        blackList2.setCreatorId(1);
        blackList2.setCreateAt(LocalDateTime.now());
        blackList2.setUpdateAt(LocalDateTime.now());

        restResult.setItem(blackList2);
        return  restResult;

    }


    @RequestMapping("/")
    public void index(HttpServletResponse response)
    {
        try {
            response.getWriter().write("order sub system is ok");
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

    }

    @RequestMapping("/src/main/webapp/test/sendMQmessage")
    public void sendMQmessage(HttpServletRequest request)
    {

        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("access",UUID.randomUUID().toString());
        rabbitMQPublisher.saveMessage("src/main/webapp/test","insert",dataMap);
    }


    @RequestMapping("/src/main/webapp/test/sendMQmessage2")
    public void sendMQmessage2(HttpServletRequest request)
    {

        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("access",UUID.randomUUID().toString());
        rabbitMQPublisher2.saveMessage("testxxx","insert",dataMap);
    }





    private static FsmRootNode initFsmRootNode() {


        String xml = null;
        try {
            xml = FileUtils.readFile(new ClassPathResource("fsm/order/orderFsm.xml").getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        // XML转换为FsmRootNode
        FsmRootNode root = convert(xml);

        return root;
    }

    private static FsmRootNode convert(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(FsmRootNode.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            FsmRootNode root = (FsmRootNode) unmarshaller.unmarshal(reader);

            return root;
        } catch (Exception e) {
            logger.error("XML转换为FsmRootNode, xml=" + xml, e);
            throw new FsmException("XML转换为FsmRootNode失败, " + e.getMessage());
        }
    }




            @RequestMapping("/order/test/add")
    public ModelAndView add(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("order/test/add");
        return modelAndView;
    }

    @RequestMapping("/order/test/save")
    public ModelAndView save(HttpServletRequest request, OrderSaveDto order)
    {
        ModelAndView modelAndView = new ModelAndView("order/test/add");

        //设置属性；
        {
            order.getOrderInfo().setMerchantOrderNo(CommonUtils.getStringValue(request.getParameter("order.orderInfo.merchantOrderNo")));
            order.getOrderInfo().setOrderTypeEnum(CommonUtils.getIntValue(request.getParameter("order.orderInfo.orderTypeEnum")));
            order.getOrderInfo().setZoneCode(CommonUtils.getStringValue(request.getParameter("order.orderInfo.zoneCode")));
            order.getOrderInfo().setCurrencyCode(CommonUtils.getStringValue(request.getParameter("order.orderInfo.currencyCode")));
            order.getOrderInfo().setOrderExchangeRate(new BigDecimal(CommonUtils.getDoubleValue(request.getParameter("order.orderInfo.orderExchangeRate"))));
            order.getOrderInfo().setOrderAmount(new BigDecimal(CommonUtils.getDoubleValue(request.getParameter("order.orderInfo.orderAmount"))));
            order.getOrderInfo().setGoodsQty(CommonUtils.getIntValue(request.getParameter("order.orderInfo.goodsQty")));
            order.getOrderInfo().setIp(CommonUtils.getStringValue(request.getParameter("order.orderInfo.ip")));
            order.getOrderInfo().setCustomerMeassage(CommonUtils.getStringValue(request.getParameter("order.orderInfo.customerMeassage")));
            order.getOrderInfo().setPurchaserAt(CommonUtils.getLocalDateTime(request.getParameter("order.orderInfo.purchaserAt")));
            order.getOrderInfo().setGetCode(CommonUtils.getStringValue(request.getParameter("order.orderInfo.getCode")));
            order.getOrderInfo().setInputCode(CommonUtils.getStringValue(request.getParameter("order.orderInfo.inputCode")));
            order.getOrderInfo().setSeoUserId(CommonUtils.getIntValue(request.getParameter("order.orderInfo.seoUserId")));
            order.getOrderInfo().setPaymentMethod(CommonUtils.getIntValue(request.getParameter("order.orderInfo.paymentMethod")));

            //设置订单项
            OrderSaveDto.OrderItemInfo orderItemInfo = new OrderSaveDto().new OrderItemInfo();

            orderItemInfo.setSpu(CommonUtils.getStringValue(request.getParameter("order.orderItemInfoList.spu")));
            orderItemInfo.setSku(CommonUtils.getStringValue(request.getParameter("order.orderItemInfoList.sku")));
            orderItemInfo.setQty(CommonUtils.getIntValue(request.getParameter("order.orderItemInfoList.qty")));
            orderItemInfo.setSingleAmount(new BigDecimal( CommonUtils.getDoubleValue(request.getParameter("order.orderItemInfoList.singleAmount"))));
            orderItemInfo.setTotalAmount(new BigDecimal(CommonUtils.getDoubleValue(request.getParameter("order.orderItemInfoList.totalAmount"))));
            orderItemInfo.setProductId(CommonUtils.getLongValue(request.getParameter("order.orderItemInfoList.productId")));
            orderItemInfo.setForeignTitle(CommonUtils.getStringValue(request.getParameter("order.orderItemInfoList.foreignTitle")));
            orderItemInfo.setForeignTotalAmount(CommonUtils.getStringValue(request.getParameter("order.orderItemInfoList.foreignTotalAmount")));
            orderItemInfo.setAttrNameArray(CommonUtils.getStringValue(request.getParameter("order.orderItemInfoList.attrNameArray")));
            orderItemInfo.setAttrValueArray(CommonUtils.getStringValue(request.getParameter("order.orderItemInfoList.attrValueArray")));
            order.getOrderItemInfoList().add(orderItemInfo);

            //联系信息
            order.getOrderLinkInfo().setFirstName(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.firstName")));
            order.getOrderLinkInfo().setLastName(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.lastName")));
            order.getOrderLinkInfo().setCountry(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.country")));
            order.getOrderLinkInfo().setTelphone(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.telphone")));
            order.getOrderLinkInfo().setEmail(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.email")));
            order.getOrderLinkInfo().setProvince(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.province")));
            order.getOrderLinkInfo().setCity(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.city")));
            order.getOrderLinkInfo().setArea(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.area")));
            order.getOrderLinkInfo().setAddress(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.address")));
            order.getOrderLinkInfo().setZipcode(CommonUtils.getStringValue(request.getParameter("order.orderLinkInfo.zipcode")));

            //web信息；

            order.getOrderWebInfo().setUserAgent(CommonUtils.getStringValue(request.getParameter("order.orderWebInfo.userAgent")));
            order.getOrderWebInfo().setOrderFrom(CommonUtils.getStringValue(request.getParameter("order.orderWebInfo.orderFrom")));
            order.getOrderWebInfo().setIp(CommonUtils.getStringValue(request.getParameter("order.orderWebInfo.ip")));
            order.getOrderWebInfo().setWebDomain(CommonUtils.getStringValue(request.getParameter("order.orderWebInfo.webDomain")));

        }




        try {
            Orders orders = orderService.saveOrder(order);
            if(orders.getId()>0)
            {//跳到状态机测试页面；
                modelAndView.setView(new RedirectView("/order/test/"+orders.getId()));
            }else{
                modelAndView.addObject("message","添加失败");
            }
        }catch (Exception ex)
        {
            modelAndView.addObject("message",ex.getMessage());
        }

        return modelAndView;
    }


    @RequestMapping("/order/test/{orderId}")
    public ModelAndView index(@PathVariable Integer orderId, HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("order/test/state");
        Orders order = orderService.findOrderById(orderId);
        modelAndView.addObject("order",order);



        StringBuffer stateButtonBuffer = new StringBuffer();

        //拿到状态配置对应的事件
        fsmRootNode.getStates()
                .getState()
                .stream()
                .filter(stateNode -> stateNode.getName().equalsIgnoreCase(order.getOrderStatusEnum().name()))
                .findFirst()
                .ifPresent(stateNode -> {
                    Set<String> eventNameSet = stateNode.getEvent().stream().map(EventNode::getName).collect(Collectors.toSet());

                    Arrays.asList(OrderEventEnum.values()).stream().forEach(st->{

                        stateButtonBuffer.append(" <button class=\"stateEventBton\" ");
                                if(eventNameSet.contains(st.name())){
                                    stateButtonBuffer.append("style='color:red;' ");
                                }
                                stateButtonBuffer.append(" event=\""+st.name()+"\" value=\""+st.ordinal()+"\" >"+st.display()+"</button><br>");


                    });



                });


        modelAndView.addObject("btnString",stateButtonBuffer.toString());

        List<IFsmHistory> iFsmHistories = ordersFsmProxyService.queryFsmHistory("Orders", orderId);

        modelAndView.addObject("his",iFsmHistories);



        if(request.getMethod().equalsIgnoreCase("post"))
        {



            //生成按钮，人工模拟操作；


        }
        return modelAndView;
    }






    @RequestMapping("/order/test/changeEvent")
    public ModelAndView changeEvent(HttpServletRequest request)
    {

        Integer orderId = CommonUtils.getIntValue(request.getParameter("orderId"));

        ModelAndView modelAndView = new ModelAndView(new RedirectView("/order/test/"+orderId));

        String eventName = CommonUtils.getStringValue(request.getParameter("eventName"));

        String memo = CommonUtils.getStringValue(request.getParameter("memo"));


        Orders instance = orderService.findOrderById(orderId);

        OrderEventEnum orderEventEnum = OrderEventEnum.fromName(eventName);
        ordersFsmProxyService.processEvent(instance,orderEventEnum,memo);

        return modelAndView;
    }




}
