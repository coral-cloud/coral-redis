package org.coral.redis.storage.entity.data;

/**
 * 简单字符串
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpExpireKey implements RcpKey {
	private byte[] key;

	private RcpExpireKey(byte[] key) {
		this.key = key;
	}

	@Override
	public byte[] getKey() {
		return key;
	}

	public static RcpExpireKey build(byte[] key) {
		return new RcpExpireKey(key);
	}
}

