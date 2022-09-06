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