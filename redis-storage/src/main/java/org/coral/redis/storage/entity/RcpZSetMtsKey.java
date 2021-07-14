package org.coral.redis.storage.entity;

/**
 * 简单字符串
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetMtsKey implements RcpKey {
	private byte[] key;
	private long version;
	private byte[] member;

	public RcpZSetMtsKey(byte[] key, long version, byte[] member) {
		this.key = key;
		this.version = version;
		this.member = member;
	}

	@Override
	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public byte[] getMember() {
		return member;
	}

	public void setMember(byte[] member) {
		this.member = member;
	}


	public static RcpZSetMtsKey build(byte[] key, long version, byte[] member) {
		return new RcpZSetMtsKey(key, version, member);
	}


}
