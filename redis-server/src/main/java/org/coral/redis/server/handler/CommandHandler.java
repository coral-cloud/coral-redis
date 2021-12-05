package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.RedisMessage;

import java.io.IOException;
import java.util.List;

/**
 * @author wuhao
 * @description: CommandHandler
 * @createTime 2021/10/29 22:16:00
 */

public interface CommandHandler {
	/**
	 *
	 * @param command
	 * @param msgReq
	 * @return
	 * @throws Exception
	 */
	List<RedisMessage> process(ChannelHandlerContext ctx, String command, RedisMessage msgReq) throws Exception;
}
