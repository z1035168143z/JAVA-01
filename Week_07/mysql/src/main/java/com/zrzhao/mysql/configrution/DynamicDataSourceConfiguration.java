package com.zrzhao.mysql.configrution;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
@Configuration
public class DynamicDataSourceConfiguration {

    @Bean
    public DataSource getDataSource() {
        AbstractRoutingDataSource dataSource = new AbstractRoutingDataSource() {

            @Override
            protected Object determineCurrentLookupKey() {
                return DynamicDataSourceContextHolder.getDataSourceKey();
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("write", dataSourceWrite());
        targetDataSources.put("read", dataSourceRead());

        dataSource.setTargetDataSources(targetDataSources);

        return dataSource;
    }

    @Value("${datasource.write.url}")
    private String writeJdbcUrl;
    @Value("${datasource.write.username}")
    private String writeUserName;

    @Value("${datasource.read.url}")
    private String readJdbcUrl;
    @Value("${datasource.read.username}")
    private String readUserName;

    public DataSource dataSourceWrite() {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(writeJdbcUrl);
        configuration.setUsername(writeUserName);
        return new HikariDataSource(configuration);
    }

    public DataSource dataSourceRead() {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(readJdbcUrl);
        configuration.setUsername(readUserName);
        return new HikariDataSource(configuration);
    }

}
