package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.type.ClusterCommand;
import org.coral.redis.entity.ClusterInfo;
import org.coral.redis.entity.ServerNode;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuhao
 * @description: ClusterHandler
 * @createTime 2021/07/22 23:01:00
 */

public class ClusterHandler implements CommandHandler {

	@Override
	public List<RedisMessage> process(ChannelHandlerContext ctx, String command,RedisMessage msgReq) throws Exception {
		ArrayRedisMessage message = (ArrayRedisMessage) msgReq;
		String operator = RedisMsgUtils.getString(message.children().get(1)).toUpperCase();
		String retMsg = "";
		switch (operator) {
			case ClusterCommand.INFO:
				retMsg = ClusterInfo.getClusterInfo();
				break;
			case ClusterCommand.MYID:
				retMsg = ServerNode.getMyId();
				break;
			case ClusterCommand.NODES:
				retMsg = ServerNode.getClusterNode();
				break;
			default:
				retMsg = "not support:" + operator;
				break;
		}
		RedisMessage redisMessage = RedisMessageFactory.buildData((retMsg).getBytes());
		return Arrays.asList(redisMessage);
	}

}
