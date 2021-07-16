package org.coral.redis.storage.entity;

import org.coral.redis.storage.entity.type.RcpType;

/**
 * RcpStringRow
 * 包含 rcpKey rcpData
 *
 * @author wuhao
 * @createTime 2021-07-13 20:35:00
 */
public class RcpExpireRow implements RcpRow {
	private RcpExpireKey rcpExpireKey;

	private RcpExpireData rcpExpireData;

	public RcpExpireRow(RcpExpireKey rcpExpireKey, RcpExpireData rcpExpireData) {
		this.rcpExpireKey = rcpExpireKey;
		this.rcpExpireData = rcpExpireData;
	}

	public RcpExpireKey getRcpExpireKey() {
		return rcpExpireKey;
	}

	public void setRcpExpireKey(RcpExpireKey rcpExpireKey) {
		this.rcpExpireKey = rcpExpireKey;
	}

	public RcpExpireData getRcpExpireData() {
		return rcpExpireData;
	}

	public void setRcpExpireData(RcpExpireData rcpExpireData) {
		this.rcpExpireData = rcpExpireData;
	}

	public static RcpExpireRow build(byte[] keys, long time, RcpType rcpType) {
		RcpExpireKey rcpKey = RcpExpireKey.build(keys);
		RcpExpireData rcpData = RcpExpireData.build(time, rcpType);
		return new RcpExpireRow(rcpKey, rcpData);
	}
}
