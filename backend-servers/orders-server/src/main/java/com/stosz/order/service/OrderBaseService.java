package com.stosz.order.service;

import com.google.common.base.Strings;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.mq.OrderStateModel;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

/**
 * @auther carter
 * create time    2017-12-06
 * 放一些service中都会使用到的方法，比如参数校验； 其它等；
 */
public abstract class OrderBaseService{

    private static final Logger logger = LoggerFactory.getLogger(OrderBaseService.class);

    @Resource
    private IZoneService zoneService;

    @Resource
    private IDepartmentService departmentService;

    @Resource
    private IUserService userService;

    @Autowired
    private ProjectConfigService projectConfigService;



    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;


    public void publishOrderStateMessage(Orders orders, OrderStateEnum orderStateEnum) {
        OrderStateModel orderStatusChangeMessage = new OrderStateModel();
        orderStatusChangeMessage.setOrder_no(orders.getMerchantOrderNo());
        /**订单状态 0 待审*******************/
        orderStatusChangeMessage.setStatus(orderStateEnum.ordinal());
        /**运单号*******************/
        orderStatusChangeMessage.setTrack_number(orders.getTrackingNo());
        /**物流公司名称*******************/
        orderStatusChangeMessage.setLogistics_name(orders.getLogisticsName());
        /**运单产生时间*******************/
        orderStatusChangeMessage.setTrack_time(orders.getStateTime().toEpochSecond(ZoneOffset.UTC));
        /**备注*******************/
        orderStatusChangeMessage.setRemark(orders.getMemo());

        rabbitMQPublisher.saveMessage(OrderStateModel.messageType, RabbitMQConfig.method_insert, orderStatusChangeMessage);
    }


    /**
     * 检查用户姓名是否合法， 如果合法，把用户信息填充到参数里面
     *  第二个参数可以传一个 new User()
     * @param userFullName  用户全名
     * @param user          用户对象
     * @return   true 检验通过  false 检验不通过
     */
    protected  boolean  checkUserFullName(String userFullName,  User user)
    {
        if (Strings.isNullOrEmpty(userFullName)) return false;

        List<User> userList = userService.findBylastname(userFullName);

        if(CollectionUtils.isNullOrEmpty(userList)) return false;

        user =  userList.stream().filter(user1 -> userFullName.equals(user1.getLastname())).findFirst().get();

        return true;
    }

    /**
     * todo 不应该在后端获取？
     * 获取地址前缀
     * @return
     */
    public String getProjectErpImageHttpPrefix(){
        return "http://erp-img-local.stosz.com";
    }



    /**
     * 检查区域是否存在，如果存在，返回true， zone的信息填充到参数里
     * @param zoneId  区域id
     * @param zone     区域对象
     * @return     true 检验通过   false 检验失败
     */
    protected  boolean  checkZoneId(Integer zoneId,  Zone zone) {

        if(null == zoneId || zoneId.intValue() < 1) return false;


       zone = zoneService.getById(zoneId);

       if(null == zone ||  null == zone.getId() || zone.getId().intValue() < 1)
       {
           return false;
       }

       return true;

    }


    /**
     *校验部门id  如果校验通过，把部门信息放在输入参数中
     * @param departmentId  部门id
     * @param department   部门实体
     * @return   true 检验通过   false 检验失败
     */
    protected  boolean  checkDepartmentId(Integer departmentId,  Department department) {

     if(null == departmentId || departmentId.intValue() < 1 ) return false;

     department =  departmentService.get(departmentId);

     if(null == department || null == department.getId() || department.getId() < 1)
     {
         return true;
     }

     return true;

    }


    public void printTimeCost(long start,String title)
    {
        logger.info(Optional.ofNullable(title).orElse("操作")+"耗时:" + String.valueOf(System.currentTimeMillis() -Optional.ofNullable(start).orElse(new Long(0))) );
    }




    }
