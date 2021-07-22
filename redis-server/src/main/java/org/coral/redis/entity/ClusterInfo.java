package org.coral.redis.entity;

/**
 * @author wuhao
 * @description =  ClusterInfo
 * @createTime 2021/07/22 23 = 03 = 00
 */

public class ClusterInfo {
	private String clusterState = "ok";
	private int clusterSlotsAssigned = 16384;
	private int clusterSlotsOk = 16384;
	private int clusterSlotsPfail = 0;
	private int clusterSlotsFail = 0;
	private int clusterKnownNodes = 1;
	private int clusterSize = 1;
	private int clusterCurrentEpoch = 1;
	private int clusterMyEpoch = 1;
	private int clusterStatsMessagesSent = 0;
	private int clusterStatsessagesReceived = 0;

	public String getClusterState() {
		return clusterState;
	}

	public void setClusterState(String clusterState) {
		this.clusterState = clusterState;
	}

	public int getClusterSlotsAssigned() {
		return clusterSlotsAssigned;
	}

	public void setClusterSlotsAssigned(int clusterSlotsAssigned) {
		this.clusterSlotsAssigned = clusterSlotsAssigned;
	}

	public int getClusterSlotsOk() {
		return clusterSlotsOk;
	}

	public void setClusterSlotsOk(int clusterSlotsOk) {
		this.clusterSlotsOk = clusterSlotsOk;
	}

	public int getClusterSlotsPfail() {
		return clusterSlotsPfail;
	}

	public void setClusterSlotsPfail(int clusterSlotsPfail) {
		this.clusterSlotsPfail = clusterSlotsPfail;
	}

	public int getClusterSlotsFail() {
		return clusterSlotsFail;
	}

	public void setClusterSlotsFail(int clusterSlotsFail) {
		this.clusterSlotsFail = clusterSlotsFail;
	}

	public int getClusterKnownNodes() {
		return clusterKnownNodes;
	}

	public void setClusterKnownNodes(int clusterKnownNodes) {
		this.clusterKnownNodes = clusterKnownNodes;
	}

	public int getClusterSize() {
		return clusterSize;
	}

	public void setClusterSize(int clusterSize) {
		this.clusterSize = clusterSize;
	}

	public int getClusterCurrentEpoch() {
		return clusterCurrentEpoch;
	}

	public void setClusterCurrentEpoch(int clusterCurrentEpoch) {
		this.clusterCurrentEpoch = clusterCurrentEpoch;
	}

	public int getClusterMyEpoch() {
		return clusterMyEpoch;
	}

	public void setClusterMyEpoch(int clusterMyEpoch) {
		this.clusterMyEpoch = clusterMyEpoch;
	}

	public int getClusterStatsMessagesSent() {
		return clusterStatsMessagesSent;
	}

	public void setClusterStatsMessagesSent(int clusterStatsMessagesSent) {
		this.clusterStatsMessagesSent = clusterStatsMessagesSent;
	}

	public int getClusterStatsessagesReceived() {
		return clusterStatsessagesReceived;
	}

	public void setClusterStatsessagesReceived(int clusterStatsessagesReceived) {
		this.clusterStatsessagesReceived = clusterStatsessagesReceived;
	}

	public String getInfo(){
		StringBuilder infoBuilder = new StringBuilder();
		infoBuilder.append("cluster_state:").append(clusterState).append("\n");
		infoBuilder.append("cluster_slots_assigned:").append(clusterSlotsAssigned).append("\n");
		infoBuilder.append("cluster_slots_ok:").append(clusterSlotsOk).append("\n");
		infoBuilder.append("cluster_slots_pfail:").append(clusterSlotsPfail).append("\n");
		infoBuilder.append("cluster_slots_fail:").append(clusterSlotsFail).append("\n");
		infoBuilder.append("cluster_known_nodes:").append(clusterKnownNodes).append("\n");
		infoBuilder.append("cluster_size:").append(clusterSize).append("\n");
		infoBuilder.append("cluster_current_epoch:").append(clusterCurrentEpoch).append("\n");
		infoBuilder.append("cluster_my_epoch:").append(clusterMyEpoch).append("\n");
		infoBuilder.append("cluster_stats_messages_sent:").append(clusterStatsMessagesSent).append("\n");
		infoBuilder.append("cluster_stats_messages_received:").append(clusterStatsessagesReceived).append("\n");
		return infoBuilder.toString();
	}

	public static String getClusterInfo(){
		ClusterInfo clusterInfo = new ClusterInfo();
		return clusterInfo.getInfo();
	}
}
