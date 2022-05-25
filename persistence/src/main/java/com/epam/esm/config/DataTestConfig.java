package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
//@PropertySource("classpath:application-de.properties")
@Profile("test")
public class DataTestConfig {
    @Bean
    public DataSource dataSource() {

        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(false)
                .setName("giftsystemdb")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create-table-test.sql")
                .addScript("data-test.sql")
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
    }
//public class HikariCPDataSource {
//
//    private  HikariConfig config = new HikariConfig();
//    private  HikariDataSource ds;
//
////    static {
////        config.setJdbcUrl("jdbc:h2:mem:test");
////        config.setUsername("user");
////        config.setPassword("password");
////        config.addDataSourceProperty("cachePrepStmts", "true");
////        config.addDataSourceProperty("prepStmtCacheSize", "250");
////        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
////        ds = new HikariDataSource(config);
////    }
////
////    public static Connection getConnection() throws SQLException {
////        return ds.getConnection();
////    }
//
//    private HikariCPDataSource(){}
//}
//
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}