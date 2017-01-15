package com.nixmash.wp.migrator.config;

import com.nixmash.wp.migrator.auditors.CurrentTimeDateTimeService;
import com.nixmash.wp.migrator.auditors.DateTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.nixmash.wp.migrator")
public class ApplicationConfig {

    @Bean
    DateTimeService currentTimeDateTimeService() {
        return new CurrentTimeDateTimeService();
    }

}
