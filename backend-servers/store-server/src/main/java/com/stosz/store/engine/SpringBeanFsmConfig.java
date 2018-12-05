package com.stosz.store.engine;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.plat.mapper.IFsmHistoryMapper;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransitStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SpringBeanFsmConfig {

    @Autowired
    private FsmHandleExecutor fsmHandleExecutor;

    @Resource
    private FsmHistoryService fsmHistoryService;

    @Bean
    public FsmHistoryService fsmHistoryService(IFsmHistoryMapper fsmHistoryMapper) {
        FsmHistoryService history = new FsmHistoryService();
        history.setFsmHistoryDao(fsmHistoryMapper);
        return history;
    }

    @Bean
    public FsmHandleExecutor fsmHandleExecutor() {
        FsmHandleExecutor executor = new FsmHandleExecutor();
        return executor;
    }

    private void fillFsmProxyService(FsmProxyService fsm, String fsmXmlFile) throws Exception {
        FsmStateContext context = new FsmStateContext();
        context.setXmlFile(fsmXmlFile);
        context.afterPropertiesSet();
        fsm.setFsmHandleExecutor(fsmHandleExecutor);
        fsm.setFsmHistoryService(fsmHistoryService);
        fsm.setFsmStateContent(context);
    }

    @Qualifier("storeRefundFsmHistoryService")
    @Bean
    public FsmHistoryService storeRefundFsmHistoryService(@Autowired @Qualifier("storeFsmHistoryMapper") IFsmHistoryMapper mapper){
        FsmHistoryService history = new FsmHistoryService();
        history.setFsmHistoryDao(mapper);
        return history;
    }

    // ==================== Fsm ===================================
    @Value("${fsm.transfer.file}")
    private String transferFsmFile;

    @Bean
    @Resource
    public FsmProxyService<Transfer> transferFsmProxyService() throws Exception {
        FsmProxyService<Transfer> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, transferFsmFile);
        return fsm;
    }


    @Value("${fsm.takeStock.file}")
    private String takeStockFsmFile;

    @Bean
    @Resource
    public FsmProxyService<TakeStock> takeStockFsmProxyService() throws Exception {
        FsmProxyService<TakeStock> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, takeStockFsmFile);
        return fsm;
    }

    @Value("${fsm.invalid.file}")
    private String invalidFsmFile;

    @Bean
    @Resource
    public FsmProxyService<Invalid> invalidFsmProxyService() throws Exception {
        FsmProxyService<Invalid> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, invalidFsmFile);
        return fsm;
    }

    @Value("${fsm.transitStock.file}")
    private String transitStockFsmFile;

    @Bean
    @Resource
    public FsmProxyService<TransitStock> transitStockFsmProxyService() throws Exception {
        FsmProxyService<TransitStock> fsm = new FsmProxyService<>();
        fillFsmProxyService(fsm, transitStockFsmFile);
        return fsm;
    }


}
