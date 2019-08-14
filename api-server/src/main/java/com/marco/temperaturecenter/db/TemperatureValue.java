package com.marco.temperaturecenter.db;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "today_temperature")
public class TemperatureValue {
	
	@Id private String _id;
	private double temperature;
	private String location;
	
	private Date date;
	
	public TemperatureValue(double temperature,String location)
	{
		this.temperature=temperature;
		this.location=location;
		date=new Date();
	}
}
