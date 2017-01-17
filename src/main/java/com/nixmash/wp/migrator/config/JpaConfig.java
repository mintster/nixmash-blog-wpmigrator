package com.nixmash.wp.migrator.config;

import com.nixmash.wp.migrator.auditors.AuditingDateTimeProvider;
import com.nixmash.wp.migrator.auditors.DateTimeService;
import com.nixmash.wp.migrator.auditors.UsernameAuditorAware;
import com.nixmash.wp.migrator.db.local.repository.PostRepository;
import org.hibernate.annotations.Filter;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@Profile("mysql")
public abstract class JpaConfig {

    // region Constants

    private static final Logger logger = LoggerFactory.getLogger(JpaConfig.class);
    public static final String UNDEFINED = "**UNDEFINED**";
    public static final String CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    public static final String VALIDATOR_APPLY_TO_DDL = "hibernate.validator.apply_to_ddl";
    public static final String VALIDATOR_AUTOREGISTER_LISTENERS = "hibernate.validator.autoregister_listeners";
    public static final String ZERO_DATETIME_BEHAVIOR = "hibernate.connection.zeroDateTimeBehavior";

    // endregion

    @Autowired
    JpaProperties jpaProperties;

    @Autowired
    private Environment environment;

    @Value("#{ environment['entity.package'] }")
    private String entityPackage;

//    public abstract DataSource dataSource();

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="wp")
    public DataSource wpDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean wpEntityManagerFactory(
            EntityManagerFactoryBuilder factory) {
        return factory.dataSource(wpDataSource())
                .packages("com.nixmash.wp.migrator.db.wp")
                .properties(getVendorProperties(wpDataSource())).build();
    }

    @Bean
    PlatformTransactionManager wpTransactionManager(EntityManagerFactoryBuilder factory) {
        return new JpaTransactionManager(wpEntityManagerFactory(factory).getObject());
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }

//    @Bean
//    @Qualifier(value = "jpaTransactionManager")
//    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }

    @Bean(name = "jpaTransactionManager")
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder factory) {
        return new JpaTransactionManager(wpEntityManagerFactory(factory).getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder factory) {
        return factory.dataSource(dataSource())
                .packages("com.nixmash.wp.migrator.db.local")
                .properties(getVendorProperties(dataSource())).build();
    }

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "wpemf", transactionManagerRef = "wpTransactionManager")
    public static class WpJpaRepositoriesConfig {}

    @Configuration
    @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager",
        basePackages = "com.nixmash.wp.migrator.db.local")
    public static class JpaRepositoriesConfig {}

//    @Configuration
//    @EnableJpaRepositories(entityManagerFactoryRef = "emf_one", transactionManagerRef = "tx_manager_one", includeFilters =
//    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = DbOneRepository.class))
//    static class DbOnepaRepositoriesConfig {}
//
//
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(false);
//        vendorAdapter.setDatabasePlatform(getDatabaseDialect().getName());
//        vendorAdapter.setShowSql(true);
//
//        LocalContainerEntityManagerFactoryBean factory =
//                new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan(entityPackage);
//        factory.setDataSource(dataSource());
//        if (getJpaProperties() != null) {
//            factory.setJpaProperties(getJpaProperties());
//        }
//        return factory;
//    }

//    @Configuration
//    @EnableJpaRepositories(entityManagerFactoryRef = "emf_one", transactionManagerRef = "tx_manager_one", includeFilters =
//    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = DbOneRepository.class))
//    static class DbOnepaRepositoriesConfig {}
//
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

    @Bean
    public DatabasePopulator databasePopulator() {
        return null;
    }

    public Class<? extends Dialect> getDatabaseDialect() {
        return MySQL5InnoDBDialect.class;
    }

    // region Get Properties from datasource .properties file


    private String getZeroDateTimeBehavior() {
        return environment.getProperty("hibernate.connection.zeroDateTimeBehavior", UNDEFINED);
    }

    public String getDatabaseName() {
        return environment.getProperty("database.name", UNDEFINED);
    }

    public String getHost() {
        return environment.getProperty("database.host", UNDEFINED);
    }

    public String getPort() {
        return environment.getProperty("database.port", UNDEFINED);
    }

    public String getUrl() {
        return environment.getProperty("database.url", UNDEFINED);
    }

    public String getUser() {
        return environment.getProperty("database.username", UNDEFINED);
    }

    public String getPassword() {
        return environment.getProperty("database.password", UNDEFINED);
    }

    public String getDriverClassName() {
        return environment.getProperty("database.driverClassName", UNDEFINED);
    }

    public String getDialect() {
        return environment.getProperty("database.dialect", UNDEFINED);
    }

    public String getDatabaseVendor() {
        return environment.getProperty("database.vendor", UNDEFINED);
    }

    public String getHbm2ddl() {
        return environment.getProperty("database.hbm2ddl.auto", "none");
    }

    public String getShowSql() {
        return environment.getProperty("hibernate.showsql", "TRUE");
    }

    public String getHibernateCharSet() {
        return environment.getProperty("database.hibernateCharSet", "UTF-8");
    }

    public String getDatabaseValidationQuery() {
        return environment.getProperty("database.validation.query", UNDEFINED);
    }

    public String getH2SqlExtractor() {
        return environment.getProperty("hibernate.hbm2ddl.import_files_sql_extractor",UNDEFINED);
    }

    // endregion

}
