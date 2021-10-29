package org.coral.redis.server.handler;

import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.server.RedisMessageFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuhao
 * @description: PingHandler
 * @createTime 2021/10/29 23:21:00
 */

public class PingHandler implements CommandHandler {
	@Override
	public List<RedisMessage> process(String command, RedisMessage msgReq) throws Exception {
		if (command.equalsIgnoreCase("PING")) {
			return Arrays.asList(RedisMessageFactory.buildPONG());
		} else {
			return Arrays.asList(RedisMessageFactory.buildPING());
		}

	}
}
