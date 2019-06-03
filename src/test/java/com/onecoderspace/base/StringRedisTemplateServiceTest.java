package com.onecoderspace.base;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onecoderspace.base.domain.Role;
import com.onecoderspace.base.domain.User;
import com.onecoderspace.base.util.JacksonHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=BaseApplication.class)
public class StringRedisTemplateServiceTest {

	@Test
	public void test(){
		//Set the cache, it is recommended to set the expiration time for each key
		redisTemplate.opsForValue().set("test", "test", 10, TimeUnit.SECONDS);
		Assert.assertEquals(redisTemplate.opsForValue().get("test"), "test");
		
		redisTemplate.delete("test");//删除某个键
		
		//Operation set
		redisTemplate.opsForSet().add("testSet", "1");
		redisTemplate.opsForSet().add("testSet", "2");
		redisTemplate.opsForSet().add("testSet", "3");
		Set<String> members = redisTemplate.opsForSet().members("testSet");//Get all the values in the set
		for (String string : members) {
			System.err.println(string);
		}
		redisTemplate.opsForSet().remove("testSet", "1","2");//Remove multiple objects from the set
		
		System.err.println("-----------");
		
		//Operation list
		redisTemplate.opsForList().rightPush("testList", "1");
		redisTemplate.opsForList().rightPush("testList", "2");
		redisTemplate.opsForList().rightPush("testList", "3");
		List<String> list = redisTemplate.opsForList().range("testList", 0, -1);
		for (String string : list) {
			System.err.println(string);
		}
		
		User user = new User();
		user.setId(1);
		user.setName("test");
		objRedisTemplate.opsForValue().set("user", user,10,TimeUnit.SECONDS);
		user = (User) objRedisTemplate.opsForValue().get("user");
		System.err.println(JacksonHelper.toJson(user));
		
		Role role = new Role();
		role.setId(1);
		role.setName("role");
		objRedisTemplate.opsForValue().set("role", role,10,TimeUnit.SECONDS);
		role = (Role) objRedisTemplate.opsForValue().get("role");
		System.err.println(JacksonHelper.toJson(role));
	}
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private RedisTemplate<String, Object> objRedisTemplate;
	
}

