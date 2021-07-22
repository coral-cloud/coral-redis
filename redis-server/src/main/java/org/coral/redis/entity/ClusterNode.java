package org.coral.redis.entity;

import java.util.UUID;

/**
 * @author wuhao
 * @description: ClusterNode
 * @createTime 2021/07/22 23:03:00
 */

public class ClusterNode {
	//184833235217c242457768709178276ee67b1852 127.0.0.1:7005@17005 myself,master - 0 0 1 connected 0-16383
	private String id = UUID.randomUUID().toString();
	private String ip = "127.0.0.1";
	private int port = 6399;
	private int backPort = 16399;
	private String clusterName = "myslef";
	private String type = "master";
	private int soltStart = 0;
	private int soltEnd = 16383;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getBackPort() {
		return backPort;
	}

	public void setBackPort(int backPort) {
		this.backPort = backPort;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSoltStart() {
		return soltStart;
	}

	public void setSoltStart(int soltStart) {
		this.soltStart = soltStart;
	}

	public int getSoltEnd() {
		return soltEnd;
	}

	public void setSoltEnd(int soltEnd) {
		this.soltEnd = soltEnd;
	}

	public String toString() {
		//184833235217c242457768709178276ee67b1852 127.0.0.1:7005@17005 myself,master - 0 0 1 connected 0-16383
		StringBuilder nodeInfo = new StringBuilder();
		nodeInfo.append(id).append(" ");
		nodeInfo.append(ip).append(":");
		nodeInfo.append(port).append("@").append(backPort).append(" ");
		nodeInfo.append(clusterName).append(",").append(type);
		nodeInfo.append(" - 0 0 1 ");
		nodeInfo.append(soltStart);
		nodeInfo.append("-");
		nodeInfo.append(soltEnd).append("\n");
		return nodeInfo.toString();
	}

	public ClusterNode build(){
		ClusterNode clusterNode = new ClusterNode();
		return clusterNode;
	}
}
