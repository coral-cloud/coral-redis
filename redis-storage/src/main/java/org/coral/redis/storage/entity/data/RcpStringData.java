package org.coral.redis.storage.entity.data;

import org.coral.redis.storage.utils.TimeUtils;

/**
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpStringData implements RcpData {
	private long time;
	private byte[] content;

	public RcpStringData(long time, byte[] content) {
		this.time = time;
		this.content = content;
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
		if (getContent() == null) {
			return 0;
		}
		return getContent().length;
	}

	@Override
	public byte[] getContent() {
		return content;
	}

	@Override
	public void setContent(byte[] content) {
		this.content = content;
	}


	public static RcpStringData build(long time, byte[] content) {
		return new RcpStringData(TimeUtils.getExpire(time), content);
	}
}
