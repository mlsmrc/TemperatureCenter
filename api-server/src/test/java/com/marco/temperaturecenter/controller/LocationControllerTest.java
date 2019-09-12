package com.marco.temperaturecenter.controller;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.marco.temperaturecenter.controller.TemperatureController;
import com.marco.temperaturecenter.db.data.location.Location;

import static com.marco.temperaturecenter.common.Constants.*;

@SpringBootTest
@ContextConfiguration

/* for TemperatureController beans scope */
@RunWith(SpringJUnit4ClassRunner.class) //

/* application properties import for testing purpose */
@TestPropertySource(locations="classpath:application.test.properties") 
public class LocationControllerTest {
	
	@Autowired
	Logger logger;
	
	@Autowired
	LocationController lc;
	
	@Autowired
	TemperatureController tc;
	
	
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
	
	@Test
	/** Testing function 
	 *  - getAllLocation values
	 */
	public void getAllLocation3()
	{
		tc.deleteAll();
		lc.deleteAllLocation();
		tc.postTemperature(TEST_ROOM, "20.0");
		tc.postTemperature(TEST_ROOM2, "21.0");
		
		List<String> locValues = stringFromLocation(lc.getAllLocation());
		
		Assertions.assertEquals(locValues.contains(TEST_ROOM), true);
		Assertions.assertEquals(locValues.contains(TEST_ROOM2), true);
		Assertions.assertEquals(locValues.contains(TEST_ROOM3), false);
	}
	
	private List<String> stringFromLocation(List<Location> locations)
	{
		List<String> values = new ArrayList<String>();
		for(Location l : locations)
		{
			values.add(l.get_id());
			System.out.println(l.get_id());
		}
		return values;
		
	}
}