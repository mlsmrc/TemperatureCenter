package com.marco.temperaturecenter.common;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;

public class ConfigurationUtilsTest {

	@Bean
	public Logger getTestLogger()
	{
		return Logger.getLogger("api server - test");
	}}

