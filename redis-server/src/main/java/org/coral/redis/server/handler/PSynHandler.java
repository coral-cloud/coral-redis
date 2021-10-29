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
import java.util.Arrays;
import java.util.List;

/**
 * @author wuhao
 * @description: PSynHandler
 * @createTime 2021/10/28 22:35:00
 */

public class PSynHandler implements CommandHandler {
	String DEFAULT = "FULLRESYNC db72e13d181ffb0d9cc7384c175a2371a530248b 1";

	@Override
	public List<RedisMessage> process(String command, RedisMessage msgReq) throws Exception {
		RedisMessage redisSync = RedisMessageFactory.buildSimple(DEFAULT);
		RedisMessage redisMessageData = RedisMessageFactory.buildData(RdbDataStorage.getData());
		return Arrays.asList(redisSync, redisMessageData);
	}
}
