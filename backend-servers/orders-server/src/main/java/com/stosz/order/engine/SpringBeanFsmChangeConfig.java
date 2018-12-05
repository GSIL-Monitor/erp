package com.stosz.order.engine;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.order.ext.enums.OrderRmaBillEventEnum;
import com.stosz.order.ext.enums.OrdersRmaStateEnum;
import com.stosz.order.ext.model.OrdersRmaBill;
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
public class SpringBeanFsmChangeConfig {

	@Resource
	private FsmHandleExecutor orderChangeFsmHandleExecutor;

	@Resource
	private FsmHistoryService orderChangeFsmHistoryService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Qualifier("orderChangeFsmHistoryService")
	@Bean
	public FsmHistoryService orderChangeFsmHistoryService(@Autowired @Qualifier("orderFsmHistoryMapper") IFsmHistoryMapper orderChangeFsmHistoryMapper){
		FsmHistoryService history = new FsmHistoryService();
		history.setFsmHistoryDao(orderChangeFsmHistoryMapper);
		return history;
	}


	@Bean
	public FsmHandleExecutor orderChangeFsmHandleExecutor(){
		//new 而不是用注入，为了避免代理影响事务；
		FsmHandleExecutor executor = new FsmHandleExecutor();
		return executor;
	}


	//====================   Fsm order   ===================================
	@Value("${order.fsm.order.change.file}")
	private String orderChangeFsmOrderFile;

	@Qualifier("orderChangeFsmProxyService")
	@Bean
	@Resource
	public FsmProxyService<OrdersRmaBill> orderChangeFsmProxyService() throws Exception {

		FsmProxyService<OrdersRmaBill> fsm = new FsmProxyService<OrdersRmaBill>();

		FsmStateContext context = new FsmStateContext();
		context.setXmlFile(orderChangeFsmOrderFile);
		context.afterPropertiesSet();
		fsm.setFsmHandleExecutor(orderChangeFsmHandleExecutor);
		fsm.setFsmHistoryService(orderChangeFsmHistoryService);
		fsm.setFsmStateContent(context);
		Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
			stringRedisTemplate.opsForValue().set("fsm.OrdersRmaBill.eventEnum","com.stosz.order.ext.enums.OrderRmaBillEventEnum");
			stringRedisTemplate.opsForValue().set("fsm.OrdersRmaBill.stateEnum","com.stosz.order.ext.enums.OrdersRmaStateEnum");
		});
		return fsm;
	}



}
