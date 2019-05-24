package com.ef;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
//		assertThat(this.applicationContext).isNotNull();
//		assertThat(this.applicationContext.containsBean("databaseService")).isTrue();
//		assertThat(this.applicationContext.containsBean("loggerService")).isTrue();
//		assertThat(this.applicationContext.containsBean("parserApplication")).isTrue();
	}
}