package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.perfmon.PerfmonCounters;
import org.coral.redis.server.CommandSign;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.StorageProxyString;
import org.coral.redis.storage.StorageProxyZSet;
import org.coral.redis.storage.entity.RcpZSetRow;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
public class ZSetHandler {
	public static PerfmonCounters perfmonCounters = PerfmonCounters.getInstance();

	/**
	 * processZAdd
	 *
	 * @param msgReq
	 * @return
	 */
	public static RedisMessage processZAdd(RedisMessage msgReq) {

		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		FullBulkStringRedisMessage cmd = (FullBulkStringRedisMessage) message.children().get(0);
		FullBulkStringRedisMessage keyMsg = (FullBulkStringRedisMessage) message.children().get(1);
		String keyStr = RedisMsgUtils.getString(keyMsg);
		HashMap<byte[], Double> hashMap = new HashMap<>(48);
		for (int i = 2; i < message.children().size(); i = i + 2) {
			FullBulkStringRedisMessage scoreMsg = (FullBulkStringRedisMessage) message.children().get(i);
			Double score = Double.parseDouble(RedisMsgUtils.getString(scoreMsg));
			FullBulkStringRedisMessage valueMsg = (FullBulkStringRedisMessage) message.children().get(i + 1);
			String value = RedisMsgUtils.getString(valueMsg);
			hashMap.put(value.getBytes(), score);
		}
		StorageProxyZSet.zadd(keyStr.getBytes(), hashMap);

		return RedisMessageFactory.buildNum(hashMap.size());
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

	public static RedisMessage processZRange(RedisMessage msgReq) {
		Stopwatch stopwatch = perfmonCounters.getTx().begin();
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		FullBulkStringRedisMessage cmdMsg = (FullBulkStringRedisMessage) message.children().get(0);
		FullBulkStringRedisMessage keyMsg = (FullBulkStringRedisMessage) message.children().get(1);
		FullBulkStringRedisMessage startMsg = (FullBulkStringRedisMessage) message.children().get(2);
		FullBulkStringRedisMessage stopMsg = (FullBulkStringRedisMessage) message.children().get(3);
		String keyStr = RedisMsgUtils.getString(keyMsg);
		String startStr = RedisMsgUtils.getString(startMsg);
		String stopStr = RedisMsgUtils.getString(stopMsg);
		int start = Integer.parseInt(startStr);
		int stop = Integer.parseInt(stopStr);
		List<RcpZSetRow> rcpZSetRows = StorageProxyZSet.zrange(keyStr.getBytes(), start, stop);
		stopwatch.end();
		return RedisMessageFactory.buildArrayData(rcpZSetRows);
	}
}
