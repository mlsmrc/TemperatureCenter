package com.marco.temperaturecenter.db.data.temperature;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Document (collection = "monthly_temperature")
public class TemperatureMonthlyValue extends Data {

	private int month;
	private int year;
	public TemperatureMonthlyValue(double temperature,String location,int month,int year)
	{
		super(temperature, location,build_id(year, month));
		this.month=month;
		this.year=year;
	}
	
	public TemperatureMonthlyValue()
	{
		super();
	}
	public TemperatureMonthlyValue(double temperature,String location)
	{
		super(temperature, location,build_currentid());
		setCurrentDateField();
	}
	
	public static String build_id(int year,int month)
	{
		return year+" "+month;
	}
	private void setCurrentDateField()
	{
		Calendar c = new GregorianCalendar();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
	}
	public static String build_currentid()
	{
		return build_id(new GregorianCalendar().get(Calendar.YEAR), 
						new GregorianCalendar().get(Calendar.MONTH));
	}
}
