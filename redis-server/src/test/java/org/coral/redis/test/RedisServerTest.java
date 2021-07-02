package org.coral.redis.test;

import org.coral.redis.service.RedisService;

public class RedisServerTest {

	public static void main(String[] args) {
		RedisService.run(6399);
	}
}