package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.components.WpUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan({"org.kamranzafar.spring.wpapi", "com.nixmash.wp.migrator"})
public class Application  {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final Environment environment;

    @Autowired
    public Application(Environment environment, WpUI wpUI) {
        this.environment = environment;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {

        ApplicationContext ctx = new
                AnnotationConfigApplicationContext("com.nixmash.wp.migrator",
                "org.kamranzafar.spring.wpapi");

        WpUI ui = ctx.getBean(WpUI.class);
        ui.init();
        ((ConfigurableApplicationContext) ctx).close();
    }

}
