package org.coral.redis.server;

/**
 * @author wuhao
 * @createTime 2021-06-25 15:10:00
 */
public class CommandSign {
	public static final String SET = "SET";
	public static final String GET = "GET";
	public static final String PING = "PING";
	public static final String PONG = "PONG";
	public static final String ZADD = "ZADD";
	public static final String ZRANGE = "ZRANGE";
	public static final String LPUSH = "LPUSH";
	public static final String LPOP = "LPOP";

	public static final String CLUSTER = "CLUSTER";

	public static final String P_EX = "EX";
	public static final String P_PX = "PX";
	public static final String P_NX = "NX";
	public static final String P_XX = "XX";
	public static final String P_WITHSCORES = "WITHSCORES";
}
