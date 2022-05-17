# redis

单线程+多路IO复用



## Redis键(key)常用操作

```
keys *		# 查看所有key
exists key		# 判断key是否存在
type key			# 查看key的类型
del key				# 删除指定key的数据
unlink key 		# 根据value选择非租塞删除，仅将keys冲keyspace元数据中删除，真正的删除会在后续异步操作
expire key 10		# 给指定的key设置过期时间为10s
ttl key 				# 查看key还有多少秒过去，-1：用不过期，-2:已过期

select 					# 命令切换数据库
dbsize					# 查看当前数据库key的数量
flushdb					# 清空当前库
flushall				# 通杀全部库
```

## Value常用五大数据类型

* 字符串（String）
* 列表（List）
* 集合（Set）
* 哈希（Hash）
* 有序集合（Zset）

### 字符串（String）

####简介

string是redis最基本的类型，你可以理解成与Memcache一摸一样的类型，一个key对应一个value

string类型是**二进制安全的**。意味着redis的string可以包含任何数据。比如jpg图片或序列化对象

string类型是redis最基本的数据类型，一个redis中字符串values最多可以是512M

#### 常用命令

```
set <key> <value> 	# 添加键值对
set key value [EX seconds|PX millisconds|KEEPTTL] [NX|XX]
get <key>							# 取得key的值
append <key> <value>	# 将给定的value追加到原值的末尾
strlen <key>					# 获得值的长度
setnx <key> <value>		# 只有在key不存在时，设置key的值

incr <key>		# 将key中存储的数字值增1，只有对数字值操作，如果为空，新增为1
decr <key>		# 将key中存储的数字值减1，只有对数字值操作，如果为空，新增为-1
incrby/decrby <key> <步长>	# 将key中存储的值加减步长

mset <key1><value1> <key2><value2> ... # 同时设置一个或多个k-v对
mget <key1><key2>... # 同时获取一个或多个v
msetnx <key1><value1> <key2><value2> ... # 同时设置一个或多个k-v对,只有在key不存在时
# 原子性，有一个失败，都失败

getrange <key> <起始位置><结束位置>		# 获取值的范围 包头包尾
#Localhost:0>getrange k2 0 1
#"v2"
#Localhost:0>get k2
#"v200"
#Localhost:0>
setrange <key><起始位置><value>			# 在指定的范围中，设置值

setex <key><过期时间><value>	# 设置键值的同时，设置过期时间，单位秒 # setex k2 10 v2
getset <key> <value>				# 以旧换新 设置新的同时，获取旧值
```

原子性

不会被线程调度机制打断的操作

 `incr <key>`时，具有原子性

#### 数据结构

string的数据结构为简单动态字符串(Simple Dynamic String，简写SDS)，是可以修改的字符串，内部结构类似java中的ArrayList，采用预分配冗余空间的方式来减少内存的频繁分配

<img src="./images/1.jpg">

内部预分配的空间capacity一般要高于实际字符串长度len，当字符串长度<1M时，扩容都是加倍现有空间，当超过1M时，一次只会多扩1M空间，不一定是2倍。最大512M



### 列表（List）

#### 简介

单键多值

redis列表是简单的字符串列表，按照插入顺序排序。可以添加一个元素到列头或列尾

它的底层实际是个双向链表，对两端的操作性能很高，通过索引下标的操作中间的节点性能会比较差

<img src='./images/2.png'>

#### 常用命令

```
lpush/rpush <key><value1><value2><value3>... # 从左边/右边插入一个或多个值
lpop/rpop <key>		# 从左边/右边吐出一个值。值在键在，值光键亡

rpoplpush <key1><key2> 从<key1>列表右边吐出一个值，插入到<key2>列表左边

lrange <key><start><stop>		# 按照索引下标获取元素(从左到右) 0：左第一个 -1:右第一个 -2:右第二...

lindex <key><index>		# 按照索引下标获取元素
llen <key>		# 获得列表长度

linsert <key> before/after <value><newvalue>	#在<value>前或后插入<newvalue>
lrem <key><n><value> 		# 从左边删除n个value
lset <key><index><value> 	# 将列表key下标为index的值替换成value
```



#### 数据结构

list的底层数据结构是一个快速链表 quickList

当元素比较少时，会分配一块**连续的内存**空间，这个结构是ziplist，即压缩列表，将所有元素紧挨在一起存储

当元素多时，把多个ziplist连接起来，变成quicklist

<img src='./images/3.png'>

链表需要额外存储指针，所以占用空间较大，比如双向链表需要两个指针

redis的quicklist，既满足快速增删，又不会有太多冗余



### 集合（Set）

#### 简介

无序不可重复的string元素

#### 常用命令

```
sadd <key><value1><value2> ... # 添加元素到集合
smembers <key>			# 取出集合所有元素
sismember <key><value> 	# 判断集合<key>是否含有<value>
scard <key>		# 返回集合元素个数
srem <key> <value1><value2>...  # 删除集合中的某个元素
spop <key>		# 随机从该集合中吐出并删除一个值
srandmember <key><n> # 随机从该集合中取出n个值，不会从集合中删除
smove <k1><k2> value # 把集合中的一个值，从k1移动到k2
sinter <k1><k2> 	# 返回两个集合的交集
sunion <k1><k2> 	# 返回两个集合的并集
sdiff <k1><k2> 		# 返回两个集合的差集，在k1不在k2

```

#### 数据结构

set数据结构是dict字典，字典是哈希表实现的



### 哈希（Hash）

#### 简介

redis hash是一个键值对集合，类似java中的map

redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象，如key=user1，value={id=1,name=tom,age=20}，Map<Sting,Object>

#### 常用命令

```
hset <key><field><value> 	# 给指定key集合中的field键赋值value
hget <key><field>				# 获取key中field的值
hmset <key><field1><value1><field2><value2>...		# 批量设置
hexists <key><field1> # 查看key中field1是否存在
hkeys <key>						# 列出key中的所有field
hvals <key>						# 列出key中的所有value

hincrby <key><field><increment>		# 为key中的field的值，增加increment
hsetnx <key><field><value>				# 给指定key集合中的field键赋值value,仅当field不存在时
```

#### 数据结构

当数据少时，用ziplist，当数据多时，用hashTable



### 有序集合（Zset）sorted set

#### 简介

有序不可重复的集合，序列是按每个元素所关联的分数进行排序

#### 常用命令

```
zadd <key><score1><value1><score2><value2>... 	# 将一个或多个member元素及score值加入到有序key中
zrange <key><start><end> [withscores]					# 取出 key中角标从start到end的值，withscores带上分数，得分从小到大排序
zrangebyscore k1 100 350 withscores			# 取出k1中 得分在[100,350]之间的value，withscores带上分数，得分从小到大排序
zrevrangebyscore k1 350 100 withscores		# 取出k1中 得分在[100,350]之间的value，withscores带上分数，得分从大到小排序


zincrby <key><increment><value>			# 为key中元素值为value的得分加上increment
zrem <key><value>										# 删除key下的value
zcount <key><min><max>							# 统计该集合，分数区间[min,max]内的元素个数
zrank <key><value>									# 返回该值在集合中的排名，从0开始
```

#### 数据结构

存储方式类似hash方式，一个key，value中是多个键值对，但是排序会根据得分

采用跳跃表，能更高效的查找数据，但具体如何跳跃？



## 新数据类型

### BitMaps(位map)

#### 简介

1. 其实本身不是一种数据类型，实际上就只是string，但可以对**位**进行操作
2. bitMaps单独提供一套命令，和字符串不太相同，可以把BitMaps想象成一个以位为单位的数组，数组的每个单元只能存储0或1，数组的下标在BitMaps中叫偏移量

#### 命令

```
setbit <key><offset><value>		# 设置一个键为key，值为value，偏移量为offset的bitMaps
setbit k1 0 1		# 设置k1位列表的第0位为1
setbit k1 1 1
setbit k1 6 1
setbit k1 11 1
setbit k1 15 1
setbit k1 19 1
```

每个独立用户是否访问过网站存放在Bitmaps中， 将访问的用户记做1， 没有访问的用户记做0， 用偏移量作为用户的id

设置键的第offset个位的值（从0算起） ， 假设现在有20个用户，userid=1， 6， 11， 15， 19的用户对网站进行了访问， 那么当前Bitmaps初始化结果如图

<img src='./images/4.png'>

```
getbit k1 0			# 获取k1列表的第0位

bitcount <key><start><end>		# 统计key列表中从[start,end]个字节(8位)共有多少是1

setbit操作的是位bit
bitcount统计的是字节byte(8bit)

bitop and/or/not/xor <destkey> [k1,k2,...]
# bitop是一个复合操作，将k1、k2等多个位map的交集(and)/并集(or)/非(or)/异或(xor)的结果保存到destkey中

setbit k2 1 1
setbit k2 2 1
setbit k2 5 1
setbit k2 9 1

setbit k3 0 1
setbit k3 1 1
setbit k3 4 1
setbit k3 9 1

bitop and k23 k2 k3	# 去k2和k3的并集放入k23
```

#### bitMaps对比set

假设网站有1亿用户， 每天独立访问的用户有5000万， 如果每天用集合类型和Bitmaps分别存储活跃用户可以得到表

| 数据类型 | 每个用户id占用空间 | 需要存储的用户量 | 全部内存量                                 |
| -------- | ------------------ | ---------------- | ------------------------------------------ |
| set      | 64位(8字节)        | 50 000 000       | 64bit(8byte)*50 000 000=400,000,000B=400M  |
| BitMaps  | 1位                | 100 000 000      | 1((1/8)byte)*100 000 000=12 500 000B=12.5M |

很明显， 这种情况下使用Bitmaps能节省很多的内存空间， 尤其是随着时间推移节省的内存还是非常可观的

| 数据类型 | 一天  | 一个月 | 一年 |
| -------- | ----- | ------ | ---- |
| set      | 400M  | 12G    | 144G |
| BitMaps  | 12.5M | 375M   | 4.5G |

但如果每天访问的用户数很少，例如只有10万个，这时bitmaps就不太合适，因为大部分都是0，尔set只需存储相应的活跃用户数

| 数据类型 | 每个用户id占用空间 | 需要存储的用户量 | 全部内存量                                 |
| -------- | ------------------ | ---------------- | ------------------------------------------ |
| set      | 64位(8字节)        | 100 000          | 64bit(8byte)*100 000=800 000B=800k         |
| BitMaps  | 1位                | 100 000 000      | 1((1/8)byte)*100 000 000=12 500 000B=12.5M |



### HyperLogLog

#### 简介

什么是基数?

比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数

为了统计不重复数据，解决基数问题，有两种方案，1）mysql中进行distinct count；2）在redis中使用hash、set、bitmaps等解决，数据精准但空间越来越大

所以退出HyperLogLog，降低精度平衡空间

HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的、并且是很小的

HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素

#### 命令

```
pfadd <key><element>		# 返回key 中，基数是否发生变化 1:变 0:不变
pfadd k1 a	# 1
pfadd k1 b	# 1
pfadd k1 a	# 0

pfcount <key>		# 计算key的基数
pfadd k1	# 2

pfmerge <destkey> <sourcekey1> <sourcekey2># 将sourcekey1和sourcekey2得基数汇聚到destkey
pfmerge k1 k2 k3
```



### GeoSpatial

#### 简介

Geographic，地理信息的缩写。地理信息的缩写。该类型，就是元素的2维坐标，在地图上就是经纬度。redis基于该类型，提供了经纬度设置，查询，范围查询，距离查询，经纬度Hash等常见操作

两极无法直接添加，一般会下载城市数据，直接通过 Java 程序一次性导入

有效的经度从 -180 度到 180 度。有效的纬度从 -85.05112878 度到 85.05112878 度。

当坐标位置超出指定范围时，该命令将会返回一个错误。

已经添加的数据，是无法再次往里面添加的。

#### 命令

```
geoadd <key> <longitude><latitude><member>[longitude latitude member...]	# 添加地理位置（经度、纬度、名称）
geoadd k1 106.63 26.64 guiyang 114.05 22.52 shenzhen

geopos  <key><member> [member...]  # 获得指定地区的坐标值
geopos k1 guiyang

geodist<key><member1><member2>  [m|km|ft|mi ]  # 获取两个位置之间的直线距离
geodist k1 guiyang shenzhen	# 默认单位米

georadius<key><longitude><latitude>radius  m|km|ft|mi   #以给定的经纬度为中心，找出某一半径内的元素 精度 维度 距离 单位
georadius k1 106.63 26.64 1000 km
```



## Redis的发布和订阅

* 什么是发布和订阅

  redis发布订阅（pub/sub）是一种消息通信模式：发送者（pub）发送消息，订阅者（sub）接收消息

  redis客户端可以订阅任意数量的频道

* 执行

  ```
  # 打开一个命令行客户端
  subcribe channel1		# 订阅频道channel1
  
  # 代开另一个客户端
  publish channel1 aaa	# 向频道channel1推送消息aaa
  ```
  
## Redis 事务
Redis事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
Redis事务的主要作用就是串联多个命令防止别的命令插队

### Multi、Exec、discard
* Multi 开始组队 类似开启事务
* Exec 执行 类似提交
* discard 撤销组队 类似回滚
<img src='./images/5.png'>

1. 当组队时发生错误，执行时，整改队列都失败
2. 当执行提交时发生错误，谁错谁失败，谁对谁成功

### 事务冲突
<img src='./images/6.png'>

解决事务冲突：
1. 悲观锁：悲观的认为冲突一定会产生，每次操作前都加锁，操作后放锁，效率较低
2. 乐观锁：乐观的认为冲突不一定会产生，操作时，添加版本字段，来判断是否需要重写读取