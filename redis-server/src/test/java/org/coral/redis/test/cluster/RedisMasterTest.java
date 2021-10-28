package org.coral.redis.test.cluster;

import org.coral.redis.service.RedisService;

import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-22 10:06:00
 */
public class RedisMasterTest {
	public static void main(String[] args) throws IOException {
		RedisService.run(6389);

	}
}
