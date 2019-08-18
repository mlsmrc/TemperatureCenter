package com.marco.temperaturecenter.db.data.temperature;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Document(collection = "today_temperature")
public class TemperatureDailyValue extends Data{

	public TemperatureDailyValue(double temperature,String location)
	{
		super(temperature, location);
	}
	
	public TemperatureDailyValue(double temperature,String location,Date d)
	{
		super(temperature, location, d);
	}
	
	public TemperatureDailyValue()
	{
		super();
	}
}
