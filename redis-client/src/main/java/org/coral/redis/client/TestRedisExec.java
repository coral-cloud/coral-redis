package org.coral.redis.client;

import java.nio.charset.StandardCharsets;

/**
 * @author wuhao
 * @createTime 2021-06-29 11:49:00
 */
public class TestRedisExec {
	public void testRedis(int tcount, int count, String caseName, boolean isRedis) {
		for (int t = 0; t < tcount; t++) {
			int finalT = t;
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					String key = finalT + "t1234567890t:";
					byte[] contents = new byte[1024];
					RedisClusterUtils redisClusterUtils = new RedisClusterUtils(caseName, isRedis);
					long time = System.currentTimeMillis();
					System.out.println("testTendis start: " + time);
					for (int i = 0; i < count; i++) {
						byte[] keybytes = (key + i).getBytes(StandardCharsets.UTF_8);
						redisClusterUtils.set(keybytes, contents);
						redisClusterUtils.get(keybytes);
					}
					System.out.println("testTendis end: " + (System.currentTimeMillis() - time));
				}
			});
			thread.start();
		}
	}
}
