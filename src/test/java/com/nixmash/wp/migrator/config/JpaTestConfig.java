package com.nixmash.wp.migrator.config;

import com.nixmash.wp.migrator.auditors.AuditingDateTimeProvider;
import com.nixmash.wp.migrator.auditors.DateTimeService;
import com.nixmash.wp.migrator.auditors.UsernameAuditorAware;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
public class JpaTestConfig {

    // region Constants

    public static final String LOCAL = "local";
    public static final String WP = "wp";

    private static final String UNDEFINED = "**UNDEFINED**";
    private static final String CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    private static final String ZERO_DATETIME_BEHAVIOR = "hibernate.connection.zeroDateTimeBehavior";

    // endregion

    @Autowired
    private Environment environment;

    // region Beans

    @Autowired
    private JpaProperties jpaProperties;

    // endregion

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.test.datasource.h2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="wp.spring.test.datasource")
    public DataSource wpDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder factory) {
        return factory.dataSource(dataSource())
                .packages("com.nixmash.wp.migrator.db.local")
                .properties(getVendorProperties(dataSource()))
                .persistenceUnit(LOCAL)
                .build();
    }

    @Bean(name = "wpEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean wpEntityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform(MySQL5InnoDBDialect.class.getName());
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.nixmash.wp.migrator.db.wp");
        factory.setDataSource(wpDataSource());
        factory.setPersistenceUnitName(WP);
        if (getJpaProperties() != null) {
            factory.setJpaProperties(getJpaProperties());
        }
        return factory;
    }

    @Bean
    @Primary
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "wpTransactionManager")
    public PlatformTransactionManager wpTransactionManager(EntityManagerFactoryBuilder factory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(wpEntityManagerFactory().getObject());
        tm.setDataSource(wpDataSource());
        tm.setPersistenceUnitName(WP);
        return tm;
    }

    @Bean
    public DatabasePopulator databasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.setIgnoreFailedDrops(true);
        populator.addScripts(new ClassPathResource("/db/h2schema.sql"),
                new ClassPathResource("/db/h2data.sql"));
        try {
            populator.populate(dataSource.getConnection());
        } catch (SQLException ignored) {
        }
        return populator;
    }

    // region static JpaRepository Configuration Classes

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
            transactionManagerRef = "transactionManager",
            basePackages = "com.nixmash.wp.migrator.db.local")
    public static class JpaRepositoriesConfig {}

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "wpEntityManagerFactory",
            transactionManagerRef = "wpTransactionManager",
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

    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBM2DDL_AUTO, getHbm2ddl());
        properties.setProperty(HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR, getH2SqlExtractor());
        properties.setProperty(GENERATE_STATISTICS, TRUE.toString());
        properties.setProperty(SHOW_SQL, getShowSql());
        properties.setProperty(ZERO_DATETIME_BEHAVIOR, getZeroDateTimeBehavior());
        properties.setProperty(FORMAT_SQL, TRUE.toString());
        properties.setProperty(USE_SQL_COMMENTS, TRUE.toString());
        properties.setProperty(CONNECTION_CHAR_SET, getHibernateCharSet());
        return properties;
    }

    private Map<String, Object> buildProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(HBM2DDL_AUTO, getHbm2ddl());
        properties.put(HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR, getH2SqlExtractor());
        properties.put(GENERATE_STATISTICS, TRUE.toString());
        properties.put(SHOW_SQL, getShowSql());
        properties.put(ZERO_DATETIME_BEHAVIOR, getZeroDateTimeBehavior());
        properties.put(FORMAT_SQL, TRUE.toString());
        properties.put(USE_SQL_COMMENTS, TRUE.toString());
        properties.put(CONNECTION_CHAR_SET, getHibernateCharSet());
        return properties;
    }


    private String getZeroDateTimeBehavior() {
        return environment.getProperty("spring.jpa.mysql.test.properties.hibernate.connection.zeroDateTimeBehavior", UNDEFINED);
    }

    public String getHbm2ddl() {
        return environment.getProperty("spring.jpa.mysql.test.properties.hibernate.hbm2ddl.auto", "none");
    }

    public String getShowSql() {
        return environment.getProperty("spring.jpa.mysql.test.properties.hibernate.showsql", "TRUE");
    }

    public String getHibernateCharSet() {
        return environment.getProperty("spring.jpa.mysql.test.properties.hibernate.connection.charSet", "UTF-8");
    }

    public String getH2SqlExtractor() {
        return environment.getProperty("spring.jpa.mysql.test.properties.hibernate.hbm2ddl.import_files_sql_extractor",UNDEFINED);
    }


    // endregion
}
