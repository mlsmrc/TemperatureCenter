package com.marco.temperaturecenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marco.temperaturecenter.db.data.temperature.TemperatureCurrentValue;
import com.marco.temperaturecenter.db.data.temperature.TemperatureMonthlyValue;
import com.marco.temperaturecenter.db.data.temperature.TemperatureDailyValue;
import com.marco.temperaturecenter.db.temperature.*;

import static com.marco.temperaturecenter.common.Constants.*;
import static org.junit.Assert.assertThat;

@RestController
@RequestMapping("/api/temperature")
public class TemperatureController {

	
	@Autowired
	private Logger logger;
	
	@Autowired // writing json on "today_temperature db"
	private TemperatureDailyRepo dailyDb;
	
	@Autowired // writing json on "last_temperature db"
	private TemperatureCurrentRepo lastTempDb;
	
	
    @GetMapping("/{location}/avg")
    public TemperatureMonthlyValue getTemperatureAvg(@PathVariable String location) {
    	logger.info("Calculate the avg for all data related to location: "+location);
    	double v=0;
    	int counter=0;
        for (TemperatureDailyValue tv :getAllForRoom(location))
        {
        	v+=tv.getValue();
        	counter++;
        }
        
        return new TemperatureMonthlyValue((double)(v/(double)counter), location); 	
    }
    
    @RequestMapping(value = "/{location}/all", method = RequestMethod.GET)
    public List<TemperatureDailyValue> getAllForRoom(@PathVariable String location)
    {
    	logger.info("Requesting all temperature value for location: "+location);
    	List<TemperatureDailyValue> list = new ArrayList<TemperatureDailyValue>();
		for(TemperatureDailyValue t : dailyDb.findAll())
			if(t.getLocation().equals(location))
				list.add(t);
		return list;
    }
    
    @RequestMapping(value = "/{location}/all", method = RequestMethod.DELETE)
    public void deleteAllForRoom(@PathVariable String location)
    {
    	logger.info("Removing all temperature value for location: "+location);
    	dailyDb.findAll();
		for(TemperatureDailyValue t : dailyDb.findAll())
		{
			if(t.getLocation().equals(location))
			{
				logger.info("Removing "+t.toString());
				dailyDb.deleteById(t.get_id());
			}
		}
    }
    
    @RequestMapping(value = "/{location}/last", method = RequestMethod.GET)
    public TemperatureCurrentValue getLastTemperature(@PathVariable String location)
    {
    	Optional<TemperatureCurrentValue> t = lastTempDb.findById(TEMPERATURE_ID);
    	return t.isPresent() ? t.get() : TemperatureCurrentValue.getEmpty(location);
    	
    }
    
    @RequestMapping(value = "/{location}", method = RequestMethod.POST)
    public void postTemperature(@PathVariable String location, @RequestBody String value)
    {
    	logger.info("Adding "+value+" temperature for location '"+location+"'");
    	dailyDb.insert(new TemperatureDailyValue(Double.parseDouble(value), location));
    	postTodayTemperature(location, value);
    }
    
    private void postTodayTemperature(String location,String value)
    {
    	lastTempDb.deleteById(TEMPERATURE_ID);
    	lastTempDb.insert(new TemperatureCurrentValue(Double.parseDouble(value), location));
    }
    
    @Test
    public void test()
    {
    	int i;
    	
    	i=0;
    	
    	Assert.assertTrue(i==1);
    }
    
}