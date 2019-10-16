package zxx.pri.core.config.esconfigs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Twilight
 * @desc
 * @createTime 2019-03-24-21:51
 */
@Configuration
@ConditionalOnMissingBean(name = "EsAutoConfig")
public class EsHealthConfig {
    @Bean
    public ElasticsearchHealthIndicator elasticsearchHealthIndicator() {
        return new ElasticsearchHealthIndicator();
    }
}
