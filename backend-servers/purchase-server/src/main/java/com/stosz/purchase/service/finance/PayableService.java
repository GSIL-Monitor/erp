package com.stosz.purchase.service.finance;

import com.google.common.collect.Lists;
import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.enums.PayStateEnum;
import com.stosz.plat.common.MBox;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IPartnerService;
import com.stosz.product.ext.service.IProductService;
import com.stosz.purchase.ext.enums.AdjustTypeEnum;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.enums.PayableEventEnum;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.ext.model.finance.*;
import com.stosz.purchase.mapper.finance.PayableMapper;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseReturnedItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author  xiongchengyang
 *  应付款的service
 */
@Service
public class PayableService {

    @Resource
    private PayableMapper payableMapper;

    @Resource
    private PaymentService paymentService;

    @Resource
    private PurchaseItemService purchaseItemService;

    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;

    @Resource
    private IProductService iProductService;

    @Resource
    private AmountAdjustService amountAdjustService;

    @Resource
    private PaymentItemService paymentItemService;

    @Resource
    private FsmProxyService<Payable> payableFsmProxyService;

    @Resource
    private FsmHistoryService payableFsmHistoryService;

    @Resource
    private IPartnerService iPartnerService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新增应付款单
     * @param payable 应付款单
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Payable payable) {
        Assert.notNull(payable,"要新增的应付款单不允许为空！");
        Assert.hasLength(payable.getChangeBillNo(),"发生单据编号不允许为空！");
        Assert.notNull(payable.getChangeBillType(),"发生单据类型不允许为空！");
        Assert.notNull(payable.getAmount(),"发生金额不允许为空！");
        Assert.notNull(payable.getGoalBillNo(),"目标单据编号不允许为空！");
        Assert.notNull(payable.getGoalBillType(),"目标单据类型不允许为空！");
        Assert.notNull(payable.getGoalBillType(),"目标单据类型不允许为空！");
        Assert.notNull(payable.getQuantity(),"发生数量不允许为空！");
        Assert.notNull(payable.getPartnerId(),"合作伙伴不允许为空！");
        Assert.isTrue(payable.getBuyerId()!=null && StringUtils.isNotBlank(payable.getBuyerName()),"采购员不允许为空！");
        Assert.hasLength(payable.getBuyerAccount(),"买手账号不允许为空！");
        payableMapper.insert(payable);
        processEvent(payable,PayableEventEnum.create,"生成应付款单",MBox.getLoginUserName());
        logger.info("生成应付款单{}成功！",payable);

    }

    /**
     * 修改应付款单
     * @param payable 应付款单
     */
    public void update(Payable payable){
        Assert.notNull(payable,"要修改的应付款单不允许为空！");
        Payable payableDB = payableMapper.getById(payable.getId());
        Assert.notNull(payableDB,"要修改的数据在数据库中不存在！");
        payableMapper.update(payable);
        logger.info("将应付款单{}修改为{}成功！",payableDB,payable);
    }

    /**
     * 根据id获取对应的应付款单
     * @param id id
     * @return 应付款单
     */
    public Payable getById(Integer id) {
        return payableMapper.getById(id);
    }

    /**
     * 状态机的处理
     * @param payable 应付款单
     * @param payableEvent 状态机内容
     * @param memo 备注
     * @param uid 用户id
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processEvent(Payable payable, PayableEventEnum payableEvent, String memo, String uid) {
        Assert.notNull(payable, "要修改的应付款单不允许为空！");
        if (StringUtils.isBlank(uid)) {
            uid = MBox.getSysUser();
        }
        this.payableFsmProxyService.processEvent(payable, payableEvent, uid, LocalDateTime.now(), memo);
    }

    public List<Payable> findByParam(Payable payable){
        return payableMapper.findByParam(payable);
    }

    public Integer count(Payable payable){
        return payableMapper.countByParam(payable);
    }

    /**
     * 获取列表页
     *
     * @param payable
     */
    public List<FinancePay> findList(Payable payable) {

        List<Payable> payables = payableMapper.findByParam(payable);
        List<Integer> partnerIds = payables.stream().map(Payable::getPartnerId).collect(Collectors.toList());
        List<Partner> partnerList = iPartnerService.findByIds(partnerIds);
        Map<Integer,Partner> partnerMap = partnerList.stream().collect(Collectors.toMap(Partner::getId, Function.identity()));
        ArrayList<FinancePay> financePays = Lists.newArrayList();
        for (Payable payableOne : payables) {
            FinancePay financePay = new FinancePay();
            BeanUtil.copy(payableOne, financePay);
            Integer changeBillType = payableOne.getChangeBillType();
            Integer goalBillType = payableOne.getGoalBillType();
            List<FinanceItemSku> financeItemSkus;
            if (BillTypeEnum.adjustment.ordinal() == changeBillType) {
                financeItemSkus = findListByType(financePay, goalBillType, payableOne.getGoalBillNo());
            } else {
                financeItemSkus = findListByType(financePay, changeBillType, payableOne.getChangeBillNo());
            }
            String spu = financePay.getProductTitle();
            Assert.notNull(spu, "为找到对应spu");
            List<FinanceItemSku> skuItems = getSkuItems(financeItemSkus);
            financePay.setFinanceItemSkus(skuItems);
            Product product = iProductService.getBySpu(spu);
            if(product != null){
                financePay.setProductTitle(product.getTitle());
                financePay.setMainImageUrl(product.getMainImageUrl());
            }
            financePay.setPartner(partnerMap.get(financePay.getPartnerId()));
            financePays.add(financePay);
        }
        return financePays;
    }

    /**
     * 根据付款单id获取到对应的应付款单
     * @param paymentIds 付款单id
     * @return 匹配结果
     */
    public List<Payable> findByPaymentIds(Integer[] paymentIds){
        Assert.notNull(paymentIds,"要查询的付款单id不允许为空！");
        return payableMapper.findByPaymentIds(paymentIds);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addPayable(Payable addPayable) {
        insert(addPayable);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addPayableList(List<Payable> addPayableList) {
        for(Payable addPayable:addPayableList){
            insert(addPayable);
        }
    }

    /**
     * 根据单据类型获取所有明细及spu
     *
     * @param changeBillType
     */
    public List<FinanceItemSku> findListByType(FinancePay financePay, Integer changeBillType, String changeBillNo) {
        List<FinanceItemSku> financeItemSkus = new ArrayList<>();

        switch (changeBillType) {
            case 0:
                List<PurchaseItem> purchaseItems = purchaseItemService.findByPurchaseId(Integer.parseInt(changeBillNo));
                for (PurchaseItem purchaseItem : purchaseItems) {
                    FinanceItemSku financeItemSku = new FinanceItemSku(purchaseItem.getSku(),
                            purchaseItem.getSkuTitle(),
                            purchaseItem.getQuantity());
                    financePay.setProductTitle(purchaseItem.getSpu());
                    financeItemSkus.add(financeItemSku);
                }
                break;
            case 1:
                List<PurchaseReturnedItem> purchaseReturnedItems = purchaseReturnedItemService.queryListByReturnId(Integer.parseInt(changeBillNo));
                for (PurchaseReturnedItem purchaseReturnedItem : purchaseReturnedItems) {
                    financePay.setProductTitle(purchaseReturnedItem.getSpu());
                    FinanceItemSku financeItemSku = new FinanceItemSku(purchaseReturnedItem.getSku(),
                            purchaseReturnedItem.getSkuTitle(),
                            purchaseReturnedItem.getReturnedQty());
                    financeItemSkus.add(financeItemSku);
                }
                break;
        }
        return financeItemSkus;
    }

    /**
     * 合并数量
     *
     * @param financeItemSkus
     */
    private List<FinanceItemSku> getSkuItems(List<FinanceItemSku> financeItemSkus) {
        Map<String, FinanceItemSku> map = new HashMap<>();
        for (FinanceItemSku financeItemSku : financeItemSkus) {
            String sku = financeItemSku.getSku();
            Integer qty = financeItemSku.getQty();
            if (map.containsKey(sku)) {
                FinanceItemSku financeItemSku1 = map.get(sku);
                qty = qty + financeItemSku1.getQty();
                financeItemSku.setQty(qty);
            }
            map.put(sku, financeItemSku);
        }
        List<FinanceItemSku> financeItemSkuList = new ArrayList<>();
        for (Map.Entry<String, FinanceItemSku> entry : map.entrySet()) {
            financeItemSkuList.add(entry.getValue());
        }
        return financeItemSkuList;
    }

    /**
     * 生成付款单
     *
     * @param payables
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Payment generate(List<Payable> payables) {

        Payment payment = new Payment();
        BeanUtils.copyProperties(payables.get(0), payment);
        this.setPaymentPayType(payables.get(0), payment);
        BigDecimal amount = new BigDecimal(0);
        for (Payable pay : payables) {
            amount = amount.add(pay.getPayAmount());
        }
        payment.setAmount(amount);
//        paymentService.insert(payment);
        for (Payable pay : payables) {
            pay.setPaymentId(payment.getId());
            payableMapper.update(pay);
            if (pay.getAmount().compareTo(pay.getPayAmount()) > 0) {
                // 有调整
                this.adjustHelper(pay, payment);
            }
        }
        return payment;
    }

    /**
     * 调整表操作
     *
     * @param pay
     * @param payment
     */
    private void adjustHelper(Payable pay, Payment payment) {
        BigDecimal adjustAmount = pay.getPayAmount().subtract(pay.getAmount());
        AmountAdjust adjust = new AmountAdjust();
        BeanUtils.copyProperties(pay, adjust);
        adjust.setPaymentId(payment.getId());
        adjust.setAmount(adjustAmount);
        //暂时全按收款类型
        adjust.setType(AdjustTypeEnum.collect.ordinal());
        amountAdjustService.insert(adjust);

        //应付表回插一条调整记录
        pay.setAmount(adjustAmount);
        pay.setPayAmount(adjustAmount);
        pay.setChangeBillNo(adjust.getId().toString());
        pay.setChangeBillType(BillTypeEnum.adjustment.ordinal());
        payableMapper.insert(pay);

        //this.adjustItem(pay, payment);
    }

    /**
     * 调整明细操作
     *
     * @param pay
     * @param payment
     */
    public void adjustItem(Payable pay, Payment payment) {
        List<PaymentItem> itemList = paymentItemService.getListByPaymentId(payment.getId());
        int size = itemList.size();
        BigDecimal last = pay.getAmount(), temp;
        for (int i = 0; i < size; ++i) {
            itemList.get(i).setPaymentId(payment.getId());
            paymentItemService.update(itemList.get(i));

            //(a-b)*c/a
            temp = (pay.getPayAmount().subtract(pay.getAmount()))
                    .multiply(itemList.get(i).getAmount())
                    .divide(pay.getAmount(), 2, BigDecimal.ROUND_HALF_UP);

            if (i == size - 1) {
                itemList.get(i).setAmount(last);
                paymentItemService.insert(itemList.get(i));
            } else {
                itemList.get(i).setAmount(temp);
                paymentItemService.insert(itemList.get(i));
                last = last.subtract(temp);
            }

        }
    }

    public void emptyPayId(Integer payId) {
        payableMapper.emptyPayId(payId);
    }

    public void deleteAjust(Integer payId, Integer type) {
        payableMapper.deleteAjust(payId, type);
    }

    /**
     * 拒绝
     *
     * @param payables
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void reject(List<Payable> payables) {
        for (Payable payable : payables) {

            Payable pay = payableMapper.getById(payable.getId());
            //删除明细
            paymentItemService.delete(pay.getGoalBillNo(), pay.getGoalBillType());

            //删除应付表记录
            payableMapper.delete(pay.getId());

            //todo 通知发生单据方
            Integer[] ids=new Integer[1];
            ids[0]=payable.getId();
//            paymentService.sendPayStateNotice(ids, PayStateEnum.refuse.display());
        }
    }

    /**
     * 根据单据类型 设置 付款类型
     *
     * @param pay
     * @param payment
     */
    private void setPaymentPayType(Payable pay, Payment payment) {
        switch (BillTypeEnum.fromId(pay.getGoalBillType())) {
            case adjustment:
                break;
            case purchase:
            case recovery:
//                payment.setPayType(PayTypeEnum.purchase_amount.ordinal());
                break;
            default:
                break;
        }
    }

}
