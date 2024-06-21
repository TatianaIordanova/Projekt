package com.tss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**It's a Spring configuration class that defines a bean for the application's main data source(cars and persons tables)
 * Spring take data for dataSource from application.properties file
*/
@Configuration
public class MainDataSourceConfig {
    
    @Autowired
    private Environment env;

    @Bean
    @Qualifier("mainDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSourceTss() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }
}