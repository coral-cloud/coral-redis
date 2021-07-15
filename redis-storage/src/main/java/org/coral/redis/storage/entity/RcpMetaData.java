package org.coral.redis.storage.entity;

import org.coral.redis.storage.utils.TimeUtils;

/**
 *
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpMetaData implements RcpData {
	private long time;
	private int size;
	private long version;

	public RcpMetaData(long time, long version) {
		this.time = time;
		this.version = version;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * build
	 * @param time
	 * @param version
	 * @return
	 */
	public static RcpMetaData build(long time, long version) {
		return new RcpMetaData(TimeUtils.getExpire(time), version);
	}
}
