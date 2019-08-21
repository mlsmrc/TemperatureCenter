package com.marco.temperaturecenter.common;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationUtilsTest {

	/*@Bean
	public Logger getTestLogger()
	{
		return Logger.getLogger("api server - test");
	}
	*/
	@Bean
	public MongoTest getTestMongoInstance()
	{
		return new MongoTest();
	}
}

