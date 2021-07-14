package org.coral.redis.storage.entity;

/**
 * member to score
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetMtsData implements RcpData {
	private long time;
	private double score;

	public RcpZSetMtsData(double score) {
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public static RcpZSetMtsData build(double score) {
		RcpZSetMtsData rcpZSetMtsData = new RcpZSetMtsData(score);
		return rcpZSetMtsData;
	}

}
