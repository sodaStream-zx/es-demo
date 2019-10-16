package zxx.pri.core.esTest;

import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
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

    @Test
    public void myTest2() {

    }
}
