package zxx.pri.core.esTest;

import com.alibaba.fastjson.JSON;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-10-13-13:53
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EsHttpService {
    @Autowired
    private JestHttpClient jestHttpClient;

    @Test
    public void myTest() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("date", "2019-10-16");
        jsonMap.put("name", "zxx");
        jsonMap.put("tweet", "白雪纷纷何所似");
        jsonMap.put("user_id", "2");
        jsonMap.put("email", "1139835238@qq.com");
        jsonMap.put("username", "gangdaner");
        Index index = new Index.Builder(jsonMap).index("gb").type("tweet").build();
        DocumentResult execute = jestHttpClient.execute(index);
        System.out.println(execute.getJsonString());
    }

    /**
     * desc:打印jest响应信息
     *
     * @Return:
     **/
    private static void jestResultPrint(JestResult jestResult) {
        System.out.println(jestResult.isSucceeded());
        System.out.println(jestResult.getResponseCode());
        System.out.println(jestResult.getPathToResult());
        System.out.println(jestResult.getJsonString());
        System.out.println(jestResult.getSourceAsString());
    }

    //新建索引
    @Test
    public void myTest2() throws IOException {
        String settings = "{\n" +
                "    \"number_of_shards\": 5,\n" +
                "    \"number_of_replicas\": 1\n" +
                "}";
        Map<String, String> asMap = Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1)
                .build()
                .getAsMap();
        CreateIndex articel = new CreateIndex.Builder("articel").settings(JSON.toJSONString(asMap)).build();
        jestHttpClient.executeAsync(articel, new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult jestResult) {
                EsHttpService.jestResultPrint(jestResult);
            }
            @Override
            public void failed(Exception e) {
                System.out.println("artical 索引创建失败");
            }
        });
        System.out.println("----------------------------------------------------------------------");
//        JestResult book = jestHttpClient.execute(new CreateIndex.Builder("book").settings(settings).build());
//        this.jestResultPrint(book);
    }

    /*mapping*/
    @Test
    public void myTest3() throws IOException {
        String jsonstr = "{ \"archive\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }";
        PutMapping pm = new PutMapping.Builder("articel", "archive", jsonstr).build();
        JestResult execute = jestHttpClient.execute(pm);
        jestResultPrint(execute);
    }

    /*search source */
    @Test
    public void myTest4() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "Place")).size(10).sort("age", SortOrder.ASC);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("mytest").addType("account").build();
        SearchResult execute = jestHttpClient.execute(search);
        System.out.println("总数：" + execute.getTotal());
        System.out.println("查询成功？ " + execute.isSucceeded());
        List<SearchResult.Hit<Account, Void>> hits = execute.getHits(Account.class);
        hits.stream().forEach(accountVoidHit -> System.out.println(accountVoidHit.source.toString()));
//        System.out.println(execute.getSourceAsString());
    }
}
