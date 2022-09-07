# ElasticSearch

## 基础知识
* 什么是ElasticSearch：
    ElasticSearch（简称ES）是一个**分布式**、**Resful风格的** **搜索和数据分析** 引擎，是用Java开发并且是当前最流行的开源的企业级搜索引擎，能够达到近实时搜索，稳定可靠快速安装使用方便

* 什么是倒排索引：
    * 正排索引：通过id，查找关键字，或内容
    * 倒排索引：通过关键字，查找id（有点类似非主键索引）
* Solr与ES比较：
    * Solr需要zk进行分布式管理，而ES自身带有分布式协同管理功能
    * Solr支持更多格式数据，入JSON、XML、CSV，ES只支持JSON
    * Solr对已有数据搜索较好，但实时搜索效率明显低于ES
* 通用数据处理流程：
    <img src='./images/2.png'>
    
## 集群配置
各版本下载[地址](https://www.elastic.co/cn/downloads/past-releases#elasticsearch)
### ES
* 机器环境配置
    * 使用es自带jdk`bin/elasticsearch-env`，添加以下内容
        ```
        ES_HOME=/home/l/develop/elasticsearch-7.17.5
        ES_JAVA_HOME=/home/l/develop/elasticsearch-7.17.5/jdk
        ```
        * `config/jvm.options`文件，修改
            ```
            -Xms1g
            -Xmx1g
            ```
    * 修改最大虚拟内存`/etc/sysctl.conf`，添加以下内容
        ```
        vm.max_map_count = 655360
        ```
        执行`sysctl -p`
    * 配置es可以创建的最大线程数量`vim /etc/security/limits.conf`，添加以下内容
        ```
        es soft nofile 65535
        es hard nofile 65535
        es soft nproc 4096
        es hard nproc 4096
        ```
* 配置文件
    * `config/elasticsearch.yml`
        ```
        # 集群名
        cluster.name: my-application
        # 节点名称，每个节点名称各异 
        node.name: vUbuntu1
        # 当前节点地址
        network.host: vUbuntu1
        # 当前节点端口
        http.port: 9200
        # 注册发现节点地址
        discovery.seed_hosts: ["vUbuntu1","vUbuntu2","vUbuntu3"]
        # 各节点地址
        cluster.initial_master_nodes: ["vUbuntu1","vUbuntu2","vUbuntu3"]
        # 设置geoip为false
        ingest.geoip.downloader.enabled: false
        #允许跨域访问
        http.cors.enabled: true
        http.cors.allow-origin: "*"
        ```
* 启动
```
bin/elasticsearch
```
浏览器访问`http://vubuntu1:9200/_cat/health?v`进行查看
### Kibana
* 配置`config/kibana.yml`
    ```
    # 访问端口
    server.port: 5601
    # 访问地址
    server.host: "vUbuntu1"
    # es各节点地址
    elasticsearch.hosts: ["http://vUbuntu1:9200","http://vUbuntu2:9200","http://vUbuntu3:9200"]
    ```
* 启动`bin/kibana`
浏览器访问`http://vubuntu1:5601/app/dev_tools#/console`
<img src='./images/1.png'>

### 常用命令
    ```
    /_cat/allocation    # 查看单节点shard分配整体情况
    /_cat/shards        # 查看各个shard的详细情况
    /_cat/shards/{index}    # 查看指定分片的详细情况
    /_cat/master            # 查看master节点信息
    /_cat/nodes             # 查看各node节点信息
    /_cat/tasks             # 
    /_cat/indices           # 查看集群中所有index的详细信息
    /_cat/indices/{index}   # 查看集群中指定index的详细信息
    /_cat/segments          # 查看各index的段segment详细信息,包括segment名，所属shard，磁盘占用大小，是否刷盘
    /_cat/segments/{index}  # 查看指定index的segment详细信息
    /_cat/count             # 查看当前集群的doc数量
    /_cat/count/{index}     # 查看指定索引的doc数量
    /_cat/recovery          # 查看集群内每个shard的recorvery过程，调整replica
    /_cat/recovery/{index}  # 查看指定索引的shard的recorvery过程
    /_cat/health            # 查看几圈当前状态：红、黄、绿
    /_cat/pending_tasks     # 查看当前集群的pending task
    /_cat/aliases           # 查看集群中所有alias信息，路由配置等
    /_cat/aliases/{alias}   # 查看指定索引的alias信息
    /_cat/thread_pool       # 查看各个节点内部不同类型的threadpool的统计信息
    /_cat/thread_pool/{thread_pools}     # 查看指定thread_pools的threadpool的统计信息
    /_cat/plugins                   # 查看各节点上的plugin信息
    /_cat/fielddata                 # 查看当前集群各个节点的fielddatat内存使用情况
    /_cat/fielddata/{fields}        # 查看指定field的内存使用情况，里面传field属性对应的值
    /_cat/nodeattrs                 # 查看单节点的自定义属性   
    /_cat/repositories              # 输出几圈汇总注册快照存储库
    /_cat/snapshots/{repository}    # 
    /_cat/templates                 # 查看当前正在存在的模版信息
    /_cat/ml/anomaly_detectors
    /_cat/ml/anomaly_detectors/{job_id}
    /_cat/ml/trained_models
    /_cat/ml/trained_models/{model_id}
    /_cat/ml/datafeeds
    /_cat/ml/datafeeds/{datafeed_id}
    /_cat/ml/data_frame/analytics
    /_cat/ml/data_frame/analytics/{id}
    /_cat/transforms
    /_cat/transforms/{transform_id}
    ```