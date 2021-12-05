一般把这个配置文件命名为pika.conf，下面是pika.conf的例子

~~~
# Pika port
port : 9221
# Thread Number
thread-num : 1
# Sync Thread Number
sync-thread-num : 6
# Item count of sync thread queue
sync-buffer-size : 10
# Pika log path
log-path : ./log/
# Pika glog level: only INFO and ERROR
loglevel : info
# Pika db path
db-path : ./db/
# Pika write-buffer-size
write-buffer-size : 268435456
# Pika timeout
timeout : 60
# Requirepass
requirepass :
# Userpass
userpass :
# User Blacklist
userblacklist :
# Dump Prefix
dump-prefix :
# daemonize  [yes | no]
#daemonize : yes
# Dump Path
dump-path : ./dump/
# pidfile Path
pidfile : ./pika.pid
# Max Connection
maxclients : 20000
# the per file size of sst to compact, defalut is 2M
target-file-size-base : 20971520
# Expire-logs-days
expire-logs-days : 7
# Expire-logs-nums
expire-logs-nums : 10
# Root-connection-num
root-connection-num : 2
# Slowlog-log-slower-than
slowlog-log-slower-than : 10000
# slave-read-only(yes/no, 1/0)
slave-read-only : 0
# Pika db sync path
db-sync-path : ./dbsync/
# db sync speed(MB) max is set to 125MB, min is set to 0, and if below 0 or above 125, the value will be adjust to 125
db-sync-speed : -1

###################
## Critical Settings
###################
# binlog file size: default is 100M,  limited in [1K, 2G]
binlog-file-size : 104857600
# Compression
compression : snappy
# max-background-flushes: default is 1, limited in [1, 4]
max-background-flushes : 1
# max-background-compactions: default is 1, limited in [1, 4]
max-background-compactions : 1
# max-cache-files default is 5000
max-cache-files : 5000
~~~
下面分别解释各个配置选项的意思：

~~~
port: 表示的是服务器启动的端口号，客户端通过这个端口号和服务端连接
thread-num: 表示的是服务端执行用户连接命令的工作线程的数量（dispatch线程接受用户端，然后分发给thread_num个工作线程负责）
sync-thread-num：slave端同步线程数
sync-buffer-size：slave端同步队列大小
log-path: 存放日志的目录
log-level：日志打印的级别，分别对应INFO和ERROR，推荐配置为INFO
db-path：数据库的目录
write-buffer-size: 底层存储引擎rocksdb的的memtable的大小限制
timeout：客户端（包括从节点客户端）与服务端的自动失效时间。在time_out时间内如果没有交互，则该客户端会被服务端摘除
requirepass：管理员客户端，主从节点通信的密码
userpass：普通客户端的密码
userblacklist: 需要管理员权限才能执行的命令，如shutdown,flushall, slaveof等
dump_prefix: dump出的数据库目录的命名前缀
daemonize：是否配置为守护进程
pidfile：用该文件保存守护进程的id
maxclients：服务器最大的连接数
target-file-size-base: 底层rocksdb的sst文件的大小
expire-logs-days: log日志的过期时间，以天为单位
expire-logs-nums: log日志的过期数量，若当前log的数量大于expire_logs_nums，则认为删除expire_logs_nums之前的log是安全的
root-connection-num：管理员权限客户端的数量限制
slave-read-only：配置服务器是否可写
db-sync-path：主从节点全同步时，用于传送主从数据库的存放路径，主从节点会分别对应为db_sync_path／master和db_sync_path/slave
db-sync-speed：master端全同步时传输文件的限速，最大125M
binlog-file-size: 单个binlog文件的大小限制，一般为100M;binlog文件存放的是改变数据库的命令纪录
compression：rocksdb构造sst文件时采用的压缩策略
max-background-flushes: rocksdb memtable flush线程数，默认是1，最大可配置到4
max-background-compactions: rocksdb compact线程数, 默认是1，最大可配置到4
max-cache-files : rocksdb tablecache的大小，默认是5000，也就是说默认缓存5000个sst关键信息，如果对内存要求特别严格，可以适当减少该值来节约内存，不过会影响效率，不推荐配置小，可配置大一些
~~~