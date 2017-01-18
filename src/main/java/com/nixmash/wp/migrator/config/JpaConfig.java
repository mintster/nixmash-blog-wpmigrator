package com.nixmash.wp.migrator.config;

import com.nixmash.wp.migrator.auditors.AuditingDateTimeProvider;
import com.nixmash.wp.migrator.auditors.DateTimeService;
import com.nixmash.wp.migrator.auditors.UsernameAuditorAware;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
@Profile("mysql")
public class JpaConfig {

    // region Constants

    public static final String UNDEFINED = "**UNDEFINED**";
    public static final String CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    public static final String ZERO_DATETIME_BEHAVIOR = "hibernate.connection.zeroDateTimeBehavior";

    // endregion

    // region Beans

    @Autowired
    JpaProperties jpaProperties;

    // endregion

    // region Datasources

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="wp.spring.datasource")
    public DataSource wpDataSource() {
        return DataSourceBuilder.create().build();
    }

    // endregion

    // region EntityManagerFactories

    @Bean(name = "wpEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean wpEntityManagerFactory(
            EntityManagerFactoryBuilder factory) {
        return factory.dataSource(wpDataSource())
                .packages("com.nixmash.wp.migrator.db.wp")
                .properties(getVendorProperties(wpDataSource()))
//                .persistenceUnit("wp")
                .build();
    }

    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder factory) {
        return factory.dataSource(dataSource())
                .packages("com.nixmash.wp.migrator.db.local")
//                .persistenceUnit("local")
                .properties(getVendorProperties(dataSource()))
                .build();
    }

    // endregion

    // region PlatformTransactionManagers

    @Bean
    @Primary
    PlatformTransactionManager wpTransactionManager(EntityManagerFactoryBuilder factory) {
        return new JpaTransactionManager(entityManagerFactory(factory).getObject());
    }

    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder factory) {
        return new JpaTransactionManager(wpEntityManagerFactory(factory).getObject());
    }

    // endregion

    // region static JpaRepository Configuration Classes

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager",
        basePackages = "com.nixmash.wp.migrator.db.local")
    public static class JpaRepositoriesConfig {}

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "wpEntityManagerFactory", transactionManagerRef = "wpTransactionManager",
            basePackages = "com.nixmash.wp.migrator.db.wp")
    public static class WpJpaRepositoriesConfig {}

    // endregion

    // region Auditing

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }

    // endregion

    // region getProperties

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    // endregion

}
