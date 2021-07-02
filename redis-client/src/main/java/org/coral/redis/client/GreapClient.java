package org.coral.redis.client;

import redis.clients.jedis.Jedis;

/**
 * @author wuhao
 * @createTime 2021-06-15 09:48:00
 */
public class GreapClient {
	private Jedis jedis = null;

	public GreapClient() {
		jedis = new Jedis("localhost", 6380);
	}

	public GreapClient(int port) {
		//指定Redis服务Host和port
		jedis = new Jedis("localhost", port);
	}

	public void set(String key, String value, long ttl) {
		jedis.set(key, value);
	}

	public String get(String key) {
		return jedis.get(key);
	}

	public void close() {
		jedis.close();
	}
}
