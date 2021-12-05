### 基本概念

支持关于slot的操作详见 [slot commands](https://github.com/Qihoo360/pika/wiki/Pika分片命令)

<br/>

### pika 兼容codis 方案

1. 目前只支持3.2 版本的 [codis](https://github.com/CodisLabs/codis)，部署方式见[codis官方文档](https://github.com/CodisLabs/codis/blob/release3.2/doc/tutorial_zh.md)。

2. dashboard 的 migration_method 选项需要配置为 "semi-async" （默认配置）

3.1. 使用3.3.x版本的pika，由于多表功能的添加，跟sentinel不能完美兼容，建议使用[codis release](https://github.com/kernelai/codis/releases)，去掉了对sentinel的依赖。

3.2. 使用3.2.x版本的pika，由于pika不支持transaction 需要对codis做一些改动，详细见[提交](https://github.com/left2right/codis/commit/a295f5446a6661e84d20574c8a57455a4d439f48)

4. coids使用crc32算法，默认使用1024个slot，对应配置文件配置default-slot-num。
5. 我们在官方codis的release3.2的基础上针对pika sharding模式做一些功能扩展[github](https://github.com/kernelai/codis)，具体功能为：
* 增加了对表的支持，使得不同表的数据可以相互隔离。表内可以根据需求自定义slot数目，设置表的密码以及和名单。
* 对mget，mset的请求拆分后按照group而不是slot重新组装，性能提升明显。
* 开发了manager模块来替换哨兵组件，在master down掉之后按照lag最少来选master。

#### 1, 初始化拓扑

现有pika实例 pika1, pika3, pika2, pika4

pika1 和pika2 属于group1，pika2 是pika1 的全量备份，也就是pika2上面的slots全是pika1的slave， 监控发现pika1异常时可以让pika2执行 pkcluster slotsslaveof no one all，然后切到pika2 上

同理 pika3 和pika4 属于 group2。

打算分配0-511slots 给 group1， 分配512-1023 slots 给 group2

以group1为例，group2同理

1.1，在pika1，pika2上执行pkcluster addslots 0-511

1.2，pika2 上执行pkcluster slotsslaveof pika1_ip pika1_port 0-511

1.3，在codis分配相对应的slots给group1

<br/>

#### 2， 迁移

如果有新实例加入， 假设pika5 pika6 作为group3 加入集群。 打算迁移slot1 到pika5。

 pika5 需要预先按照1.1中所述(pkcluster addslots 1)建立slot 1,

未做分配前，原始拓扑 如下图


![before](https://whoiami.github.io/public/images/images/before.png)

2.1 在pika5上通过pkcluster slotsslaveof  pika1_ip  pika1_port  1 把slot1 同步到pika5上，等全同步结束进行下一步。

2.2 在pika6上通过pkcluster slotsslaveof pika5_ip pika5_port 1 把slot1 同步到pika6上 （保证slot1在新的group上有副本）保证2.1，2.2 的顺序，先建立pika5 和pika1 的同步关系，再建立pika6 和pika5的同步关系。因为pika5有可能会全同步，先建立pika5和pika6的关系会造成同步异常。

![middle1](https://whoiami.github.io/public/images/images/middle1.png)

2.3 在pika1 上用pkcluster info slot 1 观察pika5的lag很小的时候，修改codis meta信息，准备将slot1指向 group3(pika5)。


![middle2](https://whoiami.github.io/public/images/images/middle2.png)

2.4 观察pika1 上lag为0，pika5 做pkcluster slotsslaveof no one  1。2.3 迁移过程中codis会cache来自客户端的请求，此时客户端请求不会流向pika1，之后观察pika1上对pika5的同步lag为0，在pika5上之后执行pkcluster slotsslaveof no one 1，pika1会向codis告知slot1迁移完成，slot1的meta信息才会指向group3，codis会把这段时间缓存的数据重新发送到slot1新的归属group上也就是group3。 

![after](https://whoiami.github.io/public/images/images/after.png)

2.5 用pkcluster slotsslaveof no one 1, pkcluster delslots 1清理pika1 pika2 上的slot1 数据

<br/>

### Pika 兼容twemproxy 方案

#### 1，配置

twemproxy配置


     alpha:
         listen: 127.0.0.1:22121
         hash: crc32a
         distribution: modula
         auto_eject_hosts: true
         redis: true
         server_retry_timeout: 2000
         server_failure_limit: 1
         servers:
          - 127.0.0.1:9221:1 server1
          - 127.0.0.1:9221:1 server2
          - 127.0.0.1:9222:1 server3
          - 127.0.0.1:9222:1 server4
    

twemproxy 参数说明：

hash：crc32a

distribution: modula

twemproxy支持的hash算法非常多，crc32a&modula 是pika目前支持的哈希取模的数据分布方式。为了使twemproxy和pika 哈希取模结果一样需要上述twemproxy配置。

    servers:
          - 127.0.0.1:9221:1 server1
          - 127.0.0.1:9221:1 server2
          - 127.0.0.1:9222:1 server3
          - 127.0.0.1:9222:1 server4

twemproxy 根据具体的server数量进行哈希，例如server数量是4，哈希取模的结果如果是0，key落到server1上，对于server1 pika1来说对于key还要重新哈希取模，判断这个key是否属于现有的slots。如果pika哈希取模的结果也是0，并且pika1拥有slot 0，那么pika将对该key进行处理。



pika配置项

    instance-mode : sharding
    # default partition number each table in sharding mode
    default-slot-num : 4

#### 2，添加slots

在127.0.0.1:9221（pika1）上执行 pkcluster addslots 0-1

在127.0.0.1:9222（pika2）上执行 pkcluster addslots 2-3



#### 3, 关于迁移

可以用pkcluster slotsslaveof 对slot进行迁移