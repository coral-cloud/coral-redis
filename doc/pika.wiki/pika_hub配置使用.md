## pika_hub 地址
[传送门](https://github.com/KernelMaker/pika_hub)

## pika 配置

server-id：需要互相同步的pika实例，每个实例配唯一一个server id，比如1，2，3...



## pika_hub配置

floyd-servers : raft集群所有节点的ip:port信息，节点见以逗号分隔

​                          例：XX1.XX1.XX1.XX1:9211,XX2.XX2.XX2.XX2:9211,XX3.XX3.XX3.XX3:9211

floyd-local-ip : raft集群对应本节点的ip信息

​                          例：XX1.XX1.XX1.XX1

floyd-local-port : raft集群对应本节点的port信息

​                          例：9211

floyd-path : raft集群对应本节点的工作目录

​			  例：./floyd

---

__sdk-port__ : pika_hub管理端口（端口号必须与上面配置的floyd端口不一样）

​                          例：6868

__conf-path__ : pika_hub配置文件路径

​                          例：./conf/pika_hub.conf

__log-path__ : pika_hub的log路径

​			  例：./log

---

__max-log-file-size__ : pika_hub的log可按大小自动切分，单位字节

__log-file-time-to-roll__ : pika_hub的log可按时间自动切分，单位秒

__info-log-level__ : pika_hub的log级别，1->Info; 2->Warn; 3->Error; 4->Fatal

---

__pika-servers__ : 所有需要进行跨机房同步的pika实例的ip:port:serverid:password信息，以逗号分隔             

​			   例：XX1.XX1.XX1.XX1:9221:1:abc,XX2.XX2.XX2.XX2:9221:2:def,XX3.XX3.XX3,XX3:9221:3:ghi

__daemonize__: 以daemon形式运行

​			   例：yes

__pidfile__: 当以daemon形式运行是，配置生成pid文件的路径及文件名

​			    例：./pika_hub.pid

__binlog-offset-absolute-consistency__: 当pika_hub发送给pika的同步偏移量在pika找不到时，如果此项配置yes，则pika_hub直接报错并放弃重连，如果配置no，则pika会使用当前存在binlog的合适偏移量开始同步【故障兼容性更好】

__requirepass__: pika_hub管理端口密码

## 启动

配置好pika和pika_hub后，启动他们即可，稍作等待，互相同步就会建立



## 管理命令

可以使用redis-cli连上任意一个pika_hub实例操作如下命令
1. info：查看状态信息，注：pika_hub的primary节点和second节点执行info命令后显示结果有不同
2. ping
3. auth：密码验证
4. transfer: 见下
5. copy：见下




## 故障处理

### 1. pika_hub故障

如果pika_hub集群中挂掉少于一半节点数据节点，pika_hub会自动恢复服务，当多余一半节点故障，则pika_hub停止服务

### 2. pika故障

跨机房同步的pika实例可在其后挂若干slave，当master实例挂掉并不可恢复时，首先将其slave实例执行slaveof no one断开与故障实例的主从连接，然后确认slave和master实例配置使用相同的server_id，接着在pika_hub端，按照先primary节点后second节点的顺序[注：推荐顺序，不强制要求]，依次执行

transfer server_id new_pika_ip new_pika_port

例：transfer 1 XX1.XX1.XX1.XX1 9221

执行后，pika_hub的primary节点会将对将server_id 1的pika转移XX1.XX1.XX1.XX1:9221上

__注__: transfer命令只是在线转移pika故障实例，并没有持久化到配置文件的pika-servers中，执行后，需要人工修改所有pika_hub的配置文件来确保配置持久化



## 新增pika实例

pika_hub支持新增pika实例同步，具体操作步骤如下：

假设pika_hub之前已经于A，B，C三个pika实例建立同步关系，此时需要新增D实例

1. 将D作为A，B，C其中一个的slave

2. 确认D与其master同步点十分接近时在pika_hub端，按照先primary节点后second节点的顺序[注：推荐顺序，不强制要求]，依次执行

   copy exist_pika_server_id  new_pika_server_id new_pika_ip new_pika_port

   例：copy 1 2 XX2.XX2.XX2.XX2 9221

   执行后， XX2.XX2.XX2.XX2:9221以server_id 2的身份加入pika_hub同步

__注__: copy命令只是在线添加pika实例，并没有持久化到配置文件的pika-servers中，执行后，需要人工修改所有pika_hub的配置文件来确保配置持久化