package org.coral.redis.storage.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 包含  RcpMeta  RcpZSetMts RcpZSetStm
 * @author wuhao
 * @createTime 2021-07-13 20:45:00
 */
public class RcpZSetRow implements RcpRow{
	/**
	 * meta 信息
	 */
	private RcpMetaKey rcpMetaKey;
	private RcpMetaData rcpMetaData;

	/**
	 * member to score
	 */
	private RcpZSetMtsKey rcpZSetMtsKey;
	private RcpZSetMtsData rcpZSetMtsData;
	/**
	 * score to member
	 */
	private RcpZSetStmKey rcpZSetStmKey;
	private RcpZSetStmData rcpZSetStmData;

	public RcpMetaKey getRcpMetaKey() {
		return rcpMetaKey;
	}

	public void setRcpMetaKey(RcpMetaKey rcpMetaKey) {
		this.rcpMetaKey = rcpMetaKey;
	}

	public RcpMetaData getRcpMetaData() {
		return rcpMetaData;
	}

	public void setRcpMetaData(RcpMetaData rcpMetaData) {
		this.rcpMetaData = rcpMetaData;
	}

	public RcpZSetMtsKey getRcpZSetMtsKey() {
		return rcpZSetMtsKey;
	}

	public void setRcpZSetMtsKey(RcpZSetMtsKey rcpZSetMtsKey) {
		this.rcpZSetMtsKey = rcpZSetMtsKey;
	}

	public RcpZSetMtsData getRcpZSetMtsData() {
		return rcpZSetMtsData;
	}

	public void setRcpZSetMtsData(RcpZSetMtsData rcpZSetMtsData) {
		this.rcpZSetMtsData = rcpZSetMtsData;
	}

	public RcpZSetStmKey getRcpZSetStmKey() {
		return rcpZSetStmKey;
	}

	public void setRcpZSetStmKey(RcpZSetStmKey rcpZSetStmKey) {
		this.rcpZSetStmKey = rcpZSetStmKey;
	}

	public RcpZSetStmData getRcpZSetStmData() {
		return rcpZSetStmData;
	}

	public void setRcpZSetStmData(RcpZSetStmData rcpZSetStmData) {
		this.rcpZSetStmData = rcpZSetStmData;
	}

	/**
	 * 构建zset存储结构
	 * 此处RcpMetaKey&RcpMetaData自有一条数据
	 *
	 * @param key
	 * @param zMap
	 * @param time
	 * @param version
	 * @return
	 */
	public static List<RcpZSetRow> build(byte[] key, Map<byte[], Double> zMap, long time, int version){
		List<RcpZSetRow> rcpZSetRows = new ArrayList<>(zMap.size());
		RcpZSetRow zSetRow = null;
		RcpMetaKey rcpMetaKey = RcpMetaKey.build(key);
		RcpMetaData rcpMetaData = RcpMetaData.build(time, version);
		for (Map.Entry<byte[], Double> mapEntry: zMap.entrySet()){
			zSetRow = new RcpZSetRow();
			zSetRow.setRcpMetaData(rcpMetaData);
			zSetRow.setRcpMetaKey(rcpMetaKey);
			zSetRow.setRcpZSetMtsKey(RcpZSetMtsKey.build(key, version, mapEntry.getKey()));
			zSetRow.setRcpZSetMtsData(RcpZSetMtsData.build(mapEntry.getValue()));
			zSetRow.setRcpZSetStmKey(RcpZSetStmKey.build(key, version, mapEntry.getValue(), mapEntry.getKey()));
			zSetRow.setRcpZSetStmData(RcpZSetStmData.build());
			rcpZSetRows.add(zSetRow);
		}
		return rcpZSetRows;
	}
}
