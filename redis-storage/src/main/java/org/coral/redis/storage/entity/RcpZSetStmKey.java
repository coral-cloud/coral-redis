package org.coral.redis.storage.entity;

import org.coral.redis.storage.protostuff.ObjectUtils;

/**
 * RcpZSetStmKey
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetStmKey implements RcpKey {
	private byte[] key;
	private long version;
	private double score;
	private byte[] member;

	public RcpZSetStmKey(byte[] key, long version, double score, byte[] member) {
		this.key = key;
		this.version = version;
		this.score = score;
		this.member = member;
	}

	@Override
	public byte[] getKey() {
		return ObjectUtils.toBytes(this);
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public byte[] getMember() {
		return member;
	}

	public void setMember(byte[] member) {
		this.member = member;
	}

	public static RcpZSetStmKey build(byte[] key, long version, double score, byte[] member) {
		return new RcpZSetStmKey(key, version, score, member);
	}

	public static RcpZSetStmKey parse(byte[] content) {
		if (content == null) {
			return null;
		}
		return (RcpZSetStmKey) ObjectUtils.toObject(content, RcpZSetStmKey.class);
	}
}
