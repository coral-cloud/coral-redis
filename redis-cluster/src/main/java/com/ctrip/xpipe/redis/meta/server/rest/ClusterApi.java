package com.ctrip.xpipe.redis.meta.server.rest;


import com.ctrip.xpipe.redis.meta.server.cluster.ClusterServerInfo;

/**
 * @author wenchao.meng
 * <p>
 * Jul 29, 2016
 */
public interface ClusterApi {

	String PATH_PREFIX = "/api/metacluster";

	String PATH_NOTIFY_SLOT_CHANGE = "/notifyslotchange/{slotId}";
	String PATH_EXPORT_SLOT = "/exportslot/{slotId}";
	String PATH_IMPORT_SLOT = "/importslot/{slotId}";

	String PATH_ADD_SLOT = "/addslot/{slotId}";
	String PATH_DELETE_SLOT = "/deleteslot/{slotId}";

	int getServerId();

	ClusterServerInfo getClusterInfo();

	void addSlot(int slotId) throws Exception;

	void deleteSlot(int slotId) throws Exception;

	void exportSlot(int slotId) throws Exception;

	void importSlot(int slotId) throws Exception;

	void notifySlotChange(int slotId) throws Exception;

	String debug();

	void refresh() throws Exception;

}
