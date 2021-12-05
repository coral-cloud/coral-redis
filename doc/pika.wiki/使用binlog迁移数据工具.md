## 名称：
binlog_sender

## 位置：
[/pika-tools/binlog_sender](https://github.com/Axlgrep/pika-tools/tree/master/binlog_sender)

## 目的：
将binlog中的命令解析出来并且发送到Pika/Redis

## 背景：
Pika3.0对binlog的格式进行了重新的设计，为了支持全新的binlog，我们提供了这个工具方便用户使用binlog迁移数据到Pika/Redis, 既可以指定迁移某个时间区间内的数据，也支持从一个binlog的某个偏移量开始迁移数据

## 实现：
### BinlogConsumer
1. 在write2file中顺序的获取binlog条目

### PikaBinlogTransverter
1. 对收到的binlog进行解析， 获取binlog中存有的redis命令

### Redis_Client
1. 将获取到redis命令发送给Pika/Redis


## 使用：
```
Usage:
        Binlog_sender reads from pika's binlog and send to pika/redis server
        You can build a new pika back to any timepoint with this tool
        -h    -- displays this help information and exits
        -n    -- input binlog path
        -i    -- ip of the pika server
        -p    -- port of the pika server
        -f    -- files to send, default = 0
        -o    -- the offset that the first file starts sending
        -s    -- start time , default: '2001-00-00 00:59:01'
        -e    -- end time , default: '2100-01-30 24:00:01'
        -a    -- password of the pika server
        example1: ./binlog_sender -n ./log -i 127.0.0.1 -p 9221 -f 526 -o 8749409
        example2: ./binlog_sender -n ./log -i 127.0.0.1 -p 9221 -f 526-530 -s '2001-10-11 11:11:11' -e '2020-12-11 11:11:11'
```

## 地址：
* 代码：[https://github.com/Axlgrep/pika-tools/tree/master/binlog_sender](https://github.com/Axlgrep/pika-tools/tree/master/binlog_sender)
