package com.marco.temperaturecenter.db.data.temperature;



import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;



@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Document(collection = "last_temperature")
public class TemperatureCurrentValue extends Data {
	
	public TemperatureCurrentValue(double temperature,String location)
	{
		super(temperature, location,buildUniqueID(location, TEMPERATURE_ID));
	}
	
	public TemperatureCurrentValue()
	{
		super();
	}
	
	public static TemperatureCurrentValue getEmpty(String location)
	{
		return getEmpty(buildUniqueID(location, TEMPERATURE_ID));
	}
}
