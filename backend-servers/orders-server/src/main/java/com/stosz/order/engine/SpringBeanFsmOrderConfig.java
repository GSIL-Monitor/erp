package com.stosz.order.engine;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.handle.FsmHandleExecutor;
import com.stosz.order.ext.model.Orders;
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
 * 订单状态机配置；
 */

@Configuration
public class SpringBeanFsmOrderConfig {

	@Resource
	private FsmHandleExecutor orderFsmHandleExecutor;

	@Resource
	private FsmHistoryService orderFsmHistoryService;


	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Bean
	public FsmHistoryService orderFsmHistoryService(@Autowired @Qualifier("orderFsmHistoryMapper")  IFsmHistoryMapper pcFsmHistoryMapper){




		FsmHistoryService history = new FsmHistoryService();
		history.setFsmHistoryDao(pcFsmHistoryMapper);
		return history;
	}


	@Bean
	public FsmHandleExecutor orderFsmHandleExecutor(){
		//new 而不是用注入，为了避免代理影响事务；
		FsmHandleExecutor executor = new FsmHandleExecutor();
		return executor;
	}


	//====================   Fsm order   ===================================
	@Value("${order.fsm.order.file}")
	private String orderFsmOrderFile;

	@Qualifier("orderFsmProxyService")
	@Bean
	@Resource
	public FsmProxyService<Orders> orderFsmProxyService() throws Exception {

		FsmProxyService<Orders> fsm = new FsmProxyService<>();

		FsmStateContext context = new FsmStateContext();
		context.setXmlFile(orderFsmOrderFile);
		context.afterPropertiesSet();

		fsm.setFsmHandleExecutor(orderFsmHandleExecutor);
		fsm.setFsmHistoryService(orderFsmHistoryService);
		fsm.setFsmStateContent(context);


		//记录状态机对应的event枚举和 state枚举
//		|com.stosz.order.ext.enums.OrderStateEnum|com.stosz.order.ext.enums.OrderEventEnum

		Optional.ofNullable(stringRedisTemplate).ifPresent(item->{
			stringRedisTemplate.opsForValue().set("fsm.Orders.eventEnum","com.stosz.order.ext.enums.OrderEventEnum");
			stringRedisTemplate.opsForValue().set("fsm.Orders.stateEnum","com.stosz.order.ext.enums.OrderStateEnum");
		});

		return fsm;
	}



}
