## RocksDB简介
RocksDB是使用C++编写的嵌入式kv存储引擎，其键值均允许使用二进制流。
由Facebook基于levelDB开发， 提供向后兼容的levelDB API。

RocksDB针对Flash存储进行优化，延迟极小。RocksDB使用LSM存储引擎，纯C++编写。
Java版本RocksJava正在开发中。参见RocksJavaBasic。

RocksDB依靠大量灵活的配置，使之能针对不同的生产环境进行调优，包括直接使用内存，使用Flash，使用硬盘或者HDFS。
支持使用不同的压缩算法，并且有一套完整的工具供生产和调试使用。

中文文档请参照：https://rocksdb.org.cn/doc/getting-started.html


### 