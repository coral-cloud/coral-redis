package org.coral.redis.storage.entity.data;

/**
 * RcpZSetStmData
 *
 * @author wuhao
 * @createTime 2021-07-09 16:09:00
 */
public class RcpZSetStmData implements RcpData {


	@Override
	public long getTime() {
		return 0;
	}

	@Override
	public void setTime(long time) {

	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public byte[] getBytes() {
		return new byte[]{};
	}

	public static RcpZSetStmData build() {
		return new RcpZSetStmData();
	}

}
