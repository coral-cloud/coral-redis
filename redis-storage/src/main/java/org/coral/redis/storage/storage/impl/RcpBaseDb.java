package org.coral.redis.storage.storage.impl;


import org.coral.redis.storage.storage.StorageDb;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wuhao
 * @createTime 2021-06-24 14:41:00
 */
public abstract class RcpBaseDb implements StorageDb {

	private static int LOCK_NUMBER = 128;
	private static ReadWriteLock[] LOCKS = new ReadWriteLock[LOCK_NUMBER];

	static {
		for (int i = 0; i < LOCK_NUMBER; i++) {
			LOCKS[i] = new ReentrantReadWriteLock();
		}
	}

	/**
	 * getLock
	 *
	 * @param value
	 * @return
	 */
	public ReadWriteLock getLock(String value) {
		return LOCKS[Math.abs(value.hashCode() % LOCK_NUMBER)];
	}

}
