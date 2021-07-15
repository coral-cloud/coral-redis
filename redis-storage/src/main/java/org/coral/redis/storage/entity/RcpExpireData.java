package org.coral.redis.storage.entity;

import org.coral.redis.storage.entity.type.RcpType;
import org.coral.redis.storage.utils.TimeUtils;

/**
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpExpireData implements RcpData {
	private long time;
	private RcpType rcpType;

	public RcpExpireData(long time, RcpType rcpType) {
		this.time = time;
		this.rcpType = rcpType;
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
		if (getContent() == null){
			return 0;
		}
		return getContent().length;
	}

	@Override
	public RcpType getRcpType() {
		return rcpType;
	}

	public void setRcpType(RcpType rcpType) {
		this.rcpType = rcpType;
	}

	public static RcpExpireData build(long time, RcpType rcpType) {
		return new RcpExpireData(TimeUtils.getExpire(time), rcpType);
	}
}
