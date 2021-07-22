package org.coral.redis.storage.entity.meta;

import org.coral.redis.storage.protostuff.ObjectUtils;

/**
 * @author wuhao
 * @createTime 2021-07-22 17:27:00
 */
public class ServerMetaData {
	private boolean slave = false;
	private String replId = "?";
	private long offset = -1;

	public String getReplId() {
		return replId;
	}

	public void setReplId(String replId) {
		this.replId = replId;
	}

	public boolean isSlave() {
		return slave;
	}

	public void setSlave(boolean slave) {
		this.slave = slave;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public byte[] getBytes() {
		return ObjectUtils.toBytes(this);
	}

	public static ServerMetaData build() {
		ServerMetaData serverMetaData = new ServerMetaData();
		return serverMetaData;
	}

}
