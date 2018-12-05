package com.stosz.order.service;

import com.stosz.admin.ext.enums.JobAuthorityRelEnum;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.OrdersRefundDTO;
import com.stosz.order.ext.enums.OrderRefundEventEnum;
import com.stosz.order.ext.enums.OrdersRefundMethodEnum;
import com.stosz.order.ext.model.OrdersRefundBill;
import com.stosz.order.ext.model.OrdersRefundBillDO;
import com.stosz.order.ext.model.UserZone;
import com.stosz.order.mapper.order.OrdersRefundBillMapper;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther tangtao
 * create time  2018/1/3
 */
@Service
public class OrdersRefundService {

    @Autowired
    private OrdersRefundBillMapper ordersRefundBillMapper;

    @Autowired
    private UserZoneService userZoneService;

    @Resource
    private IJobAuthorityRelService jobAuthorityRelService;

    @Resource
    private IDepartmentService departmentService;

    @Autowired
    @Qualifier("orderRefundFsmProxyService")
    private FsmProxyService<OrdersRefundBill> orderRefundFsmProxyService;


    /**
     * 新增退款申请单
     *
     * @param ordersRefundBill
     * @return
     */
    public void save(OrdersRefundBill ordersRefundBill) {
        ordersRefundBillMapper.insert(ordersRefundBill);
    }


    /**
     * 修改
     *
     * @param ordersRefundDTO
     * @return
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer modify(OrdersRefundDTO ordersRefundDTO) {
        Assert.notNull(ordersRefundDTO, "修改实体不能为空");
        Assert.notNull(ordersRefundDTO.getId(), "id不能为空");
        Assert.notNull(ordersRefundDTO.getRefundAmount(), "退款金额不能为空");

        OrdersRefundBill oldRefundBill = ordersRefundBillMapper.findById(Integer.valueOf(ordersRefundDTO.getId()));
//        if (null == ordersRefundDTO.getCustomerGetAccount())
//            ordersRefundDTO.setCustomerGetAccount("");

        // 物流自退方式直接完结
//        if (ordersRefundDTO.getRefundMethodEnum() == OrdersRefundMethodEnum.logistics) {
//            orderRefundFsmProxyService.processEvent(oldRefundBill, OrderRefundEventEnum.toRefunded, MBox.getLoginUserName(), LocalDateTime.now(), "退款编辑-修改退款方式：物流承运商-完结，原收款账号：" + oldRefundBill);
//        } else {
//            orderRefundFsmProxyService.processEvent(oldRefundBill, OrderRefundEventEnum.toApplyRefund, MBox.getLoginUserName(), LocalDateTime.now(), "退款编辑-提交");
//        }

        // 目前需求只能修改金额
        orderRefundFsmProxyService.processEvent(oldRefundBill, OrderRefundEventEnum.toApplyRefund, MBox.getLoginUserName(), LocalDateTime.now(), "退款编辑-提交");


        return ordersRefundBillMapper.modify(ordersRefundDTO);
    }

    /**
     * 审批
     *
     * @param id
     * @param auditMemo
     * @param result
     * @return
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer approve(Integer id, String auditMemo, Boolean result) {

        Assert.notNull(id, "id不能为空");
        Assert.notNull(result, "审批结果不能为空");
        Assert.notNull(auditMemo, "审批备注不能为空");

        OrdersRefundBill refundBill = new OrdersRefundBill();

        refundBill.setId(id);
        refundBill.setAuditMemo(auditMemo);
        refundBill.setAuditUserId(MBox.getLoginUserId());
        refundBill.setAuditUserName(MBox.getLoginUserName());
        refundBill.setAuditTime(LocalDateTime.now());
        refundBill.setUpdateAt(LocalDateTime.now());

        // FMS
        OrdersRefundBill oldRefundBill = ordersRefundBillMapper.findById(id);
        if (result) {
            orderRefundFsmProxyService.processEvent(oldRefundBill, OrderRefundEventEnum.refundAuditPass, MBox.getLoginUserName(), LocalDateTime.now(), "退款审核-通过" + "\n" + refundBill.getAuditMemo());
        } else {
            orderRefundFsmProxyService.processEvent(oldRefundBill, OrderRefundEventEnum.refundAuditReject, MBox.getLoginUserName(), LocalDateTime.now(), "退款审核-驳回" + "\n" + refundBill.getAuditMemo());
        }

        return ordersRefundBillMapper.updateSelective(refundBill);
    }

    /**
     * 确认退款
     *
     * @param id
     * @param refundSerialNumber
     * @return
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer refunded(Integer id, String refundSerialNumber) {
        Assert.notNull(id, "id不能为空");
        Assert.hasText(refundSerialNumber, "退款流水号不能为空");

        OrdersRefundBill refundBill = new OrdersRefundBill();
        refundBill.setId(id);
        refundBill.setRefundSerialNumber(refundSerialNumber);
        refundBill.setUpdateAt(LocalDateTime.now());

        // FMS TODO
        OrdersRefundBill oldRefundBill = ordersRefundBillMapper.findById(id);
        orderRefundFsmProxyService.processEvent(oldRefundBill, OrderRefundEventEnum.confrimRefund, MBox.getLoginUserName(), LocalDateTime.now(), "退款确认");

        return ordersRefundBillMapper.updateSelective(refundBill);
    }

    public List<OrdersRefundBillDO> find(OrdersRefundDTO ordersRefundDTO) {
        Assert.notNull(ordersRefundDTO, "查询参数不能为空");
        setPermission(ordersRefundDTO);
        return ordersRefundBillMapper.findByParam(ordersRefundDTO);
    }


    public Integer count(OrdersRefundDTO ordersRefundDTO) {
        Assert.notNull(ordersRefundDTO, "查询参数不能为空");
        setPermission(ordersRefundDTO);
        return ordersRefundBillMapper.count(ordersRefundDTO);
    }

    public OrdersRefundBill findById(Integer id) {
        Assert.notNull(id, "id不能为空");
        return ordersRefundBillMapper.findById(id);
    }

    /**
     * 设置权限
     *
     * @param param
     */
    private void setPermission(OrdersRefundDTO param) {
        //用户
        UserDto userDto = ThreadLocalUtils.getUser();

        //地区权限
        List<UserZone> userZoneList = userZoneService.findEnableUserZoneByUserId(userDto.getId());
        List<Integer> zoneIds = userZoneList.stream().map(z -> z.getZoneId()).collect(Collectors.toList());
        param.setZoneIds(zoneIds);

        //权限类型（公司，部门，个人）
        JobAuthorityRel jobAuthorityRel = jobAuthorityRelService.getByUser(userDto.getId());

        //个人
        if (jobAuthorityRel.getJobAuthorityRelEnum() == JobAuthorityRelEnum.myself) {
            param.setCreatorId(userDto.getId());
        }

        //部门
        if (jobAuthorityRel.getJobAuthorityRelEnum() == JobAuthorityRelEnum.myDepartment) {
            //获取查询部门的所有子部门
            Department department = departmentService.get(userDto.getDeptId());
            if (department != null) {
                List<Department> departmentList = departmentService.findByNo(department.getDepartmentNo());
                List<Integer> depts = departmentList.stream().map(e -> e.getId()).collect(Collectors.toList());
                param.setDepartmentIds(depts);
            }

        }
    }

}
