package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@ComponentScan(basePackages={"com"})
@ImportResource({"classpath:spring-cfg.xml"})
@Configuration
@EnableTransactionManagement
public class SpringConfig implements TransactionManagementConfigurer{

    @Autowired
    private DataSource dataSource;

    @Override
    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
