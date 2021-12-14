package org.coral.redis.test.cluster;

import org.coral.redis.cluster.RedisSlave;
import org.coral.redis.service.RedisService;
import org.coral.redis.storage.storage.RocksDbPathConfig;

import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-22 10:06:00
 */
public class RedisSlaveTest {
	public static void main(String[] args) throws IOException {
		RedisSlave redisSlave = new RedisSlave();
		RedisService.run(6399);
		RocksDbPathConfig.getInstance().setBasePath("/Users/wuhao/data/code/coral-learning/coral-redis/slave");
		redisSlave.start("127.0.0.1", 6379);

	}
}
