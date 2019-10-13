package zxx.pri.demo.core.esconfigs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zxx
 * @desc
 * @createTime 2019-03-11-下午 3:34
 */
@ConfigurationProperties(value = "common.es")
public class ElasticSearchProperties {
    private boolean enabled;
    private Map<String, String> indexs = new HashMap<>();
    private Map<String, String> types = new HashMap<>();
    private String clusterName;
    private List<String> hosts = new LinkedList<>();
    private int port;

    @Override
    public String toString() {
        return "ElasticSearchProperties{" +
                "enabled=" + enabled +
                ", indexs=" + indexs +
                ", types=" + types +
                ", clusterName='" + clusterName + '\'' +
                ", hosts=" + hosts +
                ", port=" + port +
                '}';
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, String> getIndexs() {
        return indexs;
    }

    public void setIndexs(Map<String, String> indexs) {
        this.indexs = indexs;
    }

    public Map<String, String> getTypes() {
        return types;
    }

    public void setTypes(Map<String, String> types) {
        this.types = types;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
