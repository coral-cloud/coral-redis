package com.ctrip.xpipe.redis.core.metaserver;

import com.ctrip.xpipe.redis.core.entity.KeeperContainerMeta;

/**
 * @author wenchao.meng
 * <p>
 * Aug 2, 2016
 */
public interface MetaServerKeeperService extends MetaServerService {

	String PATH_PING = "cluster/{clusterId}/shard/{shardId}/ping";

	String PATH_GET_ALL_KEEPERS = "getallkeepers";

	/*********************** for keeper *******************/

//	void ping(String clusterId, String shardId, KeeperInstanceMeta keeperInstanceMeta);

	/*********************** for container *******************/

	/**
	 * meta server merge all meta server's results
	 *
	 * @param keeperContainerMeta
	 * @return
	 */
//	List<KeeperTransMeta> getAllKeepersByKeeperContainer(KeeperContainerMeta keeperContainerMeta);

	KeeperContainerTokenStatusResponse refreshKeeperContainerTokenStatus(KeeperContainerTokenStatusRequest request);

	class KeeperContainerTokenStatusResponse {
		private int tokenSize;

		public KeeperContainerTokenStatusResponse(int tokenSize) {
			this.tokenSize = tokenSize;
		}

		public int getTokenSize() {
			return tokenSize;
		}

	}

	class KeeperContainerTokenStatusRequest {

		private KeeperContainerMeta keeperContainerMeta;

		private int keeperNum;

		private int acked;

		public KeeperContainerTokenStatusRequest(KeeperContainerMeta keeperContainerMeta, int keeperNum, int acked) {
			this.keeperContainerMeta = keeperContainerMeta;
			this.keeperNum = keeperNum;
			this.acked = acked;
		}

		public KeeperContainerMeta getKeeperContainerMeta() {
			return keeperContainerMeta;
		}

		public int getKeeperNum() {
			return keeperNum;
		}

		public int getAcked() {
			return acked;
		}

		@Override
		public String toString() {
			return "KeeperContainerTokenStatusRequest{" +
					"keeperContainerMeta=" + keeperContainerMeta +
					", keeperNum=" + keeperNum +
					", acked=" + acked +
					'}';
		}
	}

}
