package com.marco.temperaturecenter.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.temperaturecenter.db.data.location.Location;
import com.marco.temperaturecenter.db.location.LocationRepo;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/location")
public class LocationController {

	
	@Autowired
	private Logger logger;
	
	@Autowired // writing data on "location db"
	private LocationRepo locationDb;
	
	
    @GetMapping("/all")
    public List<Location> getAllLocation() {
    	logger.info("Get all stored location");
    	return locationDb.findAll(); 	
    }
    
    @DeleteMapping("/all")
    public void deleteAllLocation() {
    	logger.info("Deleting all stored location");
    	locationDb.deleteAll(); 	
    }
}