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

/**
 * @author wuhao
 * @description: ReplConfHandler
 * @createTime 2021/10/28 22:25:00
 */

public class ReplConfHandler {
	public static RedisMessage processReplConf(RedisMessage msgReq) {
		Stopwatch stopwatch = RedisCounters.getInstance("replconf").getTx().begin();
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		String operator = RedisMsgUtils.getString(message.children().get(1)).toUpperCase();
		switch (operator) {
			case ReplCommand.LISTENING_PORT:
			case ReplCommand.IP_ADDRESS:
			case ReplCommand.CAPA:
				return RedisMessageFactory.buildOK();
			default:
				return RedisMessageFactory.buildOK();
		}
	}
}
