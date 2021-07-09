package org.coral.redis.storage.entity;

/**
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class StorageStringData implements StorageData {
	private long time;
	private byte[] content;

	@Override
	public long getTime() {
		return 0;
	}

	@Override
	public byte[] getContent() {
		return content;
	}

	@Override
	public byte[] getBytes() {
		return new byte[0];
	}

	@Override
	public StorageType getRType() {
		return StorageType.STRING;
	}
}
