package org.coral.redis.storage;

import org.coral.redis.storage.entity.meta.ServerMetaData;
import org.coral.redis.storage.storage.impl.RcpMetaDb;

/**
 * StorageProxyMeta
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class RcpProxyMeta {

	public static boolean setServerMeta(ServerMetaData serverMetaData){
		return RcpMetaDb.getInstance().setServerMeta(serverMetaData);
	}

	public static ServerMetaData getServerMeta(){
		ServerMetaData serverMetaData = RcpMetaDb.getInstance().getServerMeta();
		if (serverMetaData == null){
			serverMetaData = ServerMetaData.build();
			RcpMetaDb.getInstance().setServerMeta(serverMetaData);
		}
		return serverMetaData;
	}

}
