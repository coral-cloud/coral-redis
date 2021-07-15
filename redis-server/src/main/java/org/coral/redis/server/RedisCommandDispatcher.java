package org.coral.redis.server;

import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import org.coral.redis.server.handler.StringHandler;
import org.coral.redis.server.handler.ZSetHandler;
import org.coral.redis.uils.RedisMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuhao
 * @createTime 2021-06-25 14:10:00
 */
public class RedisCommandDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCommandDispatcher.class);

	/**
	 * @param msg
	 * @return
	 */
	public RedisMessage processCommand(RedisMessage msg) {
		String command = getCommand(msg);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("RedisCommandDispatcher:{}", command);
		}
		switch (command) {
			case CommandSign.GET:
				return StringHandler.processGet(msg);
			case CommandSign.SET:
				return StringHandler.processSet(msg);
			case CommandSign.ZADD:
				return ZSetHandler.processZAdd(msg);
			case CommandSign.ZRANGE:
				return ZSetHandler.processZRange(msg);
			case CommandSign.PING:
				return RedisMessageFactory.buildPONG();
			case CommandSign.PONG:
				return RedisMessageFactory.buildPING();
			default:
				return RedisMessageFactory.buildError();

		}

	}

	/**
	 * @param msg
	 * @return
	 */
	private String getCommand(RedisMessage msg) {
		String command = null;
		if (msg instanceof SimpleStringRedisMessage) {
			command = ((SimpleStringRedisMessage) msg).content();
		} else if (msg instanceof ErrorRedisMessage) {
			command = ((ErrorRedisMessage) msg).content();
		} else if (msg instanceof IntegerRedisMessage) {
			throw new CodecException("unknown message type: " + msg);
		} else if (msg instanceof FullBulkStringRedisMessage) {
			command = RedisMsgUtils.getString((FullBulkStringRedisMessage) msg);
		} else if (msg instanceof ArrayRedisMessage) {
			ArrayRedisMessage message = (ArrayRedisMessage) msg;
			command = RedisMsgUtils.getString((FullBulkStringRedisMessage) message.children().get(0));
		} else {
			LOGGER.warn("unknown message type:{}", msg);
			throw new CodecException("unknown message type: " + msg);
		}
		return command.toUpperCase();
	}


}
