package com.stosz.order.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.stosz.crm.ext.CustomerSearchParam;
import com.stosz.crm.ext.enums.CodeTypeEnum;
import com.stosz.crm.ext.enums.CustomerCreditEnum;
import com.stosz.crm.ext.model.CustomerApiLog;
import com.stosz.crm.ext.model.CustomerUpdateLog;
import com.stosz.crm.ext.model.Customers;
import com.stosz.olderp.ext.service.IOldErpOrderService;
import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.order.service.OrdersLinkService;
import com.stosz.order.service.crm.CustomerService;
import com.stosz.order.util.RegexUtils;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.EnumUtils;
import com.stosz.plat.utils.IEnum;
import com.stosz.plat.web.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orders/crm/customers")
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private IOldErpOrderService oldErpOrderService;

    @Autowired
    private OrdersLinkService ordersLinkService;




    /**
     * crm枚举类型
     * @return
     */
    @RequestMapping(value = "enumList")
    public RestResult enumList() {
        RestResult result = new RestResult();
        Map<String,Object> map = Maps.newHashMap();
        map.put("codeType", EnumUtils.getEnumJsonString(CodeTypeEnum.class));
        map.put("customerCredit", EnumUtils.getEnumJsonString(CustomerCreditEnum.class));

        List<Map<String, Object>> useableEnum = EnumUtils.getEnumJsonString(UseableEnum.class);
        removeEnum(useableEnum, UseableEnum.other);
        map.put("state", useableEnum);

        result.setCode(RestResult.OK);
        result.setItem(map);
        return result;
    }

    @RequestMapping(value = "find")
    public RestResult find(CustomerSearchParam param) {
        return customerService.findCustomers(param);
    }

    @RequestMapping(value = "detail")
    public RestResult detail(@Param("id") Integer id) {
        Customers customers = customerService.getById(id);
        RestResult result = new RestResult();
        result.setItem(customers);
        result.setCode(RestResult.OK);
        result.setDesc("客户详情查询成功");
        return result;
    }


    @PostMapping(value = "add")
    public RestResult add(Customers param) {

        MBox.assertLenth(param.getFirstName(), "firstName", 1, 40);
        MBox.assertLenth(param.getLastName(), "lastName",0,40);
        MBox.assertLenth(param.getEmail(), "邮箱",0,100);
        MBox.assertLenth(param.getTelphone(), "手机号",1,15);
        MBox.assertLenth(param.getAddress(), "地址",0,200);
        MBox.assertLenth(param.getMemo(), "备注",0,200);
        Assert.notNull(param.getLevelEnum(),"客户等级不能为空");
        UserDto userDto = ThreadLocalUtils.getUser();

        if(!Strings.isNullOrEmpty(param.getEmail())) {
            Assert.isTrue(RegexUtils.checkEmail(param.getEmail()), "邮件格式不正确");
        }
        Assert.isTrue(RegexUtils.checkDigit(param.getTelphone()), "手机格式不正确");

        RestResult result = new RestResult();

        param.setSetLevelEnum(1);
        param.setCreatorId(userDto.getId());
        param.setCreator(userDto.getLastName());
        param.setZoneCode("");
        try{
            customerService.add(param);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("客户["+param.getTelphone()+"]已存在");
        }
        logger.info("客户添加成功: {}", param);

        result.setCode(RestResult.NOTICE);
        result.setDesc("客户添加成功");
        return result;
    }

    @PostMapping(value = "udpateLog")
    public RestResult updateLog(@RequestParam("id") Integer customerId, @RequestParam(value = "start", defaultValue = "1") Integer start, @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        RestResult restResult = new RestResult();
        Assert.notNull(customerId,"客户ID不能为空");
        PageHelper.offsetPage(start,limit);
        List<CustomerUpdateLog> log =customerService.findUpdateLogByCustomerId(customerId);
        PageInfo<CustomerUpdateLog> pageInfo = new PageInfo<>(log);
        restResult.setItem(log);
        restResult.setTotal((int) pageInfo.getTotal());
        restResult.setCode(RestResult.OK);
        restResult.setDesc("客户历史更改记录查询成功");
        return restResult;
    }



    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PostMapping(value = "update")
    public RestResult update(Customers param) {

        MBox.assertLenth(param.getFirstName(), "firstName", 1, 40);
        MBox.assertLenth(param.getLastName(), "lastName",0,40);
        MBox.assertLenth(param.getEmail(), "邮箱",0,100);
        MBox.assertLenth(param.getTelphone(), "手机号",1,15);
        MBox.assertLenth(param.getAddress(), "地址",0,200);
        MBox.assertLenth(param.getMemo(), "备注",0,200);

        Assert.isTrue(RegexUtils.checkEmail(param.getEmail()), "邮件格式不正确");
        Assert.isTrue(RegexUtils.checkDigit(param.getTelphone()), "手机格式不正确");
        Assert.notNull(param.getLevelEnum(),"客户等级不能为空");

        Customers temp = customerService.getById(param.getId());
        Assert.notNull(temp,"客户不存在");

        UserDto userDto = ThreadLocalUtils.getUser();

        RestResult result = new RestResult();
        param.setId(temp.getId());
        param.setUpdateAt(LocalDateTime.now());
        param.setCreatorId(userDto.getId());
        param.setCreator(userDto.getLastName());

        //记录更新内容
        saveUpdateLog(param,temp,userDto);

        try{
            customerService.update(param);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("客户["+param.getTelphone()+"]已存在");
        }
        logger.info("客户修改成功: {}", param);

        result.setCode(RestResult.NOTICE);
        result.setDesc("客户信息修改成功");
        return result;
    }

    private void saveUpdateLog(Customers param, Customers temp, UserDto userDto) {
        CustomerUpdateLog log = new CustomerUpdateLog();
        log.setCreateAt(LocalDateTime.now());
        log.setCreator(userDto.getLastName());
        log.setCreatorId(userDto.getId());
        log.setCustomerId(temp.getId());
        StringBuilder sb = new StringBuilder();
        if(!temp.getLevelEnum().equals(param.getLevelEnum())) {
            sb.append("[评级:" + CustomerCreditEnum.fromId(temp.getLevelEnum()).display() + "->" + CustomerCreditEnum.fromId(param.getLevelEnum()).display() + "],");
            //从其他状态转变成普通用户则清零
            if(param.getLevelEnum() == CustomerCreditEnum.normal.ordinal()){
                param.setRejectQty(0);
                param.setAcceptQty(0);
            }
        }
        if(!temp.getTelphone().trim().equals(param.getTelphone().trim())){
            sb.append("[手机号:" + temp.getTelphone() + "->" + param.getTelphone() + "]");
        }
        if(!temp.getEmail().trim().equals(param.getEmail().trim())){
            sb.append("[邮箱:" + temp.getEmail() + "->" + param.getEmail() + "]");
        }
        if(!temp.getAddress().trim().equals(param.getAddress().trim())){
            sb.append("[地址:" + temp.getAddress() + "->" + param.getAddress() + "]");
        }
        if(!temp.getFirstName().equals(param.getFirstName())){
            sb.append("[客户姓:" + temp.getFirstName() + "->" + param.getFirstName() + "]");
        }
        if(!temp.getLastName().equals(param.getLastName())){
            sb.append("[客户名:" + temp.getLastName() + "->" + param.getLastName() + "]");
        }
        if(temp.getState() != param.getState()){
            sb.append("[状态:" + UseableEnum.fromId(temp.getState()).display() + "->" + UseableEnum.fromId(param.getState()).display() + "]");
        }
        if(!temp.getMemo().equals(param.getMemo())){
            sb.append("[备注:" + temp.getMemo() + "->" + param.getMemo() + "]");
        }
        log.setContent(sb.toString());
        customerService.saveUpdateLog(log);
    }

    @PostMapping(value = "delete")
    public RestResult delete(Integer customerId) {
        RestResult restResult = new RestResult();
        Assert.notNull(customerId,"客户ID不能为空");
        customerService.delete(customerId);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("客户信息删除成功");
        return restResult;
    }

    /**
     * 改变客户状态
     * @param customerId
     * @return
     */
    @PostMapping(value = "enable")
    public RestResult enable(Integer customerId) {
        RestResult restResult = new RestResult();
        Assert.notNull(customerId,"客户ID不能为空");
        customerService.enable(customerId);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("状态修改成功");
        return restResult;
    }



    @PostMapping("query")
    public RestResult queryCustomersForInsert(HttpServletRequest request,Customers customers){
        Assert.notNull(customers,"用户信息不能为空");
        Assert.notNull(customers.getTelphone(),"用户手机号不能为空");
        Assert.notNull(customers.getCodeType(),"验证码类型不能为空");

        //写入调用日志
        CustomerApiLog log = new CustomerApiLog();
        log.setReqParam(customers.toString());

        Customers newCustomer = customerService.queryCustomersForInsert(customers);
        log.setOrderId(customers.getOrderId());
        log.setCustomerId(newCustomer.getId());
        log.setCreateAt(LocalDateTime.now());
        log.setLevel(newCustomer.getLevelEnum());

        //备注是字段用于存储请求客户端的地址
        String requestURL = "";
        if (request.getHeader("x-forwarded-for") == null) {
            requestURL = request.getRemoteAddr();
        }else {
            requestURL =  request.getHeader("x-forwarded-for");
        }
        if(requestURL != null && requestURL.length() > 45){
            requestURL = requestURL.substring(0,45);
        }
        log.setMemo(requestURL);


        RestResult result = new RestResult();
        result.setCode(RestResult.OK);
        result.setItem(newCustomer);
        result.setTotal(1);
        result.setDesc("请求成功");

        log.setRespBody(result.toString());
        customerService.insertApiLog(log);

        return result;
    }

    @GetMapping("/id/{customerId}")
    public RestResult getByFrontOrder(@PathVariable("customerId") Integer customerId, @RequestParam(value = "orderId",required = false) Long orderId){
        Assert.notNull(customerId,"客户编号不能为空");
        CustomerApiLog log = new CustomerApiLog();
        String req = orderId == null ? "" : orderId +"";
        log.setReqParam("customerId: " +customerId + " orderId: " + req);

        RestResult result = new RestResult();
        Customers customers =  customerService.getById(customerId);
        if(customers == null){
            result.setDesc("找不到该客户信息");
            result.setTotal(0);
            return result;
        }

        log.setOrderId(orderId);
        log.setCustomerId(customers.getId());
        log.setCreateAt(LocalDateTime.now());
        log.setLevel(customers.getLevelEnum());
        log.setMemo(customers.getCustomerCreditEnum());

        result.setItem(customers);
        result.setTotal(1);

        log.setRespBody(result.toString());
        customerService.insertApiLog(log);
        return result;
    }

    @GetMapping("/telphone/{telphone}")
    public Customers getByFrontOrder(@PathVariable("telphone") String telphone){
        Assert.notNull(telphone,"手机号不能为空");
        return customerService.getByTelphone(telphone);
    }

//    @PostMapping("oldErp/orders")
//    public RestResult getOrderErpByCustomerId(@RequestParam("id") Integer id, Integer offset , Integer limit){
//        Assert.notNull(id,"客户编号不能为空");
//        RestResult result = new RestResult();
////        Assert.isTrue(offset >= 0 , "offset必须大于等于0");
////        Assert.isTrue(limit > 0 , "limit必须大于0");
//        Customers customers = customerService.getById(id);
//        String tel = customers == null ? "" : customers.getTelphone();
//        List<OldErpOrderLink> list = oldErpOrderService.findRiskOldErpOrderLinkByTel(tel,0,100);
//        result.setTotal(list.size());
//        result.setItem(list);
//        result.setCode(RestResult.OK);
//        return result;
//    }


    @PostMapping("/order_log")
    public RestResult findOrdersByTel(@RequestParam("tel") String tel, @RequestParam(value = "start", defaultValue = "1") Integer start, @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        return customerService.findCustomerOrdersByTel(tel,start,limit);
    }


    private void removeEnum(List orderStateEnum, IEnum e) {
        for (Iterator<Map<String, Object>> iter = orderStateEnum.iterator(); iter.hasNext(); ) {
            Map<String, Object> next = iter.next();
            for (String key : next.keySet()) {
                if (next.get(key).equals(e.name())) {
                    iter.remove();
                }
            }
        }
    }

}
