package com.nixmash.wp.migrator;

import com.nixmash.wp.migrator.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
		classes= { Application.class, ApplicationConfig.class})
@Transactional
@ActiveProfiles("h2")
public class WpSpringContext {

	@Autowired
	ApplicationContext ctx;

	@Test
	public void testContextLoads() throws Exception {
		assertThat(this.ctx).isNotNull();
		assertThat(this.ctx.containsBean("wpApiService")).isTrue();
		assertThat(this.ctx.containsBean("application")).isTrue();
	}
}
