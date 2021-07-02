package org.coral.redis.service;

import org.coral.redis.server.RedisServer;
import org.coral.redis.storage.expire.StorageExpireTask;

/**
 * @author wuhao
 * @createTime 2021-06-25 17:24:00
 */
public class RedisService {
	private RedisServer redisServer = null;

	public static void run(int port){
		RedisService redisService = new RedisService();
		redisService.startAll(port);
	}
	private void startAll(int port) {
		startTcpServer(port);
		StorageExpireTask.start();
	}

	private void startTcpServer(int port) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					redisServer = new RedisServer();
					redisServer.bind(port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public static void main(String[] args) {

	}

}
