## 名称：
pika_to_txt

## 位置：
[/pika-tools/pika_to_txt](https://github.com/Axlgrep/pika-tools/tree/master/pika_to_txt)

## 目的：
#### 离线迁移pika的kv数据到txt文本

## txt格式:
#### [key_length][key][value_length][value]
注意: 长度使用`uint32_t`

## 使用：
```
Usage:
        Pika_To_Txt reads kv data from Blackwidow DB and write to file
        -h    -- displays this help information and exits
        -b    -- the upper limit for each scan, default = 256
        example: ./pika_to_txt ./blackwidow_db ./data.txt

```

## 地址：
* 代码：[https://github.com/Axlgrep/pika-tools/tree/master/pika_to_txt](https://github.com/Axlgrep/pika-tools/tree/master/pika_to_txt)