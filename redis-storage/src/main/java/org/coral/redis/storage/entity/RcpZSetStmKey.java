package org.coral.redis.storage.entity;

/**
 * RcpZSetStmKey
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetStmKey implements RcpKey {
	private byte[] key;
	private long version;
	private long score;
	private byte[] member;

	public RcpZSetStmKey(byte[] key, long version, long score, byte[] member) {
		this.key = key;
		this.version = version;
		this.score = score;
		this.member = member;
	}

	@Override
	public byte[] getKey() {
		return key;
	}

	public long getVersion() {
		return version;
	}

	public long getScore() {
		return score;
	}

	public byte[] getMember() {
		return member;
	}

	public static RcpZSetStmKey build(byte[] key, long version, long score, byte[] member) {
		return new RcpZSetStmKey(key, version, score, member);
	}
}
