package zxx.pri.core.service;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Twilight
 * @desc 单列es连接 不释放
 * @createTime 2019-04-28-22:55
 */
//@Configuration
public class EsFactory {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    //    private final String ip = "10.253.90.84";
    private final String ip = "10.253.90.90";
    private final String localIp = "127.0.0.1";

    //    @Bean(value = "transportClient")
    public Client client() {
        Settings settings = Settings.builder().put("cluster.name", "my-test")//集群名称
                .put("client.transport.sniff", true) //设置客户端监控集群状态 自动把机器ip地址加到客户端
                .build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), 9300));
        } catch (UnknownHostException e) {
            log.error("连接es服务器异常 {} ", e);
        }
        return client;
    }
}
