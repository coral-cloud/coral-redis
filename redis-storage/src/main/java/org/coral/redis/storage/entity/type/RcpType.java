package org.coral.redis.storage.entity.type;

/**
 * @author wuhao
 * @description: RcpType
 * @createTime 2021/07/07 23:27:00
 */
public enum RcpType {
	STRING(1),
	HASH(2),
	LIST(3),
	ZSET(4),
	SET(5),
	STREAM(6),
	HYPER_LOG_LOG(7),
	SCRIPT(8),
	GE0(9),
	TRANSACTION(10);

	private int value;

	RcpType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}