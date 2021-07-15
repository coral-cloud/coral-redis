package org.coral.redis.storage.entity;

import org.coral.redis.storage.entity.type.RcpType;
import org.coral.redis.storage.protostuff.ObjectUtils;

/**
 * @author wuhao
 * @description: RcpData
 * @createTime 2021/07/07 23:27:00
 */

public interface RcpData {
	/**
	 * getTime
	 *
	 * @return
	 */
	long getTime();


	/**
	 * 设置过期时间
	 *
	 * @param time
	 */
	void setTime(long time);

	/**
	 * 获取size
	 *
	 * @return
	 */
	int getSize();

	/**
	 * setSize
	 * @param size
	 */
	default void setSize(int size){

	}

	/**
	 * 返回数据实际内容
	 *
	 * @return
	 */
	default byte[] getContent() {
		return null;
	}

	/**
	 * 设置数据内容
	 *
	 * @param content
	 */
	default void setContent(byte[] content) {

	}

	/**
	 * 是否过期
	 *
	 * @return
	 */
	default boolean isExpire() {
		if (getTime() == 0) {
			return false;
		}
		if (getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}


	/**
	 * 返回整体字节
	 *
	 * @return
	 */
	default byte[] getBytes(){
		return ObjectUtils.toBytes(this);
	}

	/**
	 * getRcpType
	 *
	 * @return
	 */
	default RcpType getRcpType(){
		return RcpType.STRING;
	}


}
