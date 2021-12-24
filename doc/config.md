## 1.redis中AOF和RDB的关闭方法

* 问题
    * 当往redis中导入数据时，有时会出现redis server went away的情况；

* 原因
    * 导入的数据量太大，而内存不够（即内存1G，但数据有2G）。此时的redis服务需要重启。
    * 可能是同一时间导入的数据太多，导致数据持久化的操作出问题，此时需要关闭rdb跟aof。

### 1.1.关闭rdb

* 命令
```
config set save ""
```
* 配置
```
或者进入配置文件注释掉
Save 900 1
Save 300 10
Save 60 10000
并打开save "" 的注释，使得  save ""  生效，即可关闭rdb；
```

### 1.2.关闭aof
* 关闭aof的命令
```
config set appendfsync no

```
* 配置

```
（或者进入配置文件，将appendonly设置为no，默认是 appendonly no ）

（注：appendfsync 是同步机制，默认为appendfsync  everysec）---每秒同步一次写操作到aof文件中；
```

### 1.3.验证

该两种设置查询是否已修改成功，可分别通过命令来查看。
```
config get save
config get appendfsync
```
注意：该命令都是通过执行redis-cli后方可执行。
RDB 将数据库的快照（snapshot）以二进制的方式保存到磁盘中。
AOF 则以协议文本的方式，将所有对数据库进行过写入的命令（及其参数）记录到 AOF 文件，以此达到记录数据库状态的目的。

### 1.4.关键点
rewrite是aof的一个机制，用来压缩aof文件，通过fork一个子进程，重新写一个新的aof文件，
该次重写不是读取旧的aof文件进行复制，而是将读取内存中的redis数据库，重写一份aof文件，有点类似于rdb的快照方式；
触发机制为：
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
解释：当rof文件的大小，比旧aof文件大百分之百的时候（2倍），且aof文件的大小大于64mb的时候，触发重写机制；