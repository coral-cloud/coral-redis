package org.coral.redis.slave;

import com.moilioncircle.redis.replicator.Configuration;
import com.moilioncircle.redis.replicator.RedisReplicator;
import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.cmd.impl.SetCommand;
import com.moilioncircle.redis.replicator.event.Event;
import com.moilioncircle.redis.replicator.event.EventListener;
import com.moilioncircle.redis.replicator.event.PostRdbSyncEvent;
import com.moilioncircle.redis.replicator.util.Strings;
import org.coral.redis.server.RedisCommandDispatcher;
import org.coral.redis.service.RedisService;
import org.coral.redis.storage.StorageProxyString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author wuhao
 * @description: RedisSlave
 * @createTime 2021/07/21 23:06:00
 */

public class RedisSlave {
	public void start(String ip, int port) throws IOException {
		ReplicatorTask replicatorTask = new ReplicatorTask(ip, port);
		Thread thread = new Thread(replicatorTask);
		thread.start();

	}

}
