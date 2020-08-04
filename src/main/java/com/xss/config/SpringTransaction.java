package com.xss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.xss.service")
@PropertySource(value="classpath:sysPicPath.properties",encoding = "utf-8")
public class SpringTransaction {

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
