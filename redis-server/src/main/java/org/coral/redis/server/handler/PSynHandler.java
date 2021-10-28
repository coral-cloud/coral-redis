package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.cluster.rdb.RdbDataStorage;
import org.coral.redis.perfmon.RedisCounters;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.type.ReplCommand;
import org.coral.redis.uils.RedisMsgUtils;
import org.helium.perfmon.Stopwatch;

import java.io.IOException;

/**
 * @author wuhao
 * @description: PSynHandler
 * @createTime 2021/10/28 22:35:00
 */

public class PSynHandler {
	public static RedisMessage processPSyn(RedisMessage msgReq) {
		Stopwatch stopwatch = RedisCounters.getInstance("psyn").getTx().begin();
		try {
			return RedisMessageFactory.buildData(RdbDataStorage.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return RedisMessageFactory.buildError();
	}
}
