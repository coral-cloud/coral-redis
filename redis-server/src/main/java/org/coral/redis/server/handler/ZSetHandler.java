package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.type.CommandSign;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.RcpProxyZSet;
import org.coral.redis.storage.entity.data.RcpZSetRow;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
public class ZSetHandler implements CommandHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZSetHandler.class);
	/**
	 * processZAdd
	 *
	 * @param msgReq
	 * @return
	 */
	public static List<RedisMessage>  processZAdd(RedisMessage msgReq) {

		try {
			ArrayRedisMessage message = (ArrayRedisMessage) msgReq;

			String keyStr = RedisMsgUtils.getString(message.children().get(1));
			HashMap<byte[], Double> hashMap = new HashMap<>(48);
			for (int i = 2; i < message.children().size(); i = i + 2) {
				Double score = Double.parseDouble(RedisMsgUtils.getString(message.children().get(i)));
				String value = RedisMsgUtils.getString(message.children().get(i + 1));
				hashMap.put(value.getBytes(), score);
			}
			RcpProxyZSet.zadd(keyStr.getBytes(), hashMap);

			return Arrays.asList(RedisMessageFactory.buildNum(hashMap.size()));
		} catch (Exception e) {
			LOGGER.error("processZAdd:", e.getMessage());
		}
		return Arrays.asList(RedisMessageFactory.buildError());
	}


	public static List<RedisMessage> processZRange(RedisMessage msgReq) {
		Stopwatch stopwatch = RedisCounters.getInstance("zrange").getTx().begin();
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		String keyStr = RedisMsgUtils.getString(message.children().get(1));
		String startStr = RedisMsgUtils.getString(message.children().get(2));
		String stopStr = RedisMsgUtils.getString(message.children().get(3));
		boolean withScores = false;
		if (message.children().size() > 4){
			String withScoresStr = RedisMsgUtils.getString(message.children().get(4));
			if (withScoresStr.toUpperCase().equals(CommandSign.P_WITHSCORES)){
				withScores = true;
			}
		}
		int start = Integer.parseInt(startStr);
		int stop = Integer.parseInt(stopStr);
		List<RcpZSetRow> rcpZSetRows = RcpProxyZSet.zrange(keyStr.getBytes(), start, stop);
		stopwatch.end();
		return Arrays.asList(RedisMessageFactory.buildZSetArrayData(rcpZSetRows, withScores));
	}

	@Override
	public List<RedisMessage> process(String command, RedisMessage msgReq) throws Exception {
		if (command.equals("zadd")) {
			return processZAdd(msgReq);
		}
		if (command.equals("zrange")) {
			return processZRange(msgReq);
		}
		return null;
	}
}
