package org.coral.redis.storage.entity;

/**
 * meta info
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpMetaKey implements RcpKey {
	private byte[] key;

	private RcpMetaKey(byte[] key) {
		this.key = key;
	}

	@Override
	public byte[] getKey() {
		return key;
	}

	public static RcpMetaKey build(byte[] key) {
		return new RcpMetaKey(key);
	}
}

