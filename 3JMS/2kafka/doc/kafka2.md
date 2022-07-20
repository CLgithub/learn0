# kafka学习2
## 架构
<img src='./images/12.png'>

## 搭建
kafka 2.8几以后版本，可不依赖zk
### zk
* zoo.conf文件中
    ```
    # 自定义数据目录
    dataDir=
    # 添加
    server.1=vUbuntu1:2888:3888
    server.2=vUbuntu2:2888:3888
    server.3=vUbuntu3:2888:3888
    ```
* data目录下添加各种的`myid`

### kafka
* server.properties文件配置
    ```
    # 不同的broker.id
    beoker.id=1/2/3
    ...
    # 自定义数据存放目录
    log.dirs=/home/l/develop/kafka_2.13-3.2.0/data
    ...
    # zk连接 
    zookeeper.connect=vUbuntu1:2181,vUbuntu2:2181,vUbuntu3:2181/kafka 
    # 最后加上/kafka可使kafka数据在zk节点中均在[/kafka]节点下
    ```


## 使用
### 命令
bin目录下，各部件可通过对应的命令去操作
* topic命令`kafka-topics.sh`

    <table width="100%" >
    <th>参数</th><th>描述</th>
    <tbody>
    <tr><td>--bootstrap-server</td>
    <td>连接的kafka broker主机地址与端口</td></tr>
    <tr><td>--topic</td><td>要操作的topic名称</td></tr>
    <tr><td>--create</td><td>创建</td></tr>
    <tr><td>--delete</td><td>删除</td></tr>
    <tr><td>--alter</td><td>修改</td></tr>
    <tr><td>--list</td><td>查看所有</td></tr>
    <tr><td>--describe</td><td>查看主题详细</td></tr>
    <tr><td>--partitions</td><td>设置分区</td></tr>
    <tr><td>--replication-factor</td><td>设置分区副本</td></tr>
    <tr><td>--config</td><td>更新系统默认的配置</td></tr>
    </tbody>
    </table>
如:
```
# 查看topic列表
./kafka-topics.sh --bootstrap-server vUbuntu1:9092 --list
# 创建topicB 2分区 2副本
./kafka-topics.sh --bootstrap-server vUbuntu1:9092 --create --topic topicB --partitions 2 --replication-factor 2
# 查看topicB详细
./kafka-topics.sh --bootstrap-server vUbuntu1:9092 --topic topicB --describe
# 修改topicA为3分区
./kafka-topics.sh --bootstrap-server vUbuntu1:9092 --topic topicA --alter --partitions 3
```
* 生产者命令`./kafka-console-producer.sh`

## kafka-生产者
### 生产者写入数据过程
<img src='./images/25.png'>

### 生产者分区策略
```
producer.send(new ProducerRecord<>(topic, value), new Callback() {...}
    
# 使用默认分区规则
```
* 默认分区器`DefaultPartitioner`分区规则：
    * 如果指定分区，就使用指定的分区
    * 如果没有指定分区，但是有key，就使用key的hash值，对分区数取余，得到分区编号
    * 如果没有指定分区，也没有key，kafka采用sticky Partition(黏性分区器)，会随机选择一个分区，并尽可能的一直使用该分区，直到该分区的batch已满(16k)或时间到，kafka再随机一个分区进行使用（和上一次不同）
        
```
### 指定分区情况
public ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers) {
    ...
}
public ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value) {
    ...
}
public ProducerRecord(String topic, Integer partition, K key, V value, Iterable<Header> headers) {
    ...
}
public ProducerRecord(String topic, Integer partition, K key, V value) {
    ...
}
### 未指定分区，但有key情况
public ProducerRecord(String topic, K key, V value) {
    ...
}
### 既没有指定分区，也没有key情况
public ProducerRecord(String topic, V value) {
    ...
}
```
    
### 生产者如何提高kafka吞吐量
1. 总缓存区大小 RecordAccumulator
2. 批次大小 batch.size
3. 轮询时间 linger.ms
4. 压缩数据 compression.type
👻：是否可在配置文件中配置，也可在代码中配置

### 生产者数据可靠性
* 数据传递语音
    * 至少一次（At Least Once）= ACK级别设置为-1 `&&` 分区副本数大于等于2 `&&` ISR里应答的最小副本数大于等于2
    * 最多一次（At Most Once）= ACK级别设置为0
    * 总结
        * 至少一次：可以保证数据不丢失，但不能保证数据不重复
        * 最多一次：可以保证数据不重复，但不能保证数据不丢失
    * 精确一次（Exactly Once）：对于一些非常重要的信息，要求数据即不能重复也不能丢失
        * Kafka 0.11 版本后，引入了一个重大特性：幂等性和事务

#### 幂等性
* 幂等性：指Producer不论向Broker发送多少次重复数据，Broker端都只会持久化一条，保证了不重复
* 精确一次（Exactly Once）= 幂等性(不重复) `&&` 至少一次(ack=-1`&&`分区副本数>=2`&&`ISR最小副本数量>=2)
* 如何判断是否重复：具有<ProducerID,Partition,SeqNumber>相同主键的消息提交时，Broker只会持久化一条。
    * PID：kafka每次重启都会分配一个新的
    * Partition：分区号
    * Sequence Number：序列化号，是单调自增的

所以幂等性只能保证的是在 **单分区** **单会话**(每次启动)内不重复

* 如何开启幂等性
    * ` map.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);`(enable.idempotence默认为true）

#### 生产者事务
开启事务必须开启幂等性
* 生产者事务
    * 每个broker上都有一个 事务协调器（Transaction Coordinator）
    * kafka中，有一个存储事务的特殊主题`__transaction_state-分区-Leader`，默认50个分区
    * 生产者在使用事务前，必须先自定义一个唯一的事务id，有了事务id，即使客户端挂掉，重启后也能继续处理未完成的事务
    * 一条数据过来，先看是哪个分区，即用该分区的leader节点的 事务协调器 来处理该条数据 
    * 事务id的hashcode值%50，计算出该事务存在事务主题的哪个分区
    <img src='./images/26.png'>