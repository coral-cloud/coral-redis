package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.RcpProxyString;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

import java.util.Arrays;
import java.util.List;

/**
 * StringHandler
 *
 * @author wuhao
 * @createTime 2021-06-25 16:29:00
 */
public class StringHandler implements CommandHandler {


	/**
	 * @param msgReq
	 * @return
	 */
	public static List<RedisMessage> processSet(RedisMessage msgReq) {
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		int expire = RedisMsgUtils.getExpire(message);
		byte[] key = RedisMsgUtils.getBytes(message.children().get(1));
		byte[] value = RedisMsgUtils.getBytes(message.children().get(2));
		RcpProxyString.set(key, value, expire);
		return Arrays.asList(RedisMessageFactory.buildOK());
	}


	public static List<RedisMessage> processGet(RedisMessage msgReq) {
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		byte[] key = RedisMsgUtils.getBytes(message.children().get(1));
		byte[] valueData = RcpProxyString.get(key);
		return Arrays.asList(RedisMessageFactory.buildData(valueData));
	}

	@Override
	public List<RedisMessage> process(String command, RedisMessage msgReq) throws Exception {
		if (command.equals("set")) {
			return processSet(msgReq);
		}
		if (command.equals("get")) {
			return processGet(msgReq);
		}
		return null;
	}
}
