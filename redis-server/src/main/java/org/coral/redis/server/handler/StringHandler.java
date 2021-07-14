package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.perfmon.PerfmonCounters;
import org.coral.redis.server.CommandSign;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.StorageProxyString;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

import java.nio.charset.StandardCharsets;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
public class StringHandler {
	public static PerfmonCounters perfmonCounters = PerfmonCounters.getInstance();

	/**
	 * *3
	 * $3
	 * set
	 * $1
	 * 1
	 * $1
	 * 1
	 * +OK
	 *
	 * @param msgReq
	 * @return
	 */
	public static RedisMessage processSet(RedisMessage msgReq) {
		Stopwatch stopwatch = perfmonCounters.getTx().begin();

		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		FullBulkStringRedisMessage cmd = (FullBulkStringRedisMessage) message.children().get(0);
		FullBulkStringRedisMessage key = (FullBulkStringRedisMessage) message.children().get(1);
		FullBulkStringRedisMessage value = (FullBulkStringRedisMessage) message.children().get(2);
		int expire = -1;
		if (message.children().size() > 4) {
			expire = getExpire(message);
		}
		String keyStr = RedisMsgUtils.getString(key);
		String valueStr = RedisMsgUtils.getString(value);
		StorageProxyString.set(keyStr.getBytes(StandardCharsets.UTF_8),
				valueStr.getBytes(StandardCharsets.UTF_8), expire);
		stopwatch.end();
		return RedisMessageFactory.buildOK();
	}

	public static int getExpire(ArrayRedisMessage redisMessage) {
		if (redisMessage.children().size() < 4) {
			return -1;
		}
		for (int i = 3; i < redisMessage.children().size(); i++) {
			FullBulkStringRedisMessage msg = (FullBulkStringRedisMessage) redisMessage.children().get(i);
			String msgStr = RedisMsgUtils.getString(msg);
			if (msgStr.toUpperCase().equals(CommandSign.EX)) {
				FullBulkStringRedisMessage msgValue = (FullBulkStringRedisMessage) redisMessage.children().get(i + 1);
				String msgValueStr = RedisMsgUtils.getString(msgValue);
				return Integer.parseInt(msgValueStr);
			}
		}
		return -1;
	}

	public long getPExpire(ArrayRedisMessage redisMessage) {
		if (redisMessage.children().size() < 4) {
			return -1;
		}
		return 0;
	}

	public boolean isNx(ArrayRedisMessage redisMessage) {
		if (redisMessage.children().size() < 4) {
			return false;
		}
		return false;
	}

	public boolean isXx(ArrayRedisMessage redisMessage) {
		if (redisMessage.children().size() < 4) {
			return false;
		}
		return false;
	}

	public static RedisMessage processGet(RedisMessage msgReq) {
		Stopwatch stopwatch = perfmonCounters.getTx().begin();
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		FullBulkStringRedisMessage cmd = (FullBulkStringRedisMessage) message.children().get(0);
		FullBulkStringRedisMessage key = (FullBulkStringRedisMessage) message.children().get(1);
		String keyStr = RedisMsgUtils.getString(key);
		byte[] valueData = StorageProxyString.get(keyStr.getBytes(StandardCharsets.UTF_8));
		stopwatch.end();
		return RedisMessageFactory.buildData(valueData);
	}
}
