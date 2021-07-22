package org.coral.redis.slave;

import org.coral.redis.service.RedisService;

import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-22 10:06:00
 */
public class RedisSlaveTest {
	public static void main(String[] args) throws IOException {
		RedisSlave redisSlave = new RedisSlave();
		RedisService.run(6399);
		redisSlave.start("127.0.0.1", 7009);

	}
}
