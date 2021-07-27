package org.coral.redis.slave;

import com.moilioncircle.redis.replicator.Configuration;
import com.moilioncircle.redis.replicator.RedisReplicator;
import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.cmd.impl.SetCommand;
import com.moilioncircle.redis.replicator.event.Event;
import com.moilioncircle.redis.replicator.event.EventListener;
import com.moilioncircle.redis.replicator.event.PostRdbSyncEvent;
import com.moilioncircle.redis.replicator.event.PreRdbSyncEvent;
import com.moilioncircle.redis.replicator.rdb.datatype.KeyStringValueString;
import org.coral.redis.storage.RcpProxyMeta;
import org.coral.redis.storage.RcpProxyString;
import org.coral.redis.storage.entity.meta.ServerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author wuhao
 * @createTime 2021-07-22 09:55:00
 */
public class ReplicatorTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisSlave.class);

	private String ip = "127.0.0.1";
	private int port = 6379;

	public ReplicatorTask(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run() {
		doReplicator(ip, port);
	}

	public void doReplicator(String ip, int port) {
		try {
			Configuration configuration = getReplicatorConfiguration();
			Replicator replicator = new RedisReplicator(ip, port, configuration);
			replicator.addEventListener(new EventListener() {
				@Override
				public void onEvent(Replicator replicator, Event event) {
					try {
						LOGGER.debug("onEvent:{}", event.toString());
						if (event instanceof PreRdbSyncEvent) {
							//同步开始
							PreRdbSyncEvent preRdbSyncEvent = (PreRdbSyncEvent) event;
							Event.Context context = preRdbSyncEvent.getContext();
							LOGGER.info("Syn Start PreRdbSyncEvent:{}-{}", context.getOffsets().getV1(), context.getOffsets().getV2());
							ServerMetaData serverMetaData = RcpProxyMeta.getServerMeta();
							LOGGER.info("serverMetaData offset:{}", serverMetaData.getOffset());
						} else if (event instanceof PostRdbSyncEvent) {
							//同步结束
							PostRdbSyncEvent postRdbSyncEvent = (PostRdbSyncEvent) event;
							Event.Context context = postRdbSyncEvent.getContext();
							LOGGER.info("Syn End PreRdbSyncEvent:{}-{}", context.getOffsets().getV1(), context.getOffsets().getV2());
						} else if (event instanceof KeyStringValueString) {
							//rdb同步
							KeyStringValueString kvSyn = (KeyStringValueString) event;
							long expire = -1;
							if (kvSyn.getExpiredValue() != null) {
								expire = kvSyn.getExpiredValue();
							}
							RcpProxyString.set(kvSyn.getKey(), kvSyn.getValue(), expire);
							LOGGER.info("kvSyn:{}-{}", new String(kvSyn.getKey()), new String(kvSyn.getValue()));
						} else if (event instanceof SetCommand) {
							//实时同步
							SetCommand setSyn = (SetCommand) event;
							long expire = -1;
							if (setSyn.getExpiredValue() != null) {
								expire = setSyn.getExpiredValue();
							}
							RcpProxyString.set(setSyn.getKey(), setSyn.getValue(), expire);
							LOGGER.info("setSyn:{}-{}", new String(setSyn.getKey()), new String(setSyn.getValue()));
						}
						processEvent(replicator);
					} catch (Exception e) {
						LOGGER.error("onEvent Exception", e);
					}

				}
			});
			replicator.open();
		} catch (IOException e) {
			LOGGER.error("doReplicator Exception", e);
		}
	}

	/**
	 * getReplicatorConfiguration
	 * 设置从机offset/replid
	 *
	 */
	public Configuration getReplicatorConfiguration() {
		Configuration configuration = Configuration.defaultSetting().setRetries(1);
		ServerMetaData serverMetaData = RcpProxyMeta.getServerMeta();
		configuration.addOffset(serverMetaData.getOffset());
		configuration.setReplId(serverMetaData.getReplId());
		LOGGER.info("getReplicatorConfiguration set replid:{} offset:{}", serverMetaData.getReplId(), serverMetaData.getOffset());
		return configuration;
	}

	/**
	 * processContext
	 * 同步上下文,记录offset
	 *
	 * @param replicator
	 */
	public void processEvent(Replicator replicator) {
		Configuration configuration = replicator.getConfiguration();
		LOGGER.info("processEvent,repl: {}, offset: {}", configuration.getReplId(), configuration.getReplOffset());
		ServerMetaData serverMetaData = RcpProxyMeta.getServerMeta();
		serverMetaData.setOffset(configuration.getReplOffset());
		serverMetaData.setReplId(configuration.getReplId());
		RcpProxyMeta.setServerMeta(serverMetaData);
	}
}
