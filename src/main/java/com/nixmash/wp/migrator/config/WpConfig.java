package com.nixmash.wp.migrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by daveburke on 1/13/17.
 */
@Configuration
public class WpConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
