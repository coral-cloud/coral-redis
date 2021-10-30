package org.coral.redis.cluster.rdb;


import com.moilioncircle.redis.replicator.io.CRCOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wuhao
 * @description: RdbDataStorage
 * @createTime 2021/10/28 23:12:00
 */

public class RdbDataStorage {
	public static byte[] getData() throws IOException {
		return getData("0007");
	}
	public static byte[] getData(String version) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CRCOutputStream out = new CRCOutputStream(outputStream);
		out.write("REDIS".getBytes());
		out.write(RdbStrings.lappend(version, 4, '0').getBytes());

		out.write("".getBytes());

		RdbOutputStreams.write(0xFF, out);
		RdbOutputStreams.write(out.getCRC64(), out);
		RdbOutputStreams.close(out);
		return outputStream.toByteArray();
	}
	public static void main(String[] args) throws IOException {
		System.out.println(getData());
	}
}
