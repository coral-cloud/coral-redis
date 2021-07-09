package org.coral.redis.client.cluster;

import org.coral.redis.client.perfmon.RedisPerfmonCounters;
import org.helium.perfmon.PerformanceCounterFactory;
import org.helium.perfmon.Stopwatch;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wuhao
 * @createTime 2021-06-29 11:33:00
 */
public class RedisClusterUtils {
	private JedisCluster jedisCluster = null;
	RedisPerfmonCounters setPerfmon = null;
	RedisPerfmonCounters getPerfmon = null;

	public RedisClusterUtils(String name, boolean isRedis) {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 最大连接数
		poolConfig.setMaxTotal(300);
		// 最大空闲数
		poolConfig.setMaxIdle(30);
		// 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
		// Could not get a resource from the pool
		poolConfig.setMaxWaitMillis(1000);
		setPerfmon = PerformanceCounterFactory.getCounters(RedisPerfmonCounters.class, name + "get");
		getPerfmon = PerformanceCounterFactory.getCounters(RedisPerfmonCounters.class, name + "set");

		if (isRedis) {
			jedisCluster = new JedisCluster(initRedis(), poolConfig);
		} else {
			jedisCluster = new JedisCluster(initTendis(), poolConfig);
		}

	}

	public Set<HostAndPort> initTendis() {
		Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
		nodes.add(new HostAndPort("10.3.4.111", 51001));
		nodes.add(new HostAndPort("10.3.4.111", 51002));
		nodes.add(new HostAndPort("10.3.4.111", 51003));
		nodes.add(new HostAndPort("10.3.4.111", 51004));
		nodes.add(new HostAndPort("10.3.4.111", 51005));
		nodes.add(new HostAndPort("10.3.4.111", 51006));
		return nodes;
	}

	public Set<HostAndPort> initRedis() {
		Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
		nodes.add(new HostAndPort("10.3.4.111", 7000));
		nodes.add(new HostAndPort("10.3.4.111", 7001));
		nodes.add(new HostAndPort("10.3.4.111", 7002));
		nodes.add(new HostAndPort("10.3.4.111", 7003));
		nodes.add(new HostAndPort("10.3.4.111", 7005));
		nodes.add(new HostAndPort("10.3.4.111", 17006));
		return nodes;
	}

	public void set(byte[] key, byte[] contents) {
		Stopwatch stopwatch = setPerfmon.getTx().begin();
		try {
			jedisCluster.set(key, contents);
			stopwatch.end();
		} catch (Exception e) {
			stopwatch.fail(e.getMessage());
		}

	}

	public byte[] get(byte[] key) {
		Stopwatch stopwatch = getPerfmon.getTx().begin();
		try {
			byte[] bytewalue = jedisCluster.get(key);
			if (bytewalue == null) {
				stopwatch.fail("set value is null");
			} else {
				stopwatch.end();
			}
			return bytewalue;
		} catch (Exception e) {
			stopwatch.fail(e.getMessage());
		}

		return null;
	}

	public static void main(String[] args) {
		RedisClusterUtils redisClusterUtils = new RedisClusterUtils("testt", false);
		redisClusterUtils.set("111".getBytes(StandardCharsets.UTF_8), "222".getBytes(StandardCharsets.UTF_8));
		System.out.println(redisClusterUtils.get("111".getBytes(StandardCharsets.UTF_8)));
	}
}
