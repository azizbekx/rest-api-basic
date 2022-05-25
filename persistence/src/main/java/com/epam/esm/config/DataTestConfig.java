package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@Profile("test")
@PropertySource("classpath:application-dev.properties")
public class DataTestConfig {
    @Bean
    public DataSource dataSource(
            @Value("${db.name}") String name,
            @Value("${db.schema}") String schema,
            @Value("${db.data}") String data
    ) {

        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(false)
                .setName(name)
                .setType(EmbeddedDatabaseType.H2)
                .addScript(schema)
                .addScript(data)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}