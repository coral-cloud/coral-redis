package org.coral.redis.test.cluster;

import org.coral.redis.service.RedisService;
import org.coral.redis.storage.storage.RocksDbPathConfig;

import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-22 10:06:00
 */
public class RedisMasterTest {
	public static void main(String[] args) throws IOException {
		RocksDbPathConfig.getInstance().setBasePath("/tmp/master");
		RedisService.run(6389);

	}
}
