package org.coral.redis.storage.entity.data;

/**
 * 简单字符串
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpStringKey implements RcpKey {
	private byte[] key;

	private RcpStringKey(byte[] key) {
		this.key = key;
	}

	@Override
	public byte[] getKey() {
		return key;
	}

	public static RcpStringKey build(byte[] key) {
		return new RcpStringKey(key);
	}
}

