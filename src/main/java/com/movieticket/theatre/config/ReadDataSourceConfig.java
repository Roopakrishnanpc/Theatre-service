package com.movieticket.theatre.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
@Configuration
@EnableJpaRepositories(
        basePackages = "com.movieticket.theatre.infrastructure.read.repository",
        entityManagerFactoryRef = "readEntityManagerFactory",
        transactionManagerRef = "readTransactionManager"
)
public class ReadDataSourceConfig {

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactory(
            @Qualifier("readDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);
        em.setPackagesToScan(
                "com.movieticket.theatre.infrastructure.read.entity"
        );

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.MySQL8Dialect");

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager readTransactionManager(
            @Qualifier("readEntityManagerFactory")
            EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}