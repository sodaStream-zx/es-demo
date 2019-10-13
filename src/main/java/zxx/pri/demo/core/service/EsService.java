package zxx.pri.demo.core.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.get.GetField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-05-02-00:38
 */
//@Service
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EsService {

    @Autowired
    private Client client;

    @Test
    public void myTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "zxx");
        jsonObject.put("age", 26);
        jsonObject.put("pwd", "zxx1994");
        IndexResponse indexResponse = client.prepareIndex("person", "zxx", "1").setSource(jsonObject.toJSONString()).get();
        System.out.println(indexResponse.getIndex());
        System.out.println(indexResponse.getType());
        System.out.println(indexResponse.getVersion());
        System.out.println(indexResponse.getResult().toString());
    }

    @Test
    public void myTest2() {
        ActionFuture<GetResponse> doc = client.get(new GetRequest().index("blog").type("blog"));
        GetResponse getFields = doc.actionGet();
        Map<String, GetField> fields = getFields.getFields();
        fields.forEach((s, objects) -> System.out.println(s + " - " + objects));
    }

    @Test
    public void myTest3() {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

    }
}
