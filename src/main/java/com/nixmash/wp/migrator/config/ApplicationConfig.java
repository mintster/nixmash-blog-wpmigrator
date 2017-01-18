package com.nixmash.wp.migrator.config;

import com.nixmash.wp.migrator.auditors.CurrentTimeDateTimeService;
import com.nixmash.wp.migrator.auditors.DateTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
//@EnableConfigurationProperties
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.nixmash.wp.migrator")
// WORKS! @EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = "com.nixmash.wp.migrator.db")
@PropertySource("classpath:/application.properties")
public class ApplicationConfig {

    @Bean
    DateTimeService dateTimeService() {
        return new CurrentTimeDateTimeService();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
