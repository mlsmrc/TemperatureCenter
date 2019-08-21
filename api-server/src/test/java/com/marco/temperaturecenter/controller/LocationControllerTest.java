package com.marco.temperaturecenter.controller;

import org.junit.jupiter.api.Assertions;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.marco.temperaturecenter.common.MongoTest;
import com.marco.temperaturecenter.controller.TemperatureController;
import com.marco.temperaturecenter.db.data.location.Location;

import static com.marco.temperaturecenter.common.Constants.*;

@SpringBootTest
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class) //for TemperatureController beans scope
public class LocationControllerTest {
	
	@Autowired
	Logger logger;
	
	@Autowired
	LocationController lc;
	
	@Autowired
	TemperatureController tc;
	
	@Autowired
	MongoTest mongo;
	
	@Before
	public void before()
	{
		mongo.start();
	}
	
	
	@Test
	/** Testing function 
	 *  - getAllLocation W/ single insertion
	 */
	public void getAllLocation()
	{
		tc.deleteAll();
		lc.deleteAllLocation();
		tc.postTemperature(TEST_ROOM, "20.0");
		logger.info(String.valueOf(lc.getAllLocation()));
		logger.info(String.valueOf(lc.getAllLocation().contains(new Location(TEST_ROOM))));
		Assertions.assertEquals(lc.getAllLocation().contains(new Location(TEST_ROOM)),true);
	}
	
	@Test
	/** Testing function 
	 *  - getAllLocation W/ single insertion
	 */
	public void getAllLocation2()
	{
		tc.deleteAll();
		lc.deleteAllLocation();
		tc.postTemperature(TEST_ROOM, "20.0");
		Assertions.assertEquals(lc.getAllLocation().contains(new Location(TEST_ROOM)),true);
		tc.postTemperature(TEST_ROOM2, "21.0");
		Assertions.assertEquals(lc.getAllLocation().contains(new Location(TEST_ROOM2)),true);
		Assertions.assertEquals(lc.getAllLocation().contains(new Location(TEST_ROOM)),true);
	}
	
	@After
	public void after()
	{
		mongo.stop();
	}
}
