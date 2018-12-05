package com.stosz.order.engine;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.order.ext.model.OrdersRefundBill;
import com.stosz.plat.mapper.IFsmHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 退换货订单状态机配置；
 */

@Configuration
public class SpringBeanFsmRefundConfig {

	@Resource
	private FsmHandleExecutor orderRefundFsmHandleExecutor;
	@Resource
	private FsmHistoryService orderRefundFsmHistoryService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Qualifier("orderRefundFsmHistoryService")
	@Bean
	public FsmHistoryService orderRefundFsmHistoryService(@Autowired @Qualifier("orderFsmHistoryMapper") IFsmHistoryMapper orderRefundMpper){
		FsmHistoryService history = new FsmHistoryService();
		history.setFsmHistoryDao(orderRefundMpper);
		return history;
	}

	@Bean
	public FsmHandleExecutor orderRefundFsmHandleExecutor(){
		//new 而不是用注入，为了避免代理影响事务；
		FsmHandleExecutor executor = new FsmHandleExecutor();
		return executor;
	}


	//====================   Fsm order   ===================================
	@Value("${order.fsm.order.refund.file}")
	private String orderRefundFsmOrderFile;

	@Qualifier("orderRefundFsmProxyService")
	@Bean
	@Resource
	public FsmProxyService<OrdersRefundBill> orderRefundFsmProxyService() throws Exception {
		FsmProxyService<OrdersRefundBill> fsm = new FsmProxyService<OrdersRefundBill>();
		FsmStateContext context = new FsmStateContext();
		context.setXmlFile(orderRefundFsmOrderFile);
		context.afterPropertiesSet();
		fsm.setFsmHandleExecutor(orderRefundFsmHandleExecutor);
		fsm.setFsmHistoryService(orderRefundFsmHistoryService);
		fsm.setFsmStateContent(context);

		Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
			stringRedisTemplate.opsForValue().set("fsm.OrdersRefundBill.eventEnum","com.stosz.order.ext.enums.OrderRefundEventEnum");
			stringRedisTemplate.opsForValue().set("fsm.OrdersRefundBill.stateEnum","com.stosz.order.ext.enums.OrdersRefundStatusEnum");
		});
		return fsm;
	}



}
