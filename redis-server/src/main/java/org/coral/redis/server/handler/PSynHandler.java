package org.coral.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import org.coral.redis.cluster.rdb.RdbDataStorage;
import org.coral.redis.server.RedisMessageFactory;
import org.coral.redis.uils.IdUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuhao
 * @description: PSynHandler
 * @createTime 2021/10/28 22:35:00
 */

public class PSynHandler implements CommandHandler {

	@Override
	public List<RedisMessage> process(ChannelHandlerContext ctx, String command, RedisMessage msgReq) throws Exception {
		RedisMessage redisSync = RedisMessageFactory.buildSimple(getRespCommand());
		RedisMessage redisMessageData = RedisMessageFactory.buildData(RdbDataStorage.getData());

		Thread thread = new Thread(new SynTask(ctx));
		thread.start();

		return Arrays.asList(redisSync, redisMessageData);
	}

	public String getRespCommand() {
		String command = "FULLRESYNC db72e13d181ffb0d9cc7384c175a2371a530248b 1";
		return command;
	}


	public static class SynTask implements Runnable {
		private ChannelHandlerContext ctx;

		public SynTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				try {
					Thread.sleep(1000);
					synData(ctx, i + "");
					System.out.println("syn " + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void synData(ChannelHandlerContext ctx, String key) {
		RedisMessage redisMessageCmd = RedisMessageFactory.buildData("set".getBytes());
		RedisMessage redisMessageKey = RedisMessageFactory.buildData(key.getBytes());
		RedisMessage redisMessageValue = RedisMessageFactory.buildData(key.getBytes());
		ArrayRedisMessage redisMessage = new ArrayRedisMessage(Arrays.asList(redisMessageCmd, redisMessageKey, redisMessageValue));
		ctx.writeAndFlush(redisMessage);
	}
}
