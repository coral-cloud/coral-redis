package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.entity.ClusterCommand;
import org.coral.redis.entity.ClusterInfo;
import org.coral.redis.entity.ServerNode;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.storage.StorageProxyString;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

/**
 * @author wuhao
 * @description: ClusterHandler
 * @createTime 2021/07/22 23:01:00
 */

public class ClusterHandler {
	public static RedisMessage processCluster(RedisMessage msgReq) {
		Stopwatch stopwatch = RedisCounters.getInstance("get").getTx().begin();
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
		return RedisMessageFactory.buildData((retMsg).getBytes());
	}
}
