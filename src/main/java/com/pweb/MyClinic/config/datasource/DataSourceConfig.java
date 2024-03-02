package com.pweb.MyClinic.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptyMap;
import static net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel.INFO;
import static org.springframework.orm.jpa.vendor.Database.POSTGRESQL;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final HibernateProperties hibernateProperties;
    private final SQlLogProperties sqlLogProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(POSTGRESQL);

        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.pweb.MyClinic");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    @Primary
    public DataSource dataSource(){
        final var dataSource = new HikariDataSource(hikariConfig());

        if (sqlLogProperties.logAllQueries || sqlLogProperties.logSlowQueries) {
            var proxyDataSourceBuilder = ProxyDataSourceBuilder.create(dataSource);
            if (sqlLogProperties.logAllQueries) {
                proxyDataSourceBuilder.logQueryBySlf4j(INFO);
            }
            if (sqlLogProperties.logSlowQueries) {
                proxyDataSourceBuilder.logSlowQueryBySlf4j(sqlLogProperties.slowQueryThreshold, sqlLogProperties.slowQueryTimeUnit);
            }
            if (sqlLogProperties.jsonFormat) {
                proxyDataSourceBuilder.asJson();
            }

            return proxyDataSourceBuilder.build();
        } else {
            return dataSource;
        }
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariConfig hikariConfig(){
        return this.getHikariConfig();
    }

    @Configuration
    @ConfigurationProperties("spring.jpa")
    public static class HibernateProperties {

        @Getter
        @Setter
        private Map<String, String> properties = emptyMap();
    }

    @Getter
    @Setter
    @Validated
    @Configuration
    @ConfigurationProperties(prefix = "spring.datasource.logging")
    public static class SQlLogProperties{

        private boolean jsonFormat;
        private boolean logAllQueries;
        private boolean logSlowQueries;

        @Value("${spring.datasource.logging.slow.threshold:500}")
        private int slowQueryThreshold;

        @Value("${spring.datasource.logging.slow.timeUnit:MILLISECONDS}")
        private TimeUnit slowQueryTimeUnit;

    }

    private HikariConfig getHikariConfig(){
        var hc = new HikariConfig();
        hc.setRegisterMbeans(false);
        hc.setAllowPoolSuspension(true);
        return hc;
    }

    private Properties additionalProperties(){
        var properties = new Properties();
        hibernateProperties.properties.forEach(properties::setProperty);
        return properties;
    }
}
