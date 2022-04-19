学习目标：

1. 索引数据结构红黑树，hash，B+树详解

2. 千万级数据如何用索引快速查找

3. 如何基于索引B+树精准建立高性能索引

4. 联合索引底层数据结构又是怎样的

5. 聚集索引与覆盖索引与索引下推到底是什么

6. Mysql最左前缀优化原则是怎么回事

7. 为什么推荐使用自增整型的主键而不是UUID

8. Mysql并发支撑底层BufferPool机制详解

9. 阿里巴巴Mysql索引优化军规

   

# 索引基本结构

   MySQL索引时帮助MySQL高效获取数据的 **排好序** 的 **数据结构**

   * 索引数据结构，每个节点<k,v> <索引,数据磁盘文件地址>

     * 二叉树：每个节点最多只有两个分支的有序树结构
       * 二叉查找树：本质是一种二叉树，但每个节点左子树的所有节点值均小于它的跟节点的二叉树
     * 红黑树（平衡二叉树）：叶节点高度差的绝对值不超过1，每个节点都 **带有颜色** 属性的 **二叉查找树**，
     * Hash表
     * B-Tree：一个节点可以有多个子节点的红黑树

     ```mysql
     # 以二叉树为例
     select * from t where t.col2=89; # 从根节点开始查找，两次就能查到
     # 但Mysql并不以二叉树为索引，因为存在问题如：
     select * from t where t.col1=6;	# col1放在二叉树里类似链表
     ```

     <img src=./images/1.jpg >

     ```mysql
     # 红黑树为例 只需要查找3次
     select * from t where t.col1=6; # 早期间mysql使用平衡二叉树，现在已经不使用
     # 红黑树的问题在于数量量大时，树 的高度较高
     ```

      <img src='./images/2.png'>

     为了改进红黑树树高较高的问题，可以在一个节点里 横向 按序 多放几个元素，就形成了[B Tree](https://www.cs.usfca.edu/~galles/visualization/BTree.html)，度为4，相当于4进制，逢4分裂，每个节点最多放3个元素，每个节点最多有4个分支

     <img src='./images/3.png'>

     对B Tree再进行改进，得到[B+Tree](https://www.cs.usfca.edu/~galles/visualization/BPlusTree.html)

     <img src="./images/4.png">

     B树和B+树区别：

     * B树叶子节点之间没有相互维护的指针
     * B+树叶子节点有冗余数据，其实冗余并不多，但可以大大提升效率



# MySQL索引

MySQL索引采用 B+树结构，有以下特点
* 排好序

* 一个节点(page)有多个元素

* 叶子节点用指针连接，提高区间访问的性能

* 非叶子节点不存储data，只存储索引(冗余)，是索引页，起到分组的作用，方便叶子节点长链表的快速查找

* 叶子节点包含所有索引以及数据地址，是数据页

<img src="./images/5.png">
         
```mysql
     show global status like 'innodb_page_size'; # innoDB页大小
     # 16384=1024*16
     # 每个大节点大小为16k Byte
     # mysql中int和java中int占位一样（32位4字节），bigint相当于java中的long（64位8字节）
     # 若用bigint类型的字段做索引 每个跟节点可防止16384/(8+6)=1170.2857142857个索引 
     # 若树高为h，每个叶子节点放索引和数据需要1kB，
     # 则共能放 16*(1170)^(h-1)，若h=3，=21902400
     
     create table t1(
     a int primary key,
     b int,
     c int,
     d int,
     e varchar(20)
     )engine=InnoDB
     
     # 显示t1表的索引
     show index from t1; # 目前有一个索引
     
     # 乱序逐行插入数据
     INSERT into t1 values(4,3,1,1,'d');
     INSERT into t1 values(1,1,1,1,'a');
     INSERT into t1 values(8,8,8,8,'h');
     INSERT into t1 values(2,2,2,2,'b');
     INSERT into t1 values(5,2,3,5,'e');
     INSERT into t1 values(3,3,2,2,'c');
     INSERT into t1 values(7,4,5,5,'g');
     INSERT into t1 values(6,6,4,4,'f');
     
     select * from t1; # 插入的时候就已经排序
     
     select * from t1 where a=7; # 不加索引需要7次磁盘io，加了B+树索引，只需要1次磁盘io，因为innoDB_page_size为16kB，直接就在根大节点内
```



InnoDBpage         

* InnoDB页内部结构       
  <img src="./images/10.png">
       

在插入的时候就会按主键进行逻辑排序，但会页内部会形成链表（插入快，查询慢）

利用「页目录」提升查询速度，对数据进行分组，页目录中存储每一组首数据主键，查询时先利用二分查找确定在哪组

<img src="./images/11.png">

<img src="./images/12.png">
       

Explain关键字：使用explain关键字可以模拟优化器执行sql语句，分析你的查询语句或是结构的性能评价。在select语句之前添加explain关键字，Mysql会在查询上设置一个标记，执行查询会返回执行计划的信息，而不是执行这条sql，注意：如果from中包含子查询，仍会执行孩子查询，将结果放入临时表中

* 走索引：从根页往下查找
* 不走索引：全表扫描，从叶子节点最左侧逐个查找
  
```mysql
explain select * from t1 where a=5; # 走索引 
explain select * from t1 where a>7; # 走索引
explain select * from t1 where c=7; # 不走索引，全表扫描
```



# 联合索引

```mysql
create index idx_t1_bcd on t1(b,c,d);	# B+
```
<img src="./images/13.png">

每创建一个索引，就生成一颗B+树，会带来两个问题：

* 数据冗余
* 更新麻烦

所有考虑只冗余bcd三个字段

<img src="./images/14.png">

```mysql
select * from t1 where b=1 and c=1 and c=1; # 走索引 但是只能查出bcd字段，*字段不能查出
```

所有在叶子节点上再记录这一行的主键

<img src="images/15.png">

这样并不完全冗余，只冗余必要的信息，索引字段和主键字段，查找的时候先通过索引找到主键，再通过主键索引树去查找*字段（会表）

<img src="./images/16.png">

## 最左前缀原则

存储方式按字段逐个比较

```mysql
explain select * from t1 where b=1; # 走索引，相当于用1** 去bcd索引树中查找
explain select * from t1 where b>1; # 不走bcd索引，走全表扫描，因为走全表扫描更快（走索引只能定位1**，后面的需要7次回表，不如全表扫描）
explain select * from t1 where b>6; # 走索引（只需要一次回表）
explain select b from t1 where b>1; # 走索引（直接得出）
explain select b,c,d from t1 where b>1; # 走索引（直接得出）
explain select b,c,d,a from t1 where b>1; # 走索引（a是主键，也可以直接得出）

explain select b from t1;	# 我认为不走，因为不限条件，所以直接全表扫描

# 若加上
create index idx_t1_e on t1(e);

explain select * from t1 where e=1; 	# 走索引
explain select * from t1 where e='1'; #
explain select * from t1 where a=1; 	# 走索引
explain select * from t1 where a='1';	# 
```

联合索引优化原则：最左前缀原则

* where后的条件安联合索引字段来查询，不能跳过，若跳过了之后后面的字段就不会走
* 该原则原因：看联合存储方式，联合索引字段逐个比较后存储





#MyISAM 与InnoDB 存储引擎

MyISAM 和 InnoDB 分别是mysql的两种存储引擎，存储引擎作用与表，MyISAM已经很少用了，两者都用B+树结构索引，区别在于，MyISAM 的叶子节点放索引地址，InnoDB 的叶子节点放的是整行的数据（同时放数据和索引）

 * MyISAM 有3个文件，.frm放表结构，.MYD放数据，.MYI放索引，目前MyISAM已经很少用了

 * InnoDB 两个文件，.frm放表结构，.idb放数据和索引

    * 表数据文件本身就是按B+树组织的一个索引结构文件
    * 聚集索引：叶子节点包含了完整的数据记录
    * 为什么建议InnoDB表建主键，并且推荐使用整型自增主键？（若不建主键，mysql会自己去找一列，或自己去维护一列当作索引，会消耗数据库资源。整型方便比对，自增可以每次都插入到最后，可能会导致树结构平凡变动，消耗资源。所以不要用UUID做主键，非整型长而非自增）
    * 主键索引存储方式，叶子节点放索引和整行数据

    <img src="./images/6.jpg">

   * 非主键索引存储方式，叶子节点放该列数据和该行主键。为什么？（一致性和节省存储空间）

     <img src="./images/7.png">





## Hash索引

Mysql索引还可以是hash索引

* 对索引的key进行一次hash计算就可以定位出数据存储的位置
* 很多时候Hash索引要比B+树索引更高效
* 仅能满足“=”，“IN”，不支持范围查询
* hash冲突问题

<img src="./images/8.png">

