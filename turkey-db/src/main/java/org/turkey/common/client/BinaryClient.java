package org.turkey.common.client;

import org.turkey.common.command.Commands;
import redis.clients.jedis.*;
import redis.clients.jedis.args.ListDirection;
import redis.clients.jedis.args.UnblockType;
import redis.clients.jedis.params.*;

import java.util.Map;

/**
 * @author wuhao
 * @createTime 2021-06-11 17:17:00
 */
public class BinaryClient implements Commands {
	@Override
	public void copy(String srcKey, String dstKey, int db, boolean replace) {

	}

	@Override
	public void copy(String srcKey, String dstKey, boolean replace) {

	}

	@Override
	public void ping(String message) {

	}

	@Override
	public void set(String key, String value) {

	}

	@Override
	public void set(String key, String value, SetParams params) {

	}

	@Override
	public void get(String key) {

	}

	@Override
	public void getDel(String key) {

	}

	@Override
	public void getEx(String key, GetExParams params) {

	}

	@Override
	public void exists(String... keys) {

	}

	@Override
	public void del(String... keys) {

	}

	@Override
	public void unlink(String... keys) {

	}

	@Override
	public void type(String key) {

	}

	@Override
	public void keys(String pattern) {

	}

	@Override
	public void rename(String oldkey, String newkey) {

	}

	@Override
	public void renamenx(String oldkey, String newkey) {

	}

	@Override
	public void expire(String key, long seconds) {

	}

	@Override
	public void expireAt(String key, long unixTime) {

	}

	@Override
	public void ttl(String key) {

	}

	@Override
	public void pttl(String key) {

	}

	@Override
	public void touch(String... keys) {

	}

	@Override
	public void setbit(String key, long offset, boolean value) {

	}

	@Override
	public void setbit(String key, long offset, String value) {

	}

	@Override
	public void getbit(String key, long offset) {

	}

	@Override
	public void setrange(String key, long offset, String value) {

	}

	@Override
	public void getrange(String key, long startOffset, long endOffset) {

	}

	@Override
	public void move(String key, int dbIndex) {

	}

	@Override
	public void getSet(String key, String value) {

	}

	@Override
	public void mget(String... keys) {

	}

	@Override
	public void setnx(String key, String value) {

	}

	@Override
	public void setex(String key, long seconds, String value) {

	}

	@Override
	public void mset(String... keysvalues) {

	}

	@Override
	public void msetnx(String... keysvalues) {

	}

	@Override
	public void decrBy(String key, long decrement) {

	}

	@Override
	public void decr(String key) {

	}

	@Override
	public void incrBy(String key, long increment) {

	}

	@Override
	public void incrByFloat(String key, double increment) {

	}

	@Override
	public void incr(String key) {

	}

	@Override
	public void append(String key, String value) {

	}

	@Override
	public void substr(String key, int start, int end) {

	}

	@Override
	public void hset(String key, String field, String value) {

	}

	@Override
	public void hget(String key, String field) {

	}

	@Override
	public void hset(String key, Map<String, String> hash) {

	}

	@Override
	public void hsetnx(String key, String field, String value) {

	}

	@Override
	public void hmset(String key, Map<String, String> hash) {

	}

	@Override
	public void hmget(String key, String... fields) {

	}

	@Override
	public void hincrBy(String key, String field, long value) {

	}

	@Override
	public void hincrByFloat(String key, String field, double value) {

	}

	@Override
	public void hexists(String key, String field) {

	}

	@Override
	public void hdel(String key, String... fields) {

	}

	@Override
	public void hlen(String key) {

	}

	@Override
	public void hkeys(String key) {

	}

	@Override
	public void hvals(String key) {

	}

	@Override
	public void hrandfield(String key) {

	}

	@Override
	public void hrandfield(String key, long count) {

	}

	@Override
	public void hrandfieldWithValues(String key, long count) {

	}

	@Override
	public void hgetAll(String key) {

	}

	@Override
	public void rpush(String key, String... strings) {

	}

	@Override
	public void lpush(String key, String... strings) {

	}

	@Override
	public void llen(String key) {

	}

	@Override
	public void lrange(String key, long start, long stop) {

	}

	@Override
	public void ltrim(String key, long start, long stop) {

	}

	@Override
	public void lindex(String key, long index) {

	}

	@Override
	public void lset(String key, long index, String value) {

	}

	@Override
	public void lrem(String key, long count, String value) {

	}

	@Override
	public void lpop(String key) {

	}

	@Override
	public void lpop(String key, int count) {

	}

	@Override
	public void lpos(String key, String element) {

	}

	@Override
	public void lpos(String key, String element, LPosParams params) {

	}

	@Override
	public void lpos(String key, String element, LPosParams params, long count) {

	}

	@Override
	public void rpop(String key) {

	}

	@Override
	public void rpop(String key, int count) {

	}

	@Override
	public void rpoplpush(String srckey, String dstkey) {

	}

	@Override
	public void sadd(String key, String... members) {

	}

	@Override
	public void smembers(String key) {

	}

	@Override
	public void srem(String key, String... member) {

	}

	@Override
	public void spop(String key) {

	}

	@Override
	public void spop(String key, long count) {

	}

	@Override
	public void smove(String srckey, String dstkey, String member) {

	}

	@Override
	public void scard(String key) {

	}

	@Override
	public void sismember(String key, String member) {

	}

	@Override
	public void smismember(String key, String... members) {

	}

	@Override
	public void sinter(String... keys) {

	}

	@Override
	public void sinterstore(String dstkey, String... keys) {

	}

	@Override
	public void sunion(String... keys) {

	}

	@Override
	public void sunionstore(String dstkey, String... keys) {

	}

	@Override
	public void sdiff(String... keys) {

	}

	@Override
	public void sdiffstore(String dstkey, String... keys) {

	}

	@Override
	public void zdiff(String... keys) {

	}

	@Override
	public void zdiffWithScores(String... keys) {

	}

	@Override
	public void srandmember(String key) {

	}

	@Override
	public void zadd(String key, double score, String member) {

	}

	@Override
	public void zadd(String key, double score, String member, ZAddParams params) {

	}

	@Override
	public void zadd(String key, Map<String, Double> scoreMembers) {

	}

	@Override
	public void zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {

	}

	@Override
	public void zaddIncr(String key, double score, String member, ZAddParams params) {

	}

	@Override
	public void zdiffStore(String dstkey, String... keys) {

	}

	@Override
	public void zrange(String key, long start, long stop) {

	}

	@Override
	public void zrem(String key, String... members) {

	}

	@Override
	public void zincrby(String key, double increment, String member) {

	}

	@Override
	public void zincrby(String key, double increment, String member, ZIncrByParams params) {

	}

	@Override
	public void zrank(String key, String member) {

	}

	@Override
	public void zrevrank(String key, String member) {

	}

	@Override
	public void zrevrange(String key, long start, long stop) {

	}

	@Override
	public void zrangeWithScores(String key, long start, long stop) {

	}

	@Override
	public void zrevrangeWithScores(String key, long start, long stop) {

	}

	@Override
	public void zrandmember(String key) {

	}

	@Override
	public void zrandmember(String key, long count) {

	}

	@Override
	public void zrandmemberWithScores(String key, long count) {

	}

	@Override
	public void zcard(String key) {

	}

	@Override
	public void zscore(String key, String member) {

	}

	@Override
	public void zmscore(String key, String... members) {

	}

	@Override
	public void zpopmax(String key) {

	}

	@Override
	public void zpopmax(String key, int count) {

	}

	@Override
	public void zpopmin(String key) {

	}

	@Override
	public void zpopmin(String key, long count) {

	}

	@Override
	public void watch(String... keys) {

	}

	@Override
	public void sort(String key) {

	}

	@Override
	public void sort(String key, SortingParams sortingParameters) {

	}

	@Override
	public void sort(String key, SortingParams sortingParameters, String dstkey) {

	}

	@Override
	public void sort(String key, String dstkey) {

	}

	@Override
	public void lmove(String srcKey, String dstKey, ListDirection from, ListDirection to) {

	}

	@Override
	public void blmove(String srcKey, String dstKey, ListDirection from, ListDirection to, double timeout) {

	}

	@Override
	public void blpop(String[] args) {

	}

	@Override
	public void blpop(int timeout, String... keys) {

	}

	@Override
	public void blpop(double timeout, String... keys) {

	}

	@Override
	public void brpop(String[] args) {

	}

	@Override
	public void brpop(int timeout, String... keys) {

	}

	@Override
	public void brpop(double timeout, String... keys) {

	}

	@Override
	public void brpoplpush(String source, String destination, int timeout) {

	}

	@Override
	public void bzpopmax(double timeout, String... keys) {

	}

	@Override
	public void bzpopmin(double timeout, String... keys) {

	}

	@Override
	public void zcount(String key, double min, double max) {

	}

	@Override
	public void zcount(String key, String min, String max) {

	}

	@Override
	public void zrangeByScore(String key, double min, double max) {

	}

	@Override
	public void zrangeByScore(String key, String min, String max) {

	}

	@Override
	public void zrangeByScore(String key, double min, double max, int offset, int count) {

	}

	@Override
	public void zrangeByScore(String key, String min, String max, int offset, int count) {

	}

	@Override
	public void zrangeByScoreWithScores(String key, double min, double max) {

	}

	@Override
	public void zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {

	}

	@Override
	public void zrangeByScoreWithScores(String key, String min, String max) {

	}

	@Override
	public void zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {

	}

	@Override
	public void zrevrangeByScore(String key, double max, double min) {

	}

	@Override
	public void zrevrangeByScore(String key, String max, String min) {

	}

	@Override
	public void zrevrangeByScore(String key, double max, double min, int offset, int count) {

	}

	@Override
	public void zrevrangeByScore(String key, String max, String min, int offset, int count) {

	}

	@Override
	public void zrevrangeByScoreWithScores(String key, double max, double min) {

	}

	@Override
	public void zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {

	}

	@Override
	public void zrevrangeByScoreWithScores(String key, String max, String min) {

	}

	@Override
	public void zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {

	}

	@Override
	public void zremrangeByRank(String key, long start, long stop) {

	}

	@Override
	public void zremrangeByScore(String key, double min, double max) {

	}

	@Override
	public void zremrangeByScore(String key, String min, String max) {

	}

	@Override
	public void zunion(ZParams params, String... keys) {

	}

	@Override
	public void zunionWithScores(ZParams params, String... keys) {

	}

	@Override
	public void zunionstore(String dstkey, String... sets) {

	}

	@Override
	public void zunionstore(String dstkey, ZParams params, String... sets) {

	}

	@Override
	public void zinter(ZParams params, String... keys) {

	}

	@Override
	public void zinterWithScores(ZParams params, String... keys) {

	}

	@Override
	public void zinterstore(String dstkey, String... sets) {

	}

	@Override
	public void zinterstore(String dstkey, ZParams params, String... sets) {

	}

	@Override
	public void strlen(String key) {

	}

	@Override
	public void lpushx(String key, String... string) {

	}

	@Override
	public void persist(String key) {

	}

	@Override
	public void rpushx(String key, String... string) {

	}

	@Override
	public void echo(String string) {

	}

	@Override
	public void linsert(String key, ListPosition where, String pivot, String value) {

	}

	@Override
	public void bgrewriteaof() {

	}

	@Override
	public void bgsave() {

	}

	@Override
	public void lastsave() {

	}

	@Override
	public void save() {

	}

	@Override
	public void configSet(String parameter, String value) {

	}

	@Override
	public void configGet(String pattern) {

	}

	@Override
	public void configResetStat() {

	}

	@Override
	public void multi() {

	}

	@Override
	public void exec() {

	}

	@Override
	public void discard() {

	}

	@Override
	public void objectRefcount(String key) {

	}

	@Override
	public void objectIdletime(String key) {

	}

	@Override
	public void objectEncoding(String key) {

	}

	@Override
	public void objectHelp() {

	}

	@Override
	public void objectFreq(String key) {

	}

	@Override
	public void bitcount(String key) {

	}

	@Override
	public void bitcount(String key, long start, long end) {

	}

	@Override
	public void bitop(BitOP op, String destKey, String... srcKeys) {

	}

	@Override
	public void dump(String key) {

	}

	@Override
	public void restore(String key, long ttl, byte[] serializedValue) {

	}

	@Override
	public void restoreReplace(String key, long ttl, byte[] serializedValue) {

	}

	@Override
	public void restore(String key, long ttl, byte[] serializedValue, RestoreParams params) {

	}

	@Override
	public void scan(String cursor, ScanParams params) {

	}

	@Override
	public void hscan(String key, String cursor, ScanParams params) {

	}

	@Override
	public void sscan(String key, String cursor, ScanParams params) {

	}

	@Override
	public void zscan(String key, String cursor, ScanParams params) {

	}

	@Override
	public void waitReplicas(int replicas, long timeout) {

	}

	@Override
	public void bitfield(String key, String... arguments) {

	}

	@Override
	public void bitfieldReadonly(String key, String... arguments) {

	}

	@Override
	public void hstrlen(String key, String field) {

	}

	@Override
	public void migrate(String host, int port, String key, int destinationDB, int timeout) {

	}

	@Override
	public void migrate(String host, int port, int destinationDB, int timeout, MigrateParams params, String... keys) {

	}

	@Override
	public void clientKill(String ipPort) {

	}

	@Override
	public void clientKill(String ip, int port) {

	}

	@Override
	public void clientKill(ClientKillParams params) {

	}

	@Override
	public void clientGetname() {

	}

	@Override
	public void clientList() {

	}

	@Override
	public void clientList(long... clientIds) {

	}

	@Override
	public void clientInfo() {

	}

	@Override
	public void clientSetname(String name) {

	}

	@Override
	public void clientId() {

	}

	@Override
	public void clientUnblock(long clientId, UnblockType unblockType) {

	}

	@Override
	public void memoryDoctor() {

	}

	@Override
	public void xadd(String key, StreamEntryID id, Map<String, String> hash, long maxLen, boolean approximateLength) {

	}

	@Override
	public void xadd(String key, Map<String, String> hash, XAddParams params) {

	}

	@Override
	public void xlen(String key) {

	}

	@Override
	public void xrange(String key, StreamEntryID start, StreamEntryID end) {

	}

	@Override
	public void xrange(String key, StreamEntryID start, StreamEntryID end, int count) {

	}

	@Override
	public void xrange(String key, StreamEntryID start, StreamEntryID end, long count) {

	}

	@Override
	public void xrevrange(String key, StreamEntryID end, StreamEntryID start) {

	}

	@Override
	public void xrevrange(String key, StreamEntryID end, StreamEntryID start, int count) {

	}

	@Override
	public void xread(int count, long block, Map.Entry<String, StreamEntryID>... streams) {

	}

	@Override
	public void xread(XReadParams params, Map<String, StreamEntryID> streams) {

	}

	@Override
	public void xack(String key, String group, StreamEntryID... ids) {

	}

	@Override
	public void xgroupCreate(String key, String consumer, StreamEntryID id, boolean makeStream) {

	}

	@Override
	public void xgroupSetID(String key, String consumer, StreamEntryID id) {

	}

	@Override
	public void xgroupDestroy(String key, String consumer) {

	}

	@Override
	public void xgroupDelConsumer(String key, String consumer, String consumerName) {

	}

	@Override
	public void xdel(String key, StreamEntryID... ids) {

	}

	@Override
	public void xtrim(String key, long maxLen, boolean approximateLength) {

	}

	@Override
	public void xtrim(String key, XTrimParams params) {

	}

	@Override
	public void xreadGroup(String groupname, String consumer, int count, long block, boolean noAck, Map.Entry<String, StreamEntryID>... streams) {

	}

	@Override
	public void xreadGroup(String groupname, String consumer, XReadGroupParams params, Map<String, StreamEntryID> streams) {

	}

	@Override
	public void xpending(String key, String groupname) {

	}

	@Override
	public void xpending(String key, String groupname, StreamEntryID start, StreamEntryID end, int count, String consumername) {

	}

	@Override
	public void xpending(String key, String groupname, XPendingParams params) {

	}

	@Override
	public void xclaim(String key, String group, String consumername, long minIdleTime, long newIdleTime, int retries, boolean force, StreamEntryID... ids) {

	}

	@Override
	public void xclaim(String key, String group, String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {

	}

	@Override
	public void xclaimJustId(String key, String group, String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {

	}

	@Override
	public void xautoclaim(String key, String group, String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {

	}

	@Override
	public void xautoclaimJustId(String key, String group, String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {

	}

	@Override
	public void xinfoStream(String key) {

	}

	@Override
	public void xinfoGroup(String key) {

	}

	@Override
	public void xinfoConsumers(String key, String group) {

	}
}
