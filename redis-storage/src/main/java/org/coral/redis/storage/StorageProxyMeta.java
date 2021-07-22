package org.coral.redis.storage;

import org.coral.redis.storage.entity.meta.ServerMetaData;

/**
 * StorageProxyMeta
 * @author wuhao
 * @createTime 2021-06-24 18:25:00
 */
public class StorageProxyMeta {

	public static boolean setServerMeta(ServerMetaData serverMetaData){
		return StorageClientMeta.getInstance().setServerMeta(serverMetaData);
	}

	public static ServerMetaData getServerMeta(){
		ServerMetaData serverMetaData = StorageClientMeta.getInstance().getServerMeta();
		if (serverMetaData == null){
			serverMetaData = ServerMetaData.build();
			StorageClientMeta.getInstance().setServerMeta(serverMetaData);
		}
		return serverMetaData;
	}

}
