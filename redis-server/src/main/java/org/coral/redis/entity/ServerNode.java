package org.coral.redis.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhao
 * @description: ClusterNode
 * @createTime 2021/07/22 23:03:00
 */

public class ServerNode {
	private static class ServerNodeInit{
		private static ServerNode serverNode = new ServerNode();
	}
	public static ServerNode getInstance(){
		return ServerNodeInit.serverNode;
	}
	private ClusterNode clusterNodeSelf = new ClusterNode();
	private List<ClusterNode> clusterNodes = new ArrayList<>();

	public ClusterNode getClusterNodeSelf() {
		return clusterNodeSelf;
	}

	public void setClusterNodeSelf(ClusterNode clusterNodeSelf) {
		this.clusterNodeSelf = clusterNodeSelf;
	}

	public List<ClusterNode> getClusterNodes() {
		return clusterNodes;
	}

	public void setClusterNodes(List<ClusterNode> clusterNodes) {
		this.clusterNodes = clusterNodes;
	}

	public static String getMyId(){
		return ServerNode.getInstance().getClusterNodeSelf().getId();
	}
	public static String getClusterNode(){
		return ServerNode.getInstance().getClusterNodeSelf().toString();
	}
}
