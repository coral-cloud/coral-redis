package org.coral.redis.command;


import org.coral.redis.server.handler.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caojiajun on 2019/11/18.
 */
public enum RedisCommand {

	/**
	 * FULL
	 */
	PING(SPType.FULL, OPType.READ, CMDType.DB, null, new PingHandler()),
	INFO(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	AUTH(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	QUIT(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	SET(SPType.FULL, OPType.WRITE, CMDType.STRING, null, new StringHandler()),
	GET(SPType.FULL, OPType.READ, CMDType.STRING, null, new StringHandler()),
	EXISTS(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	DEL(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	TYPE(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	EXPIRE(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	EXPIREAT(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	TTL(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	GETSET(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	MGET(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	SETNX(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	SETEX(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	MSET(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	SUBSTR(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	DECRBY(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	DECR(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	INCRBY(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	INCR(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	APPEND(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	HSET(SPType.FULL, OPType.WRITE, CMDType.HASH, null, null),
	HGET(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	HSETNX(SPType.FULL, OPType.WRITE, CMDType.HASH, null, null),
	HMSET(SPType.FULL, OPType.WRITE, CMDType.HASH, null, null),
	HMGET(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	HINCRBY(SPType.FULL, OPType.WRITE, CMDType.HASH, null, null),
	HEXISTS(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	HDEL(SPType.FULL, OPType.WRITE, CMDType.HASH, null, null),
	HLEN(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	HKEYS(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	HVALS(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	HGETALL(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	RPUSH(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	LPUSH(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	LLEN(SPType.FULL, OPType.READ, CMDType.LIST, null, null),
	LRANGE(SPType.FULL, OPType.READ, CMDType.LIST, null, null),
	LTRIM(SPType.FULL, OPType.READ, CMDType.LIST, null, null),
	LINDEX(SPType.FULL, OPType.READ, CMDType.LIST, null, null),
	LSET(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	LREM(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	LPOP(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	RPOP(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	SADD(SPType.FULL, OPType.WRITE, CMDType.SET, null, null),
	SMEMBERS(SPType.FULL, OPType.READ, CMDType.SET, null, null),
	SREM(SPType.FULL, OPType.WRITE, CMDType.SET, null, null),
	SPOP(SPType.FULL, OPType.WRITE, CMDType.SET, null, null),
	SCARD(SPType.FULL, OPType.READ, CMDType.SET, null, null),
	SISMEMBER(SPType.FULL, OPType.READ, CMDType.SET, null, null),
	SRANDMEMBER(SPType.FULL, OPType.READ, CMDType.SET, null, null),
	ZADD(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZINCRBY(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZRANK(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZCARD(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZSCORE(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	SORT(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	ZCOUNT(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZRANGE(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZRANGEBYSCORE(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZRANGEBYLEX(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZREVRANK(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZREVRANGE(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZREVRANGEBYSCORE(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZREVRANGEBYLEX(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZREM(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZREMRANGEBYRANK(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZREMRANGEBYSCORE(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZREMRANGEBYLEX(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZLEXCOUNT(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZMSCORE(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	ZPOPMAX(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	ZPOPMIN(SPType.FULL, OPType.WRITE, CMDType.ZSET, null, null),
	STRLEN(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	LPUSHX(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	PERSIST(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	RPUSHX(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	LINSERT(SPType.FULL, OPType.WRITE, CMDType.LIST, null, null),
	SETBIT(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	GETBIT(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	BITPOS(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	SETRANGE(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	GETRANGE(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	BITCOUNT(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	PEXPIRE(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	PEXPIREAT(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	PTTL(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	INCRBYFLOAT(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	PSETEX(SPType.FULL, OPType.WRITE, CMDType.STRING, null, null),
	CLIENT(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	HINCRBYFLOAT(SPType.FULL, OPType.WRITE, CMDType.HASH, null, null),
	HSCAN(SPType.FULL, OPType.READ, CMDType.HASH, null, null),
	SSCAN(SPType.FULL, OPType.READ, CMDType.SET, null, null),
	ZSCAN(SPType.FULL, OPType.READ, CMDType.ZSET, null, null),
	GEOADD(SPType.FULL, OPType.WRITE, CMDType.GE0, null, null),
	GEODIST(SPType.FULL, OPType.READ, CMDType.GE0, null, null),
	GEOHASH(SPType.FULL, OPType.READ, CMDType.GE0, null, null),
	GEOPOS(SPType.FULL, OPType.READ, CMDType.GE0, null, null),
	GEORADIUS(SPType.FULL, OPType.READ, CMDType.GE0, null, null),
	GEORADIUSBYMEMBER(SPType.FULL, OPType.READ, CMDType.GE0, null, null),
	GEOSEARCH(SPType.FULL, OPType.READ, CMDType.GE0, null, null),
	BITFIELD(SPType.FULL, OPType.READ, CMDType.STRING, null, null),
	ECHO(SPType.FULL, OPType.READ, CMDType.DB, null, null),
	PFADD(SPType.FULL, OPType.WRITE, CMDType.HYPER_LOG_LOG, null, null),
	XACK(SPType.FULL, OPType.WRITE, CMDType.STREAM, null, null),
	XADD(SPType.FULL, OPType.WRITE, CMDType.STREAM, null, null),
	XCLAIM(SPType.FULL, OPType.WRITE, CMDType.STREAM, null, null),
	XDEL(SPType.FULL, OPType.WRITE, CMDType.STREAM, null, null),
	XLEN(SPType.FULL, OPType.READ, CMDType.STREAM, null, null),
	XPENDING(SPType.FULL, OPType.READ, CMDType.STREAM, null, null),
	XRANGE(SPType.FULL, OPType.READ, CMDType.STREAM, null, null),
	XREVRANGE(SPType.FULL, OPType.READ, CMDType.STREAM, null, null),
	XTRIM(SPType.FULL, OPType.WRITE, CMDType.STREAM, null, null),
	XGROUP(SPType.FULL, OPType.WRITE, CMDType.STREAM, null, null),
	XINFO(SPType.FULL, OPType.READ, CMDType.STREAM, null, null),
	UNLINK(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	TOUCH(SPType.FULL, OPType.WRITE, CMDType.DB, null, null),
	LPOS(SPType.FULL, OPType.READ, CMDType.LIST, null, null),
	SMISMEMBER(SPType.FULL, OPType.READ, CMDType.SET, null, null),
	HSTRLEN(SPType.FULL, OPType.READ, CMDType.HASH, null, null),

	/**
	 * Restrictive Support
	 * support only when all the keys in these command route to same redis-server or same redis-cluster slot
	 * especially, blocking command don't support multi-write
	 */
	EVAL(SPType.RESTRICTIVE, OPType.WRITE, CMDType.SCRIPT, null, null),
	EVALSHA(SPType.RESTRICTIVE, OPType.WRITE, CMDType.SCRIPT, null, null),
	PFCOUNT(SPType.RESTRICTIVE, OPType.READ, CMDType.HYPER_LOG_LOG, null, null),
	PFMERGE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.HYPER_LOG_LOG, null, null),
	RENAME(SPType.RESTRICTIVE, OPType.WRITE, CMDType.DB, null, null),
	RENAMENX(SPType.RESTRICTIVE, OPType.WRITE, CMDType.DB, null, null),
	SINTER(SPType.RESTRICTIVE, OPType.READ, CMDType.SET, null, null),
	SINTERSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.SET, null, null),
	SUNION(SPType.RESTRICTIVE, OPType.READ, CMDType.SET, null, null),
	SUNIONSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.SET, null, null),
	SDIFF(SPType.RESTRICTIVE, OPType.READ, CMDType.SET, null, null),
	SDIFFSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.SET, null, null),
	SMOVE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.SET, null, null),
	ZUNIONSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.ZSET, null, null),
	ZINTERSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.ZSET, null, null),
	BITOP(SPType.RESTRICTIVE, OPType.WRITE, CMDType.STRING, null, null),
	MSETNX(SPType.RESTRICTIVE, OPType.WRITE, CMDType.STRING, null, null),
	BLPOP(SPType.RESTRICTIVE, OPType.WRITE, CMDType.LIST, null, null),
	BRPOP(SPType.RESTRICTIVE, OPType.WRITE, CMDType.LIST, null, null),
	BZPOPMAX(SPType.RESTRICTIVE, OPType.WRITE, CMDType.ZSET, null, null),
	BZPOPMIN(SPType.RESTRICTIVE, OPType.WRITE, CMDType.ZSET, null, null),
	RPOPLPUSH(SPType.RESTRICTIVE, OPType.WRITE, CMDType.LIST, null, null),
	BRPOPLPUSH(SPType.RESTRICTIVE, OPType.WRITE, CMDType.LIST, null, null),
	XREADGROUP(SPType.RESTRICTIVE, OPType.WRITE, CMDType.STREAM, null, null),
	XREAD(SPType.RESTRICTIVE, OPType.WRITE, CMDType.STREAM, null, null),
	GEOSEARCHSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.GE0, null, null),
	ZDIFFSTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.ZSET, null, null),
	ZDIFF(SPType.RESTRICTIVE, OPType.READ, CMDType.ZSET, null, null),
	ZINTER(SPType.RESTRICTIVE, OPType.READ, CMDType.ZSET, null, null),
	ZUNION(SPType.RESTRICTIVE, OPType.READ, CMDType.ZSET, null, null),
	ZRANGESTORE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.ZSET, null, null),
	LMOVE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.LIST, null, null),
	BLMOVE(SPType.RESTRICTIVE, OPType.WRITE, CMDType.LIST, null, null),

	/**
	 * Partially Support
	 * only support while have singleton-upstream(no custom shading) (standalone-redis or redis-sentinel or redis-cluster)
	 */
	SUBSCRIBE(SPType.PARTIALLY_1, OPType.WRITE, CMDType.PUB_SUB, null, null),
	PUBLISH(SPType.PARTIALLY_1, OPType.WRITE, CMDType.PUB_SUB, null, null),
	UNSUBSCRIBE(SPType.PARTIALLY_1, OPType.WRITE, CMDType.PUB_SUB, null, null),
	PSUBSCRIBE(SPType.PARTIALLY_1, OPType.WRITE, CMDType.PUB_SUB, null, null),
	PUNSUBSCRIBE(SPType.PARTIALLY_1, OPType.WRITE, CMDType.PUB_SUB, null, null),
	PUBSUB(SPType.PARTIALLY_1, OPType.READ, CMDType.PUB_SUB, null, null),

	/**
	 * Partially Support
	 * only support while have singleton-upstream(no custom shading) (standalone-redis or redis-sentinel)
	 */
	KEYS(SPType.PARTIALLY_2, OPType.READ, CMDType.DB, null, null),
	SCAN(SPType.PARTIALLY_2, OPType.READ, CMDType.DB, null, null),
	MULTI(SPType.PARTIALLY_2, OPType.WRITE, CMDType.TRANSACTION, null, null),
	DISCARD(SPType.PARTIALLY_2, OPType.WRITE, CMDType.TRANSACTION, null, null),
	EXEC(SPType.PARTIALLY_2, OPType.WRITE, CMDType.TRANSACTION, null, null),
	WATCH(SPType.PARTIALLY_2, OPType.READ, CMDType.TRANSACTION, null, null),
	UNWATCH(SPType.PARTIALLY_2, OPType.READ, CMDType.TRANSACTION, null, null),

	REPLCONF(SPType.FULL, OPType.WRITE, CMDType.DB, null, new ReplConfHandler()),
	PSYNC(SPType.FULL, OPType.WRITE, CMDType.DB, null, new PSynHandler()),
	/**
	 * NOT
	 */

	FLUSHDB(SPType.NOT, OPType.WRITE, null, null, null),
	RANDOMKEY(SPType.NOT, OPType.READ, null, null, null),
	DBSIZE(SPType.NOT, OPType.READ, null, null, null),
	SELECT(SPType.NOT, null, null, null, null),
	MOVE(SPType.NOT, null, null, null, null),
	FLUSHALL(SPType.NOT, OPType.WRITE, null, null, null),
	SAVE(SPType.NOT, null, null, null, null),
	BGSAVE(SPType.NOT, null, null, null, null),
	BGREWRITEAOF(SPType.NOT, null, null, null, null),
	LASTSAVE(SPType.NOT, null, null, null, null),
	SHUTDOWN(SPType.NOT, null, null, null, null),
	MONITOR(SPType.NOT, null, null, null, null),
	SLAVEOF(SPType.FULL, null, null, null, null),
	CONFIG(SPType.NOT, null, null, null, null),
	SYNC(SPType.NOT, null, null, null, null),
	DEBUG(SPType.NOT, null, null, null, null),
	SCRIPT(SPType.NOT, null, null, null, null),
	SLOWLOG(SPType.NOT, null, null, null, null),
	OBJECT(SPType.NOT, null, null, null, null),
	SENTINEL(SPType.NOT, null, null, null, null),
	DUMP(SPType.NOT, null, null, null, null),
	RESTORE(SPType.NOT, null, null, null, null),
	TIME(SPType.NOT, null, null, null, null),
	MIGRATE(SPType.NOT, null, null, null, null),
	WAIT(SPType.NOT, null, null, null, null),
	CLUSTER(SPType.NOT, null, null, null, null),
	ASKING(SPType.NOT, null, null, null, null),
	READONLY(SPType.NOT, null, null, null, null),
	;

	private final SPType spType;
	private final OPType opType;
	private final CMDType cmdType;
	private final NETType netType;
	private final CommandHandler cmdHander;


	RedisCommand(SPType spType, OPType opType, CMDType cmdType, NETType netType, CommandHandler cmdHander) {
		this.spType = spType;
		this.opType = opType;
		this.cmdType = cmdType;
		this.netType = netType;
		this.cmdHander = cmdHander;
	}

	public enum NETType {
		TCP,
		;
	}

	public enum OPType {
		READ,
		WRITE,
		;
	}

	public enum CMDType {
		DB,
		PUB_SUB,
		STRING,
		HASH,
		LIST,
		ZSET,
		SET,
		STREAM,
		HYPER_LOG_LOG,
		SCRIPT,
		GE0,
		TRANSACTION,
		;
	}

	public enum SPType {

		//full support commands
		FULL(1),

		//only support while keys in this command location at the same server or same slot, especially, blocking command don't support multi-write
		RESTRICTIVE(2),

		//only support while have singleton-upstream(no custom shading) [standalone-redis or redis-sentinel or redis-cluster]
		PARTIALLY_1(3),

		//only support while have singleton-upstream(no custom shading) [standalone-redis or redis-sentinel]
		PARTIALLY_2(4),

		//not support
		NOT(Integer.MAX_VALUE),
		;

		private final int value;

		SPType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public SPType getSpType() {
		return spType;
	}

	public OPType getOpType() {
		return opType;
	}

	public CMDType getCmdType() {
		return cmdType;
	}

	public CommandHandler getCmdHander() {
		return cmdHander;
	}

	private static final Map<String, RedisCommand> supportCommandMap = new HashMap<>();
	private static final Map<String, RedisCommand> commandMap = new HashMap<>();

	static {
		for (RedisCommand command : RedisCommand.values()) {
			if (command.getSpType() != SPType.NOT && command.getCmdType() != null) {
				supportCommandMap.put(command.name().toLowerCase(), command);
			}
			commandMap.put(command.name().toLowerCase(), command);
		}

	}

	public static RedisCommand getSupportCmd(String command) {
		return supportCommandMap.get(command.toLowerCase());
	}

	public static RedisCommand getCmd(String command) {
		return commandMap.get(command.toLowerCase());
	}
}
