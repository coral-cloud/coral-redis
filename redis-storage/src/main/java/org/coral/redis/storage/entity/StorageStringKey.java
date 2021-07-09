package org.coral.redis.storage.entity;

import java.nio.charset.StandardCharsets;

/**
 * 简单字符串
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class StorageStringKey implements StorageKey {
	private String key;
	private StorageStringKey(String key){
		this.key = key;
	}
	public static StorageStringKey build(String key){
		return new StorageStringKey(key);
	}
	@Override
	public byte[] getKey() {
		return key.getBytes(StandardCharsets.UTF_8);
	}
}
