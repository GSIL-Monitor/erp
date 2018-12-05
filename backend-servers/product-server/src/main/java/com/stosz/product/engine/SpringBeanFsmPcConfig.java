package com.stosz.product.engine;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.plat.mapper.IFsmHistoryMapper;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.ext.model.ProductZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SpringBeanFsmPcConfig {




	@Resource
	private FsmHandleExecutor pcFsmHandleExecutor;

	@Resource
	private FsmHistoryService pcFsmHistoryService;

	@Bean
	public FsmHistoryService pcFsmHistoryService(IFsmHistoryMapper pcFsmHistoryMapper){
		FsmHistoryService history = new FsmHistoryService();
		history.setFsmHistoryDao(pcFsmHistoryMapper);
		return history;
	}


	@Bean
	public FsmHandleExecutor pcFsmHandleExecutor(){
		FsmHandleExecutor executor = new FsmHandleExecutor();
		return executor;
	}


	private void fillFsmProxyService(FsmProxyService fsm, String fsmXmlFile ) throws Exception {
		FsmStateContext context = new FsmStateContext();
		context.setXmlFile(fsmXmlFile);
		context.afterPropertiesSet();
		fsm.setFsmHandleExecutor(pcFsmHandleExecutor);
		fsm.setFsmHistoryService(pcFsmHistoryService);
		fsm.setFsmStateContent(context);
	}
	//====================   Fsm ProductNew  ===================================
	@Value("${pc.fsm.productNew.file}")
	private String pcFsmProductNewFile;

	@Bean
	@Resource
	public FsmProxyService<ProductNew> pcProductNewFsmProxyService() throws Exception {
		FsmProxyService<ProductNew> fsm = new FsmProxyService<>();
		fillFsmProxyService(fsm,pcFsmProductNewFile);
		return fsm;
	}

	//====================   Fsm Product  ===================================
	@Value("${pc.fsm.product.file}")
	private String pcFsmProductFile;

	@Bean
	@Resource
	public FsmProxyService<Product> pcProductFsmProxyService() throws Exception {
		FsmProxyService<Product> fsm = new FsmProxyService<>();
		fillFsmProxyService(fsm,pcFsmProductFile);
		return fsm;
	}


	//====================   Fsm ProductZone  ===================================
	@Value("${pc.fsm.productZone.file}")
	private String pcFsmProductZoneFile;

	@Bean
	@Resource
	public FsmProxyService<ProductZone> pcProductZoneFsmProxyService() throws Exception{
		FsmProxyService<ProductZone> fsm = new FsmProxyService<>();
		fillFsmProxyService(fsm,pcFsmProductZoneFile);
		return fsm;
	}

}
