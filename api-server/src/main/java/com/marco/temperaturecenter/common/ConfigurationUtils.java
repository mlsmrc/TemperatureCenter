package com.marco.temperaturecenter.common;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationUtils {

	@Bean
	public Logger getLogger()
	{
		return Logger.getLogger("api server");
	}
}

