package org.coral.redis.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import org.coral.redis.command.RedisCommand;
import org.coral.redis.server.handler.*;
import org.coral.redis.type.CommandSign;
import org.coral.redis.uils.RedisMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

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
	public List<RedisMessage> processCommand(ChannelHandlerContext ctx, RedisMessage msg) {
		try {
			String command = getCommand(msg);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("processCommand:{}", command);
			}
			RedisCommand redisCmd = RedisCommand.getSupportCmd(command);
			if (redisCmd != null && redisCmd.getCmdHander() != null) {
				return redisCmd.getCmdHander().process(ctx, command, msg);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Arrays.asList(RedisMessageFactory.buildError());

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
			command = RedisMsgUtils.getString(msg);
		} else if (msg instanceof ArrayRedisMessage) {
			ArrayRedisMessage message = (ArrayRedisMessage) msg;
			command = RedisMsgUtils.getString(message.children().get(0));
		} else {
			LOGGER.warn("unknown message type:{}", msg);
			throw new CodecException("unknown message type: " + msg);
		}
		return command.toUpperCase();
	}


}
