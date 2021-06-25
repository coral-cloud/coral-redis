package org.greap.redis.service;

import org.greap.redis.server.RedisServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wuhao
 * @createTime 2021-06-25 17:24:00
 */
@Component
public class RedisService {
	private RedisServer redisServer = null;

	@PostConstruct
	public void start() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int port = 6399;
					System.out.println("start RedisService: " + port);
					redisServer = new RedisServer();
					redisServer.bind(port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();

	}
}
