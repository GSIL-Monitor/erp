package com.stosz.purchase.service.finance;

import com.stosz.plat.utils.BeanUtil;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.ext.model.finance.PaymentItem;
import com.stosz.purchase.mapper.finance.PaymentItemMapper;
import com.stosz.purchase.utils.IBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
public class PaymentItemService {

    @Resource
    private PaymentItemMapper mapper;

    public void insert(PaymentItem item) {
        item.setCreateAt(LocalDateTime.now());
        item.setUpdateAt(LocalDateTime.now());
        mapper.insert(item);
    }

    /**
     * 根据 （单据id和单据类型）唯一删除
     *
     * @param goalBillNo
     * @param goalBillType
     */

    public void delete(String goalBillNo, Integer goalBillType) {
        mapper.deleteByNoAndType(goalBillNo, goalBillType);
    }

    public void emptyPayId(Integer payIds) {
        mapper.emptyPayId(payIds);
    }

    public void deleteAjust(Integer payId,Integer type) {
        mapper.deleteAjust(payId,type);
    }


    public void update(PaymentItem paymentItem) {
        mapper.update(paymentItem);
    }

    /**
     * 根据付款单查询
     *
     * @param paymentId
     * @return
     */
    public List<PaymentItem> getListByPaymentId(Integer paymentId) {

        return mapper.getListByPaymentId(paymentId);
    }

    /**
     * 根据（目标单据id和单据类型）查询
     *
     * @param
     * @return
     */
    public List<PaymentItem> getListByNoAndType(String goalBillNo, Integer goalBillType) {

        return mapper.getListByNoAndType(goalBillNo, goalBillType);
    }

    /**
     * 根据（发生单据id）查询
     *
     * @param
     * @return
     */
    public List<PaymentItem> getListByChangeNo(String changeNo) {
        return mapper.getListByChangeNo(changeNo);
    }

    /**
     * 根据（单据明细id和发生单据类型）查询
     *
     * @param
     * @return
     */
    public PaymentItem getOneByChange(String itemNo, Integer changeBillType) {

        return mapper.getOneByChange(itemNo, changeBillType);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addPaymentItem(Payable addPayable) {
//        addPayable.setMemo("System");
//        IBeanUtil.checkObjFieldIsNotNull(addPayable);
//        List<Item> items = addPayable.getItems();
//        for (Item item : items) {
//            PaymentItem paymentItem = new PaymentItem();
//            BeanUtil.copy(addPayable, paymentItem);
//            BeanUtil.copy(item, paymentItem);
//            insert(paymentItem);
//        }
    }

    public Integer count(PaymentItem paymentItem) {
        return mapper.count(paymentItem);
    }

    public List<PaymentItem> query(PaymentItem paymentItem) {
        return mapper.query(paymentItem);
    }

}
