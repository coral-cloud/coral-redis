package org.coral.redis.slave;

import com.moilioncircle.redis.replicator.Configuration;
import com.moilioncircle.redis.replicator.RedisReplicator;
import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.cmd.impl.SetCommand;
import com.moilioncircle.redis.replicator.event.Event;
import com.moilioncircle.redis.replicator.event.EventListener;
import com.moilioncircle.redis.replicator.event.PostRdbSyncEvent;
import com.moilioncircle.redis.replicator.util.Strings;
import org.coral.redis.service.RedisService;
import org.coral.redis.storage.StorageProxyString;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * @author wuhao
 * @description: RedisSlave
 * @createTime 2021/07/21 23:06:00
 */

public class RedisSlave {
	public void start(String ip, int port) throws IOException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					Replicator replicator = new RedisReplicator(ip, port, Configuration.defaultSetting().setRetries(1));
					replicator.addEventListener(new EventListener() {
						@Override
						public void onEvent(Replicator replicator, Event event) {
							try {
								if (event instanceof PostRdbSyncEvent) {
									System.out.println(event.toString());
								}
								if (event instanceof SetCommand) {
									SetCommand setCommand = (SetCommand) event;
									StorageProxyString.set(setCommand.getKey(), setCommand.getValue(), -1);
									System.out.println(new String(setCommand.getKey()) + " : " + new String(setCommand.getValue()));
								}
							} catch (Exception e){
								e.printStackTrace();
							}

						}
					});
					replicator.open();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();

	}

	public static void main(String[] args) throws IOException {
		RedisSlave redisSlave = new RedisSlave();
		RedisService.run(6399);
		redisSlave.start("127.0.0.1", 6379);

	}
}
