package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.StorageProxyString;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

/**
 * StringHandler
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
public class StringHandler {


	/**
	 * @param msgReq
	 * @return
	 */
	public static RedisMessage processSet(RedisMessage msgReq) {
		Stopwatch stopwatch = RedisCounters.getInstance("set").getTx().begin();
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		int expire = RedisMsgUtils.getExpire(message);
		byte[] key = RedisMsgUtils.getBytes(message.children().get(1));
		byte[] value = RedisMsgUtils.getBytes(message.children().get(2));
		StorageProxyString.set(key, value, expire);
		stopwatch.end();
		return RedisMessageFactory.buildOK();
	}


	public static RedisMessage processGet(RedisMessage msgReq) {
		Stopwatch stopwatch = RedisCounters.getInstance("get").getTx().begin();
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		byte[] key = RedisMsgUtils.getBytes(message.children().get(1));
		byte[] valueData = StorageProxyString.get(key);
		stopwatch.end();
		return RedisMessageFactory.buildData(valueData);
	}
}
