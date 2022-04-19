# mysql索引

1. B树和B+树之间的区别是什么？
2. Innodb中的B+树有什么特点？
3. 什么是Innodb中的page？
4. Innodb中B+树是怎么产生的？
5. 什么事聚簇索引？
6. Innodb是如何支持范围查找能走索引的？
7. 什么事联合索引？对应的B+树是如何生成的
8. 什么事最左侧缀原理？
9. 为什么要遵守最左侧前缀原则才能利用到索引？
10. 什么事索引条件下推？
11. 什么事覆盖索引？
12. 有哪些情况会导致索引失效？

## 0、索引数据结构

* 二叉树：每个节点最多只有两个分支的有序树结构
  * 二叉查找树：本质是一种二叉树，但每个节点左子树的所有节点值均小于它的跟节点的二叉树
* 红黑树（平衡二叉树）：叶节点高度差的绝对值不超过1，每个节点都 **带有颜色** 属性的 **二叉查找树**
* B树：一个节点可以有多个子节点的红黑树
* Hash索引
  * 对索引的key进行一次hash计算就可以定位出数据存储的位置
  * 很多时候Hash索引要比B+树索引更高效
  * 仅能满足“=”，“IN”，不支持范围查询
  * hash冲突问题

## 1、B树和B+树之间的区别是什么？

* B树叶子节点之间没有相互维护的指针

* B+树叶子节点有冗余数据，其实冗余并不多，但可以大大提升效率

## 2、Innodb中的B+树有什么特点？

。。。





```mysql
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

# 
select * from t1 where a=7; # 普通情况需要7次磁盘io
# InnoDB page 
show global status like 'innodb_page_size'; #1
```



InnoDB page

