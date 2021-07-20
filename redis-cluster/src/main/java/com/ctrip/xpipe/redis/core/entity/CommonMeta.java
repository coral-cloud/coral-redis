package com.ctrip.xpipe.redis.core.entity;

/**
 * @author wuhao
 * @description: CommonMeta
 * @createTime 2021/07/21 00:05:00
 */

public class CommonMeta {

	public CommonMeta(String id) {
		this.id = id;
	}

	private int port;
	private String ip;

	public String id;


	public CommonMeta parenti;

	public String getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public CommonMeta setPort(int port) {
		this.port = port;
		return this;
	}

	public String getIp() {
		return ip;
	}

	public CommonMeta setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public CommonMeta parent(){
		return parenti;
	}
	public CommonMeta getParent() {
		return parenti;
	}

	public void setParent(CommonMeta parent) {
		this.parenti = parent;
	}
}
