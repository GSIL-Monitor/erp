package com.stosz.tms.cache;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderCache {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public final String key = "tms.async.place.orders";

	/**
	 * 添加到异步执行的列表
	 * @param parcelId
	 */
	public void addAsyncOrder(int parcelId) {
		stringRedisTemplate.opsForSet().add(key, String.valueOf(parcelId));
	}

	/**
	 * 检查是否正在异步执行
	 * @param parcelId
	 */
	public boolean existsAsyncOrder(int parcelId) {
		return stringRedisTemplate.opsForSet().isMember(key, String.valueOf(parcelId));
	}

	/**
	 * 移除异步处理
	 * @param parcelId
	 */
	public void remove(int parcelId) {
		stringRedisTemplate.opsForSet().remove(key, String.valueOf(parcelId));
	}

	/**
	 * 查询正在异步抛送的包裹ID
	 * @return
	 */
	public Set<Integer> queryAysncOrder() {
		Set<String> set = stringRedisTemplate.opsForSet().members(key);
		return set.stream().map(Integer::valueOf).collect(Collectors.toSet());
	}

}
