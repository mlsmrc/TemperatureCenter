package com.marco.temperaturecenter.db.value;

import java.util.HashMap;
import java.util.Map;

public class TemperatureValue {
	
	private long uid=-1;
	private double temperature=-1;
	private String location="";
	public TemperatureValue(long uid,double temperature,String location)
	{
		this.uid=uid;
		this.temperature=temperature;
		this.location=location;
	}
	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}
	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Map<String,String> toMap()
	{
		Map<String,String> t = new HashMap<String,String>();
        
        t.put("room", location);
        t.put("requested", "temperature");
        t.put("value", String.valueOf(temperature));
        return t;
	}

	public static Map<String,String> value(long uid,double temperature,String location)
	{
		return new TemperatureValue(uid, temperature, location).toMap();
	}
}
