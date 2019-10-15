package zxx.pri.core.esconfigs;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * @author Twilight
 * @desc 自定义健康检测
 * @createTime 2019-03-24-21:29
 */

public class ElasticsearchHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().build();
    }
}
