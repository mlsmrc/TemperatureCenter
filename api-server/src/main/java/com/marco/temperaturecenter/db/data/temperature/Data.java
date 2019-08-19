package com.marco.temperaturecenter.db.data.temperature;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Data {
	
	public static final String TEMPERATURE_ID="1";
	public static final String HUMIDITY_ID="2";
	
	private Date date;
	private double value;
	private String location;
	private @Id String _id;
	
	public Data(double value,String location,String id)
	{
		this.value=value;
		this.location=location;
		this.date=new Date();
		this._id=id;
	}
	
	public Data()
	{
		
	}
	
	/** Build a Data object with <code>_id</code> build by <code>@Id</code> annotation
	 * @param value as temperature value
	 * @param location
	 */
	public Data(double value,String location)
	{
		this.value=value;
		this.location=location;
		this.date=new Date();
	}
	
	public Data(double value,String location,Date d)
	{
		this.value=value;
		this.location=location;
		this.date=d;
	}
	
	public Data(double value,String location,Date d,String id)
	{
		this.value=value;
		this.location=location;
		this.date=d;
		this._id=id;
	}
	
	protected static Data getEmpty(String id,String location)
	{
		return new Data(0, location, id);
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("id:"+_id+" - ");
		sb.append("location:"+location+" - ");
		sb.append("date:"+date.toString()+" - ");
		sb.append("value:"+date.toString());
		return sb.toString();
	}
}