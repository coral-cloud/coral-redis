package org.coral.redis.storage.entity.data;

import org.coral.redis.storage.protostuff.ObjectUtils;

/**
 * @author wuhao
 * @description: RcpContent
 * @createTime 2021/12/10 00:04:00
 */

public class RcpContent {
	private RcpType rcpType;
	private byte[] content;

	public RcpType getRcpType() {
		return rcpType;
	}

	public byte[] getContent() {
		return content;
	}

	public RcpContent(RcpType rcpType, byte[] content) {
		this.rcpType = rcpType;
		this.content = content;
	}

	public static byte[] encode(RcpType rcpType, byte[] bytes){
		RcpContent rcpContent = new RcpContent(rcpType, bytes);
		return ObjectUtils.toBytes(rcpContent);
	}

	public static RcpContent decode(byte[] bytes){
		RcpContent rcpContent = ObjectUtils.toObject(bytes, RcpContent.class);
		return rcpContent;
	}
}
