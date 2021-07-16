package org.coral.redis.storage.entity;

/**
 * RcpStringRow
 * 包含 rcpKey rcpData
 *
 * @author wuhao
 * @createTime 2021-07-13 20:35:00
 */
public class RcpStringRow implements RcpRow {
	private RcpStringKey rcpStringKey;

	private RcpStringData rcpStringData;

	public RcpStringKey getRcpStringKey() {
		return rcpStringKey;
	}

	public RcpStringData getRcpStringData() {
		return rcpStringData;
	}

	private RcpStringRow(RcpStringKey rcpStringKey, RcpStringData rcpStringData) {
		this.rcpStringKey = rcpStringKey;
		this.rcpStringData = rcpStringData;
	}

	public static RcpStringRow build(byte[] keys, byte[] content, long time) {
		RcpStringKey rcpKey = RcpStringKey.build(keys);
		RcpStringData rcpData = RcpStringData.build(time, content);
		return new RcpStringRow(rcpKey, rcpData);
	}
}
