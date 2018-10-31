package com.yangz.tank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TankApplicationTests {

	@Autowired
	StringRedisTemplate redisTemplate;//都是k-v字符串
	@Autowired
	RedisTemplate<Object, Object> objectRedisTemplate;
	/**
	 * 	字符串 (String)，		redisTemplate.opsForValue()
	 * 	散列 (hashes)	, 			redisTemplate.opsForHash()
	 * 	列表 (lists)，		redisTemplate.opsForList()
	 * 	集合 sets)，		redisTemplate.opsForSet()
	 * 	有序集合 (sorted sets)，		redisTemplate.opsForZSet()
	 */
	@Test
	public void contextLoads() {
//		redisTemplate.opsForValue().set("msg","hello redis");

		String msg = redisTemplate.opsForValue().get("msg");
		System.out.println(msg);
	}

}
