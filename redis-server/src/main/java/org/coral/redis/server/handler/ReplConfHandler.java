package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.type.ClusterCommand;
import org.coral.redis.entity.ClusterInfo;
import org.coral.redis.entity.ServerNode;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.type.ReplCommand;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuhao
 * @description: ReplConfHandler
 * @createTime 2021/10/28 22:25:00
 */

public class ReplConfHandler implements CommandHandler {


	@Override
	public List<RedisMessage> process(String command, RedisMessage msgReq) throws Exception {
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		RedisMessage redisMessage = null;
		String operator = RedisMsgUtils.getString(message.children().get(1)).toUpperCase();
		switch (operator) {
			case ReplCommand.LISTENING_PORT:
			case ReplCommand.IP_ADDRESS:
			case ReplCommand.CAPA:
				redisMessage = RedisMessageFactory.buildOK();
			default:
				redisMessage = RedisMessageFactory.buildOK();
		}
		return Arrays.asList(redisMessage);
	}
}
