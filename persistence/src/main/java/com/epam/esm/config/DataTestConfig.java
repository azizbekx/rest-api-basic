package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db-test.properties")
@Profile("test")
public class DataTestConfig {
    @Bean
    public DataSource dataSource(
            @Value("${db-test.url}") String url,
            @Value("${db-test.user}") String user,
            @Value("${db-test.password}") String password,
            @Value("${db-test.driver}") String driver,
            @Value("${db-test.connections}") Integer connections) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setMaxActive(connections);

//      Populate database with createTable.sql and data.sql
        Resource initData = new ClassPathResource("create-table-test.sql");
        Resource fillData = new ClassPathResource("data-test.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData, fillData);
        DatabasePopulatorUtils.execute(databasePopulator, basicDataSource);

        return basicDataSource;

    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}