package org.coral.redis.manager;

import io.netty.channel.ChannelHandlerContext;
import org.coral.redis.cluster.handler.RcpSynRealTimeHandler;
import org.coral.redis.cluster.handler.RcpSynStorageHandler;

/**
 * NodeContext
 *
 * @author wuhao
 * @createTime 2022-01-21 16:50:00
 */
public class NodeContext {
	private String uid;
	private ChannelHandlerContext ctx;
	private byte[] index;
	private RcpSynRealTimeHandler realTimeHandler;
	private RcpSynStorageHandler rcpSynStorageHandler;

	public String getUid() {
		return uid;
	}

	public NodeContext setUid(String uid) {
		this.uid = uid;
		return this;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public NodeContext setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		return this;
	}

	public byte[] getIndex() {
		return index;
	}

	public NodeContext setIndex(byte[] index) {
		this.index = index;
		return this;
	}

	public RcpSynRealTimeHandler getRealTimeHandler() {
		return realTimeHandler;
	}

	public NodeContext setRealTimeHandler(RcpSynRealTimeHandler realTimeHandler) {
		this.realTimeHandler = realTimeHandler;
		return this;
	}

	public RcpSynStorageHandler getRcpSynStorageHandler() {
		return rcpSynStorageHandler;
	}

	public NodeContext setRcpSynStorageHandler(RcpSynStorageHandler rcpSynStorageHandler) {
		this.rcpSynStorageHandler = rcpSynStorageHandler;
		return this;
	}
}
