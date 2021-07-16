package org.coral.redis.test;

import org.coral.redis.service.RedisService;
import org.helium.perfmon.MonitorServer;

public class RedisServerTest {

	public static void main(String[] args) {
		RedisService.run(6399);
		MonitorServer.run(8081);
	}
}