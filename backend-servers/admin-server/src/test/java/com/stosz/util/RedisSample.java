package com.stosz.util;

import com.stosz.test.common.JUnitBaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;

import java.util.List;
import java.util.stream.Stream;

public class RedisSample extends JUnitBaseTest {

	public static final Logger logger = LoggerFactory.getLogger(RedisSample.class);

	@Autowired
	StringRedisTemplate template;

	@Test
	public void redisTest() {

		List<RedisClientInfo> lst = template.getClientList();
		logger.info("template client list is :{} - template:\n{}", lst, template);
	}

	public static void main(String[] args) {
		Stream.of(AopProxyUtils.class.getMethods()).forEach(item -> {
			logger.info(item.getName());
		});
	}
}
