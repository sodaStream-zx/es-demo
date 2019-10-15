package zxx.pri.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.aggregations.Aggregations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-05-02-00:38
 */
//@Service
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EsSocketService {

    @Autowired
    private Client client;

    @Test
    public void myTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "zxx");
        jsonObject.put("age", 26);
        jsonObject.put("pwd", "zxx1994");
        IndexResponse indexResponse = client.prepareIndex("person", "user", "1").setSource(jsonObject.toJSONString()).get();
        System.out.println(indexResponse.getIndex());
        System.out.println(indexResponse.getType());
        System.out.println(indexResponse.getVersion());
        System.out.println(indexResponse.getResult().toString());
    }

    @Test
    public void myTest_3() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("date", "2019-10-16");
        jsonMap.put("name", "zxx");
        jsonMap.put("tweet", "白雪纷纷何所似");
        jsonMap.put("user_id", "2");
        jsonMap.put("email", "1139835238@qq.com");
        jsonMap.put("username", "gangdaner");
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("gb", "tweet");
        IndexResponse indexResponse = indexRequestBuilder.setSource(jsonMap).get();
        System.out.println(indexResponse);

    }

    @Test
    public void myTest2() {
        SearchResponse searchResponse = client.search(new SearchRequest().indices("gb").types("tweet")).actionGet();
        JSONObject jsonObject = JSON.parseObject(searchResponse.toString());
        JSONObject jsonObject1 = JSON.parseObject(jsonObject.get("hits").toString());
        System.out.println(jsonObject1.toJSONString());
        Aggregations aggregations = searchResponse.getAggregations();
        int numReducePhases = searchResponse.getNumReducePhases();
        String scrollId = searchResponse.getScrollId();
        System.out.println(searchResponse.toString());
        System.out.println(searchResponse.getHits());
        System.out.println(searchResponse.getHits().getHits().toString());
    }

    @Test
    public void myTest3() {
    }
}
