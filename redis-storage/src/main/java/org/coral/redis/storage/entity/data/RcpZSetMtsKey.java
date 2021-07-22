package org.coral.redis.storage.entity.data;

import org.coral.redis.storage.protostuff.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单字符串
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetMtsKey implements RcpKey {
	private static final Logger LOGGER = LoggerFactory.getLogger(RcpZSetMtsKey.class);
	private byte[] key;
	private int version;
	private byte[] member;

	public RcpZSetMtsKey(byte[] key, int version, byte[] member) {
		this.key = key;
		this.version = version;
		this.member = member;
	}

	public static RcpZSetMtsKey build(byte[] key, int version, byte[] member) {
		return new RcpZSetMtsKey(key, version, member);
	}

	public static RcpZSetMtsKey parse(byte[] content) {
		if (content == null) {
			return null;
		}
		return (RcpZSetMtsKey) ObjectUtils.toObject(content, RcpZSetMtsKey.class);
	}

	@Override
	public byte[] getKey() {
		return ObjectUtils.toBytes(this);
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public byte[] getMember() {
		return member;
	}

	public void setMember(byte[] member) {
		this.member = member;
	}


}
