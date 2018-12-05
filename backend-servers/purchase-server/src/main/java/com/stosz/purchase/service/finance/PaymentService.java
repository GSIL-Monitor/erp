package com.stosz.purchase.service.finance;


import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.enums.PayableStateEnum;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.ext.model.finance.PayStateNotice;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.ext.model.finance.Payment;
import com.stosz.purchase.mapper.finance.PaymentMapper;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseReturnedItemService;
import com.stosz.purchase.service.PurchaseReturnedService;
import com.stosz.purchase.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/29 15:39
 */
@Service
public class PaymentService {

    @Resource
    private PaymentMapper paymentMapper;
    @Resource
    private PaymentItemService paymentItemService;
    @Resource
    private PayableService payableService;
    @Resource
    private PurchaseReturnedService purchaseReturnedService;
    @Resource
    private PurchaseReturnedItemService returnedItemService;
    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private AmountAdjustService amountAdjustService;

//    public void insert(Payment payment) {
//        payment.setCreateAt(LocalDateTime.now());
//        payment.setUpdateAt(LocalDateTime.now());
//        paymentMapper.insert(payment);
//    }
//
//    public Integer count(Payment payment) {
//        return paymentMapper.count(payment);
//    }
//
//    public List<Payment> query(Payment payment) {
//        return paymentMapper.query(payment);
//    }
//
//    public Integer findByIds(Integer[] ids) {
//        return paymentMapper.findByIds(ids).size();
//    }
//
//    public Payment findById(int id) {
//        return paymentMapper.getById(id);
//    }
//
//    /**
//     * 已付款
//     *
//     * @param ids 元素对象
//     */
//    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void billPaid(Integer[] ids) {
//        // 更新付款单数据 插入明细
//        updatePayment(ids, PayStateEnum.paid.name());
//        //更新应付款的状态
//        List<Payable> payableList = payableService.findByPaymentIds(ids);
//        for (Payable payable : payableList){
//            payable.setState(PayableStateEnum.complete.name());
//            payableService.update(payable);
//        }
//        addPayItem(ids);
//        //通知各模块更新数
//        sendPayStateNotice(ids, PayStateEnum.paid.name());
//    }
//
//    /**
//     * 拒绝
//     *
//     * @param ids 元素对象
//     */
//    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void refusePay(Integer[] ids) {
//        updatePayment(ids, PayStateEnum.refuse.name());
//        sendPayStateNotice(ids, PayStateEnum.refuse.name());
//    }
//
//    /**
//     * 删除付款单
//     *
//     * @param id 元素对象
//     */
//    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void delete(Integer id) {
//        Payment payment = paymentMapper.getById(id);
//        Assert.notNull(payment, "不存在该付款单");
//        Assert.isTrue(PayStateEnum.wait_pay.name().equals(payment.getState()), "付款单，单号：" + payment.getId() + "已支付不可删除");
//        //将付款单里的数据
//        Integer deleteQty = paymentMapper.delete(id);
//        Assert.isTrue(deleteQty == 1, "付款表删除数据操作有误");
//        //要删除调整单里面的数据
//        amountAdjustService.deleteByPayId(id);
//        //删除应付款单里的调整单
//        payableService.deleteAjust(id, BillTypeEnum.adjustment.ordinal());
//        //将应付单里的付款单id清空
//        payableService.emptyPayId(id);
//    }
//
//    public void sendPayStateNotice(Integer[] ids, String payType) {
//        List<Payment> payments = paymentMapper.findByIds(ids);
//        for (Payment payment : payments) {
////            Assert.isTrue(payment.getPayType() == PayStateEnum.wait_pay.ordinal(), "付款单，单号：" + payment.getId() + "已支付不可删除");
//        }
//        for (Payment payment : payments) {
//            Payable payable = new Payable();
//            payable.setPaymentId(payment.getId());
//            List<Payable> parableList = payableService.findByParam(payable);
//            for (Payable payable1 : parableList) {
//
//                if (!BillTypeEnum.adjustment.name().equals(BillTypeEnum.fromId(payable1.getChangeBillType()).name())) {
//                    PayStateNotice payStateNotice = new PayStateNotice();
//                    payStateNotice.setBillNo(payable1.getChangeBillNo().toString());
//                    payStateNotice.setBillType(BillTypeEnum.fromId(payable1.getChangeBillType()).name());
//                    payStateNotice.setAmount(payable1.getAmount());
//                    payStateNotice.setType(payType);
//                    payStateNotice.setUser(payment.getPayUser());
//                    payStateNotice.setUserId(payment.getPayUserId());
//                    payStateNotice.setPayTime(payment.getPayTime());
//                    if (BillTypeEnum.purchase.ordinal() == payable1.getChangeBillType()) {
//                        purchaseService.payEventWriteBack(payStateNotice);
//                    } else if (BillTypeEnum.recovery.ordinal() == payable1.getChangeBillType()) {
//                        purchaseReturnedService.payEventWriteBack(payStateNotice);
//                    }
//                }
//            }
//        }
//    }
//
//    private void addPayItem(Integer[] ids) {
//        for (Integer id : ids) {
//            Payment payment = paymentMapper.getById(id);
//            Payable payable = new Payable();
//            payable.setPaymentId(id);
//            List<Payable> payables = payableService.query(payable);
//            for (Payable pay : payables) {
//                Integer changeBillType = pay.getChangeBillType();
//                if (changeBillType == BillTypeEnum.purchase.ordinal()) {
//                    Purchase purchase = purchaseService.getByPurchaseNo(pay.getChangeBillNo().toString());
//                    List<PurchaseItem> purchaseItems = purchaseItemService.findByPurchaseId(Integer.parseInt(pay.getChangeBillNo()));
//                    AddPayable addPayAble = purchaseService.getAddPayAble(purchase, purchaseItems);
//                    addPayAble.setPaymentId(id);
//                    paymentItemService.addPaymentItem(addPayAble);
//                } else if (changeBillType == BillTypeEnum.recovery.ordinal()) {
//                    PurchaseReturned purchaseReturned = purchaseReturnedService.getById(Integer.parseInt(pay.getChangeBillNo()));
//                    List<PurchaseReturnedItem> purchaseReturnedItems = returnedItemService.queryListByReturnId(Integer.parseInt(pay.getChangeBillNo()));
//                    AddPayable addPayAble = purchaseReturnedService.getAddPayAble(purchaseReturned, purchaseReturnedItems);
//                    paymentItemService.addPaymentItem(addPayAble);
//                } else if (changeBillType == BillTypeEnum.adjustment.ordinal()) {
//                    payableService.adjustItem(pay, payment);
//                }
//            }
//        }
//    }
//
//    private void updatePayment(Integer[] ids, String state) {
//        for (Integer id : ids) {
//            Payment payment = paymentMapper.getById(id);
//            Assert.isTrue(PayStateEnum.wait_pay.name().equals(payment.getState()),"该订单已支付不可支付");
//            payment.setState(state);
//            payment.setPayTime(LocalDateTime.now());
//            UserDto user = ThreadLocalUtils.getUser();
//            payment.setPayUser(user.getLastName());
//            payment.setPayUserId(user.getId());
//            payment.setMemo("System");
//            paymentMapper.update(payment);
//        }
//    }
}