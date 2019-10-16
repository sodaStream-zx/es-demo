package zxx.pri.core.config.datasourceconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author zxx
 * @desc
 * @createTime 2019-04-18-下午 4:36
 */
@Configuration
@EnableConfigurationProperties(value = CustomDuridDataSourceProperties.class)
@ConditionalOnProperty(value = "common.custom-durid.enabled")
public class CustomDataSourceAutoConfig {
    private static final Logger log = LoggerFactory.getLogger(CustomDataSourceAutoConfig.class);
    @Autowired
    private CustomDuridDataSourceProperties customDuridDataSourceProperties;

    @Bean
    public DataSource dataSource() {
        log.warn("-------------->>druid 连接池配置 {}", customDuridDataSourceProperties.getUrl());
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(customDuridDataSourceProperties.getUrl());
        datasource.setDriverClassName(customDuridDataSourceProperties.getDriverClassName());
        datasource.setUsername(customDuridDataSourceProperties.getUsername());
        datasource.setPassword(customDuridDataSourceProperties.getPassword());
        datasource.setInitialSize(customDuridDataSourceProperties.getInitialSize());
        datasource.setMinIdle(customDuridDataSourceProperties.getMinIdle());
        datasource.setMaxWait(customDuridDataSourceProperties.getMaxWait());
        datasource.setMaxActive(customDuridDataSourceProperties.getMaxActive());
        datasource.setMinEvictableIdleTimeMillis(customDuridDataSourceProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(customDuridDataSourceProperties.getValidationQuery());
        datasource.setTestWhileIdle(customDuridDataSourceProperties.isTestWhileIdle());
        datasource.setTestOnBorrow(customDuridDataSourceProperties.isTestOnBorrow());
        datasource.setTestOnReturn(customDuridDataSourceProperties.isTestOnReturn());
        datasource.setPoolPreparedStatements(customDuridDataSourceProperties.getPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(customDuridDataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(customDuridDataSourceProperties.getFilters());
        } catch (SQLException e) {
            log.error("Druid Datasource Connect Exception {}", e);
        }
        return datasource;
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean
    public WallConfig wallConfig() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);//允许一次执行多条语句
        return wallConfig;
    }
}
