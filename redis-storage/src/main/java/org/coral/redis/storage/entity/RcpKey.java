package org.coral.redis.storage.entity;

import org.coral.redis.storage.protostuff.ObjectUtils;

/**
 * @author wuhao
 * @description: RKey
 * @createTime 2021/07/07 23:26:00
 */

public interface RcpKey {
	/**
	 * 返回key值
	 *
	 * @return
	 */
	byte[] getKey();

	/**
	 *
	 * @return
	 */
	default String getKeyString(){
		if (getKey() == null){
			return "";
		}
		return new String(getKey());
	}


}
