package zxx.pri.demo.core;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Twilight
 * @desc 单列es连接 不释放
 * @createTime 2019-04-28-22:55
 */
@Configuration
public class EsFactory {
    @Bean(value = "esClient")
    public Client client() {
        Settings settings = Settings.builder().put("cluster.name", "ClusterName")//集群名称
                .put("client.transport,sniff", true) //设置客户端监控集群状态 自动把机器ip地址加到客户端
                .build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }
}
