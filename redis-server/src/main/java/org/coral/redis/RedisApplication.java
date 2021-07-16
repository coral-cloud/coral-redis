package org.coral.redis;

import org.coral.redis.service.RedisService;
import org.helium.perfmon.MonitorServer;

public class RedisApplication {

    public static void main(String[] args) {
		RedisService.run(6399);
		MonitorServer.run(8081);
    }

}
