package com.infozaid.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

//@SpringBootTest
class WebcourseApplicationTests {


	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		System.out.println(applicationContext.getBeanDefinitionCount());
	}

}
