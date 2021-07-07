package org.coral.redis.storage.entity;

/**
 * @author wuhao
 * @description: RData
 * @createTime 2021/07/07 23:27:00
 */

public interface RData {
	/**
	 * 创建更新时间
	 * @return
	 */
	long getTime();

	/**
	 * 当前版本号
	 * @return
	 */
	int getVersion();

	/**
	 * 是否过期
	 *
	 * @return
	 */
	boolean isExpire();

	/**
	 * 返回数据实际内容
	 *
	 * @return
	 */
	byte[] getContent();

	/**
	 * 返回整体字节
	 *
	 * @return
	 */
	byte[] getBytes();
}
