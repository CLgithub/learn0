package com.cl.learn.es.test;

import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test1 implements CommandLineRunner {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void run(String... args) throws Exception {
        final String index = "demo_user";
        final String id = "1";

        // 1) 创建索引
        boolean exists = restHighLevelClient.indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
        if (!exists) {
            restHighLevelClient.indices().create(new CreateIndexRequest(index), RequestOptions.DEFAULT);
            System.out.println("[ES] 创建 index: " + index);
        } else {
            System.out.println("[ES] 缩影已经存在: " + index);
        }

        // 2) 查询简单文档
        Map<String, Object> doc = new HashMap<>();
        doc.put("name", "亚洲");
        doc.put("age", 30);
        doc.put("city", "上海");
        IndexRequest indexRequest = new IndexRequest(index).id(id).source(doc);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("[ES] 查询到文档 id=" + indexResponse.getId());

        // 3) 根据ID查询稳定
        GetResponse getResponse = restHighLevelClient.get(new GetRequest(index, id), RequestOptions.DEFAULT);
        System.out.println("[ES] 查询到文档: " + getResponse.getSourceAsString());

        // 4) Simple search (match on name)
        SearchSourceBuilder ssb = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery("name", "亚洲"))
                .size(5);
        SearchRequest searchRequest = new SearchRequest(index).source(ssb);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("[ES] 搜索到 hits: " + searchResponse.getHits().getTotalHits());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("  -> " + hit.getSourceAsString());
        }

        // 5) Delete the document (cleanup)
//        restHighLevelClient.delete(new DeleteRequest(index, id), RequestOptions.DEFAULT);
//        System.out.println("[ES] 删除文档 id=" + id);
    }
}
