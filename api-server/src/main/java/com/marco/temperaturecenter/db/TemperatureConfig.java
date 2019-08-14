package com.marco.temperaturecenter.db;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marco.temperaturecenter.controller.TemperatureController;

@Configuration
public class TemperatureConfig {

	@Bean
	public Logger getLogger()
	{
		return Logger.getLogger(TemperatureController.class.getName());
	}
}
