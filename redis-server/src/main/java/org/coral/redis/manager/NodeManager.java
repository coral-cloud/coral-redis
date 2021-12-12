package org.coral.redis.manager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wuhao
 * @description: manager
 * @createTime 2021/12/11 13:01:00
 */

public class NodeManager {
	private static LinkedHashMap<String, byte[]> linkedHashMap = new LinkedHashMap<String, byte[]>(){
		@Override
		protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
			return size() > 100;
		}
	};

	public static byte[] getIndex(String id){
		return linkedHashMap.get(id);
	}
	public static void setIndex(String id, byte[] index){
		linkedHashMap.put(id, index);
	}
}
