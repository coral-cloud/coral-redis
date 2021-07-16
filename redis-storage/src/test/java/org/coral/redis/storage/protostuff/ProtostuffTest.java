package org.coral.redis.storage.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author wuhao
 * @description: ProtostuffTest
 * @createTime 2021/07/07 23:49:00
 */

public class ProtostuffTest {
	public static void main(String[] args) {
		testNew();
		testLong();
	}

	public static void testPro() {
		Person p = new Person("mushan", 20);
		System.out.println("create: " + p);
		LinkedBuffer buffer = LinkedBuffer.allocate();
		Schema<Person> schema = RuntimeSchema.getSchema(Person.class);

		byte[] protobuf = ProtobufIOUtil.toByteArray(p, schema, buffer);

		Person person = schema.newMessage();
		ProtobufIOUtil.mergeFrom(protobuf, person, schema);
		System.out.println("parse: " + person);
	}

	public static void testNew() {
		Person p = new Person("mushan", 20);
		System.out.println("create: " + p);

		byte[] protobuf = ObjectUtils.toBytes(p);
		Person person = (Person) ObjectUtils.toObject(protobuf, Person.class);
		System.out.println("parse: " + person);
	}

	public static void testLong() {
		Long test1 = 3l;
		System.out.println("create: " + test1);

		byte[] protobuf = ObjectUtils.toBytes(test1);
		Long test2 = (Long) ObjectUtils.toObject(protobuf, Long.class);
		System.out.println("parse: " + test2);
	}
}
