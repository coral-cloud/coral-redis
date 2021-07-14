package org.coral.redis.storage.entity;

/**
 * member to score
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetMtsData implements RcpData {
	private long time;
	private long score;

	public RcpZSetMtsData(long score) {
		this.score = score;
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
	public long getSize() {
		return 0;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public static RcpZSetMtsData build(long score) {
		RcpZSetMtsData rcpZSetMtsData = new RcpZSetMtsData(score);
		return rcpZSetMtsData;
	}

}
