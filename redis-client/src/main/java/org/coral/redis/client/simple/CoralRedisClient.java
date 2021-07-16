package org.coral.redis.client.simple;

import org.coral.redis.client.perfmon.RedisPerfmonCounters;
import org.helium.perfmon.Stopwatch;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * @author wuhao
 * @createTime 2021-06-15 09:48:00
 */
public class CoralRedisClient {
	private Jedis jedis = null;

	public CoralRedisClient(String ip, int port) {
		jedis = new Jedis(ip, port);
	}

	public void set(byte[] key, byte[] value, long ttl) {
		Stopwatch stopwatch = RedisPerfmonCounters.getInstance("set").getTx().begin();
		try {
			if (ttl > 0){
				SetParams setParams = new SetParams();
				setParams.ex(ttl);
				jedis.set(key, value, setParams);
			} else {
				jedis.set(key, value);
			}
			stopwatch.end();
		} catch (Exception e) {
			e.printStackTrace();
			stopwatch.fail(e.getMessage());
		}

	}

	public byte[] get(byte[] key) {
		Stopwatch stopwatch = RedisPerfmonCounters.getInstance("get").getTx().begin();
		try {
			byte[] value = jedis.get(key);
			stopwatch.end();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			stopwatch.fail(e.getMessage());
		}
		return null;
	}

	public void close() {
		jedis.close();
	}
}
