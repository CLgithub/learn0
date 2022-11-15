* 常用命令
    * 通用命令
        ```
        db
        	选择数据库
        		select 0
        	查看当前库所有key
        		keys *
        	查看当前库的key的数量
        		dbsize
        	清空当前库
        		flushdb
        	通杀全部库
        		flushall
        key
        	判断key是否存在
        		exests <key>
        	判断key类型
        		type <key>
        	删除key以及其数据
        		del <key>
        	异步删除key
        		unlink <key>
        	为给定的key设置过期时间
        		expire key 10
        	查看key剩余时间 -2过期 -1永不过期
        		ttl <key> 
        ```
    
    * String
        ```
        增
            增加键值对
                set <key> <value>
            增加键值对 带过期时间
                set <key> <value> ex 10
                setex <key> 10 <value>
            只有在key不存在时 设置key的值
                setnx <key> <value>
            批量设置/获取
                mset/mget <key1> <value1> <key2> <value2> ...
                msetnx k1 v1 k2 v2 ...当且仅当所有给定key都不存在 才能设置成功原子性
            setex设置过期时间/setnx当key不存在时才能设置
        删
            del <key>
            unlink <key>
        改
            在原有值末尾添加
                append <key> v
            将key中储存的数字值增/减1
                incr/decr <key>
            将key中储存的数字值增/减自定义步长
                incrby/decrby <key>
            以新换旧，设置了新值同时获得旧值
                oldValue = getset <key> <newValue>
        查
            获取
                get <key>
            获得值的长度
                strlen <key>
        ```
    * List
        ```
        增
            从左边/右边插入一个或多个值
                lpush/rpush <k> <v1> <v2> <v3> ...
            在<v1>之前插入<v2>
                linsert <k> before <v1> <v2>
        删
            从左边/右边吐出一个值，值在键在 值光键亡
                lpop/rpop <k>
            从左删除n个v
                lrem <k> <n> <v>
        改
            从<key1>列表右边吐出一个值，插到<key2>列表左边
                rpoplpush <key1> <key2>
            将列表k下标为index的值替换成value
                lset <k> <index> <value>
        查
            按照索引下标获得元素(从左到右)，最后一个下标为-1
                lrange <k> <begin> <end>
            按照索引下标获得元素(从左到右)
                lindex <k> <i>
            查询列表长度
                llen <k>
        ```