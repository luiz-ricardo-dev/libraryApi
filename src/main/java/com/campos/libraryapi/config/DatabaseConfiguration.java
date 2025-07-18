package com.campos.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.metadata.HanaCallMetaDataProvider;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    /**
     * Configuracao Hikari
     * https://github.com/brettwooldridge/HikariCP
     * @return
     */

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); //maximo de conexões liberadas
        config.setMinimumIdle(1); //tamanho inical do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(6000000); //milisegundos (10 minutos)
        config.setConnectionTimeout(100000); //timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); //teste de conexão do banco

        return new HikariDataSource(config);
    }
}
