package com.nixmash.wp.migrator.config;

import com.nixmash.wp.migrator.auditors.AuditingDateTimeProvider;
import com.nixmash.wp.migrator.auditors.DateTimeService;
import com.nixmash.wp.migrator.auditors.UsernameAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
@Profile("mysql")
public class JpaConfig {

    // region Constants

    private static final String LOCAL = "local";
    private static final String WP = "wp";

    // endregion

    // region Beans

    @Autowired
    private JpaProperties jpaProperties;

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
                .persistenceUnit(WP)
                .build();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder factory) {
        return factory.dataSource(dataSource())
                .packages("com.nixmash.wp.migrator.db.local")
                .properties(getVendorProperties(dataSource()))
                .persistenceUnit(LOCAL)
                .build();
    }

    // endregion

    // region PlatformTransactionManagers

    @Bean
    PlatformTransactionManager wpTransactionManager(EntityManagerFactoryBuilder factory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(wpEntityManagerFactory(factory).getObject());
        tm.setDataSource(wpDataSource());
        tm.setPersistenceUnitName(WP);
        return tm;
    }

    @Bean
    @Primary
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    // endregion

    // region static JpaRepository Configuration Classes

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
            transactionManagerRef = "transactionManager",
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
