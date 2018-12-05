package com.stosz.purchase.engine;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.plat.mapper.IFsmHistoryMapper;
import com.stosz.purchase.ext.model.*;
import com.stosz.purchase.ext.model.finance.Payable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Optional;

@Configuration
public class SpringBeanFsmPurchaseConfig {

    @Resource
    private FsmHandleExecutor purchaseFsmHandleExecutor;

    @Resource
    private FsmHistoryService purchaseFsmHistoryService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public FsmHistoryService purchaseFsmHistoryService(IFsmHistoryMapper purchaseFsmHistoryMapper) {
        FsmHistoryService history = new FsmHistoryService();
        history.setFsmHistoryDao(purchaseFsmHistoryMapper);
        return history;
    }

    @Bean
    public FsmHandleExecutor purchaseFsmHandleExecutor() {
        FsmHandleExecutor executor = new FsmHandleExecutor();
        return executor;
    }

    private void fillFsmProxyService(FsmProxyService fsm, String fsmXmlFile) throws Exception {
        FsmStateContext context = new FsmStateContext();
        context.setXmlFile(fsmXmlFile);
        context.afterPropertiesSet();
        fsm.setFsmHandleExecutor(purchaseFsmHandleExecutor);
        fsm.setFsmHistoryService(purchaseFsmHistoryService);
        fsm.setFsmStateContent(context);
    }

    // ==================== Fsm purchase ===================================
    @Value("${purchase.fsm.purchase.file}")
    private String purchaseFsmFile;

    @Bean
    @Resource
    public FsmProxyService<Purchase> purchaseFsmProxyService() throws Exception {
        FsmProxyService<Purchase> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, purchaseFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.Purchase.eventEnum","com.stosz.purchase.ext.enums.PurchaseEvent");
            stringRedisTemplate.opsForValue().set("fsm.Purchase.stateEnum","com.stosz.purchase.ext.enums.PurchaseState");
        });
        return fsm;
    }

    // ==================== Fsm purchaseItem ===================================
    @Value("${purchase.fsm.purchaseItem.file}")
    private String purchaseItemFsmFile;

    @Bean
    @Resource
    public FsmProxyService<PurchaseItem> purchaseItemFsmProxyService() throws Exception {
        FsmProxyService<PurchaseItem> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, purchaseItemFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.PurchaseItem.eventEnum","com.stosz.purchase.ext.enums.PurchaseItemEvent");
            stringRedisTemplate.opsForValue().set("fsm.PurchaseItem.stateEnum","com.stosz.purchase.ext.enums.PurchaseItemState");
        });
        return fsm;
    }

    @Value("${purchase.fsm.purchaseReturned.file}")
    private String purchaseReturnedFsmFile;

    @Bean
    @Resource
    public FsmProxyService<PurchaseReturned> purchaseReturnedFsmProxyService() throws Exception {
        FsmProxyService<PurchaseReturned> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, purchaseReturnedFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.PurchaseReturned.eventEnum","com.stosz.purchase.ext.enums.PurchaseReturnedEvent");
            stringRedisTemplate.opsForValue().set("fsm.PurchaseReturned.stateEnum","com.stosz.purchase.ext.enums.purchaseReturnState");
        });
        return fsm;
    }
    
    
    @Value("${purchase.fsm.purchaseReturnedItem.file}")
    private String purchaseReturnedItemFsmFile;

    @Bean
    @Resource
    public FsmProxyService<PurchaseReturnedItem> purchaseReturnedItemFsmProxyService() throws Exception {
        FsmProxyService<PurchaseReturnedItem> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, purchaseReturnedItemFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.PurchaseReturnedItem.eventEnum","com.stosz.purchase.ext.enums.PurchaseReturnedItemEvent");
            stringRedisTemplate.opsForValue().set("fsm.PurchaseReturnedItem.stateEnum","com.stosz.purchase.ext.enums.PurchaseReturnItemState");
        });
        return fsm;
    }


    @Value("${purchase.fsm.errorGoods.file}")
    private String errorGoodsFsmFile;

    @Bean
    @Resource
    public FsmProxyService<ErrorGoods> errorGoodsFsmProxyService() throws Exception {
        FsmProxyService<ErrorGoods> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, errorGoodsFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.ErrorGoods.eventEnum","com.stosz.purchase.ext.enums.ErrorGoodsEvent");
            stringRedisTemplate.opsForValue().set("fsm.ErrorGoods.stateEnum","com.stosz.purchase.ext.enums.ErrorGoodsState");
        });
        return fsm;
    }


    @Value("${purchase.fsm.errorGoodsItem.file}")
    private String errorGoodsItemFsmFile;

    @Bean
    @Resource
    public FsmProxyService<ErrorGoodsItem> errorGoodsItemFsmProxyService() throws Exception {
        FsmProxyService<ErrorGoodsItem> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, errorGoodsItemFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.ErrorGoodsItem.eventEnum","com.stosz.purchase.ext.enums.ErrorGoodsItemEvent");
            stringRedisTemplate.opsForValue().set("fsm.ErrorGoodsItem.stateEnum","com.stosz.purchase.ext.enums.ErrorGoodsItemState");
        });
        return fsm;
    }


    @Value("${purchase.fsm.payable.file}")
    private String payableFsmFile;

    @Bean
    @Resource
    public FsmProxyService<Payable> payableFsmProxyService() throws Exception {
        FsmProxyService<Payable> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, payableFsmFile);
        Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
            stringRedisTemplate.opsForValue().set("fsm.Payable.eventEnum","com.stosz.purchase.ext.enums.PayableEventEnum");
            stringRedisTemplate.opsForValue().set("fsm.Payable.stateEnum","com.stosz.purchase.ext.enums.PayableStateEnum");
        });
        return fsm;
    }
}
