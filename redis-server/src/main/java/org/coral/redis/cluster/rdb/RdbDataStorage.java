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
		return staticData();
		//return getData("0007");
	}
	public static byte[] getData(String version) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CRCOutputStream out = new CRCOutputStream(outputStream);
		//version
		out.write("REDIS".getBytes());
		out.write(RdbStrings.lappend(version, 4, '0').getBytes());

		//ctime
		out.write("redis-bits".getBytes());

		out.write("ctime".getBytes());

		out.write("used-mem".getBytes());

		RdbOutputStreams.write(0xFF, out);
		RdbOutputStreams.write(out.getCRC64(), out);
		RdbOutputStreams.close(out);
		return outputStream.toByteArray();
	}

	/**
	 0000001B  2b 46 55 4c 4c 52 45 53  59 4e 43 20 34 39 61 64   +FULLRES YNC 49ad
	 0000002B  65 65 32 61 64 33 63 33  62 63 33 61 32 33 61 38   ee2ad3c3 bc3a23a8
	 0000003B  31 32 64 64 62 62 32 34  30 33 37 32 35 30 37 39   12ddbb24 03725079
	 0000004B  37 31 38 62 20 31 0d 0a                            718b 1..
	 00000053  24 37 37 0d 0a 52 45 44  49 53 30 30 30 37 fa 09   $77..RED IS0007..
	 00000063  72 65 64 69 73 2d 76 65  72 06 33 2e 32 2e 31 32   redis-ve r.3.2.12
	 00000073  fa 0a 72 65 64 69 73 2d  62 69 74 73 c0 40 fa 05   ..redis- bits.@..
	 00000083  63 74 69 6d 65 c2 cf 85  ab 61 fa 08 75 73 65 64   ctime... .a..used
	 00000093  2d 6d 65 6d c2 08 db 0c  00 ff c4 ab b6 03 b8 0d   -mem.... ........
	 000000A3  99 ea                                              ..
	 * @return
	 */

	public static byte[] staticData(){
		byte data[] = { /* Packet 3344 */
				0x52, 0x45, 0x44,
				0x49, 0x53, 0x30, 0x30, 0x30, 0x37, (byte) 0xfa, 0x09,
				0x72, 0x65, 0x64, 0x69, 0x73, 0x2d, 0x76, 0x65,
				0x72, 0x06, 0x33, 0x2e, 0x32, 0x2e, 0x31, 0x32,
				(byte) 0xfa, 0x0a, 0x72, 0x65, 0x64, 0x69, 0x73, 0x2d,
				0x62, 0x69, 0x74, 0x73, (byte) 0xc0, 0x40, (byte) 0xfa, 0x05,
				0x63, 0x74, 0x69, 0x6d, 0x65, (byte) 0xc2, (byte) 0xcf, (byte) 0x85,
				(byte) 0xab, 0x61, (byte) 0xfa, 0x08, 0x75, 0x73, 0x65, 0x64,
				0x2d, 0x6d, 0x65, 0x6d, (byte) 0xc2, 0x08, (byte) 0xdb, 0x0c,
				0x00, (byte) 0xff, (byte) 0xc4, (byte) 0xab, (byte) 0xb6, 0x03, (byte) 0xb8, 0x0d,
				(byte) 0x99, (byte) 0xea};
		return data;
	}
	public static void main(String[] args) throws IOException {
		System.out.println(getData());
	}
}
