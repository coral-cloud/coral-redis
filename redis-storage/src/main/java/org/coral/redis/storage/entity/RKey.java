package org.coral.redis.storage.entity;

/**
 * @author wuhao
 * @description: RKey
 * @createTime 2021/07/07 23:26:00
 */

public interface RKey {
	/**
	 * 返回key值
	 *
	 * @return
	 */
	byte[] getKey();
}
