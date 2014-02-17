/**
 * 
 */
package org.migue.test;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.migue.LocalRedisConfig;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author migue
 * 
 * Test class for "LocalRedisConfig" class
 *
 */

@ContextConfiguration(classes=LocalRedisConfig.class)
@RunWith (SpringJUnit4ClassRunner.class)
public class LocalRedisConfigTest {

	@Inject
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Inject
	private StringRedisTemplate redisTemplate;
	
	@Test
	public void testJedisConnectionFactory() {
		assertNotNull (jedisConnectionFactory);
	}
	
	@Test
	public void testRedisTemplate() {
		assertNotNull(redisTemplate);
	}
}
