package zxx.pri.core.service;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zxx.pri.core.esconfigs.EsAutoConfig;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-10-13-13:53
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EsHttpService {
    @Autowired
    private EsAutoConfig esAutoConfig;
    @Autowired
    private JestClientFactory jestClientFactory;

    private JestClient getJestClient() {
        return esAutoConfig.jestHttpClient(jestClientFactory);
    }

    @Test
    public void myTest() {
        JestClient jestClient = this.getJestClient();
    }
}
