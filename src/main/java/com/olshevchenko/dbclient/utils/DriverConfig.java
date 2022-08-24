package com.olshevchenko.dbclient.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
@AllArgsConstructor
public class DriverConfig {
    private Properties properties;

    public DataSource dataSource() {
        try {
            String jdbcDriver = properties.getProperty("jdbc.driver");
            String jdbcUrl = properties.getProperty("jdbc.url");
            String jdbcUser = properties.getProperty("jdbc.user");
            String jdbcPassword = properties.getProperty("jdbc.password");

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(jdbcDriver);
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(jdbcUser);
            dataSource.setPassword(jdbcPassword);
            return dataSource;
        } catch (Exception e) {
            log.error("Cannot connect to database.", e);
            throw new RuntimeException("Cannot connect to database.", e);
        }

    }


}
