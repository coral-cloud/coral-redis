package org.coral.redis.server;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhao
 * @createTime 2021-06-15 09:40:00
 */
public class RedisServerHandler extends ChannelDuplexHandler {

	private RedisCommandDispatcher redisCommandDispatcher = new RedisCommandDispatcher();

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
		String[] commands = ((String) msg).split("\\s+");
		List<RedisMessage> children = new ArrayList<RedisMessage>(commands.length);
		for (String cmdString : commands) {
			children.add(new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), cmdString)));
		}
		RedisMessage request = new ArrayRedisMessage(children);
		ctx.write(request, promise);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		RedisMessage msgReq = (RedisMessage) msg;
		List<RedisMessage> redisMessages = redisCommandDispatcher.processCommand(msgReq);
		ReferenceCountUtil.release(msgReq);
		for (RedisMessage msgRes : redisMessages) {
			ctx.writeAndFlush(msgRes);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace(System.err);
		ctx.close();
	}
}