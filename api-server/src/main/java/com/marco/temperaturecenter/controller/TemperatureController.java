package com.marco.temperaturecenter.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marco.temperaturecenter.db.TemperatureRepository;
import com.marco.temperaturecenter.db.TemperatureValue;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

	
	@Autowired
	private Logger logger;
	
	@Autowired
	TemperatureRepository tempDb;
	
	
    @GetMapping("/")
    public List<TemperatureValue> allDayTemperature() {
    	logger.info("Requesting all temperature value");
        return tempDb.findAll();
    }
    
    @RequestMapping(value = "/{room}", method = RequestMethod.POST)
    public void postTemperature(@PathVariable String room, @RequestBody String value)
    {
    	logger.info("Adding "+value+" temperature for room '"+room+"'");
    	tempDb.insert(new TemperatureValue(Double.parseDouble(value), room));
    }
    
    
    @GetMapping("/error")
    public String errorApi()
    {
    	return "404";
    }  
    
}