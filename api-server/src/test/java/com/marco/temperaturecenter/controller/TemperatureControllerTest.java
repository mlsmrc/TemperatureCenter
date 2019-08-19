package com.marco.temperaturecenter.controller;

import org.junit.jupiter.api.Assertions;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.marco.temperaturecenter.controller.TemperatureController;

@SpringBootTest
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class) //for TemperatureController beans scope
public class TemperatureControllerTest {

	private static String TEST_ROOM = "test_room";
	private static String TEST_ROOM2 = "test_room2";
	
	Logger testLogger = Logger.getLogger(TemperatureControllerTest.class.getName());
	
	@Autowired
	TemperatureController t;
	
	@Test
	/** Testing function 
	 *  - deleteAllForRoom W/ insertion
	 */
	public void deleteAllForRoom()
	{
		t.deleteAllForRoom(TEST_ROOM);
		Assertions.assertEquals(t.getAllForRoom(TEST_ROOM).size(), 0);
	}
	
	@Test
	/** Testing function 
	 *  - deleteAll W/ insertion
	 */
	public void deleteAll()
	{
		t.deleteAll();
		Assertions.assertEquals(t.getAll().size(), 0);
	}
	
	@Test
	/** Testing function 
	 *  - deleteAllForRoom W/O insertion
	 */
	public void deleteAllForRoom2()
	{
		testLogger.info("Testing deleteAllForRoom W/O insertion");
		t.deleteAllForRoom(TEST_ROOM);
		t.postTemperature(TEST_ROOM, "20.0");
		t.postTemperature(TEST_ROOM, "21.0");
		Assertions.assertEquals(t.getAllForRoom(TEST_ROOM).size(), 2);
		t.deleteAllForRoom(TEST_ROOM);
		Assertions.assertEquals(t.getAllForRoom(TEST_ROOM).size(), 0);
	}
	
	@Test
	/** Testing function 
	 *  - deleteAll W/O insertion
	 */
	public void deleteAll2()
	{
		t.deleteAll();
		testLogger.info("Testing deleteAllForRoom W/O insertion");
		t.postTemperature(TEST_ROOM, "20.0");
		t.postTemperature(TEST_ROOM2, "21.0");
		Assertions.assertEquals(t.getAll().size(), 2);
		t.deleteAll();
		Assertions.assertEquals(t.getAll().size(), 0);
	}
	
	@Test
	/** Testing function
	 *  - postTemperature W/ location
	 */
	public void postTemperature() {
		testLogger.info("Testing postTemperature");
		t.deleteAllForRoom(TEST_ROOM);
	    t.postTemperature(TEST_ROOM, "20.0");
	    Assertions.assertEquals(t.getLastTemperature(TEST_ROOM).getValue(), 20.0);
	    Assertions.assertEquals(t.getAllForRoom(TEST_ROOM).size(), 1);
	}
	
	@Test
	/** Testing function
	 *  - getAll W/O location
	 */
	public void postTemperature2() {
		testLogger.info("Testing postTemperature");
		t.deleteAllForRoom(TEST_ROOM);
		t.deleteAllForRoom(TEST_ROOM2);
	    t.postTemperature(TEST_ROOM, "20.0");
	    t.postTemperature(TEST_ROOM2, "20.0");
	    Assertions.assertEquals(t.getAll().size(), 2);
	    Assertions.assertEquals(t.getAllForRoom(TEST_ROOM).size(), 1);
	    Assertions.assertEquals(t.getAllForRoom(TEST_ROOM2).size(), 1);
	}
	
	@Test
	/** Testing function
	 *  - postTodayTemperature
	 */
	public void postTodayTemperature() {
		testLogger.info("Testing postTodayTemperature");
		t.deleteAllForRoom(TEST_ROOM);
	    t.postTemperature(TEST_ROOM, "20.0");
	    Assertions.assertEquals(t.getLastTemperature(TEST_ROOM).getValue(),20,0);
	    t.postTemperature(TEST_ROOM, "21.0");
	    Assertions.assertEquals(t.getLastTemperature(TEST_ROOM).getValue(),21,0);
	}
	
	@Test
	/** Testing function
	 *  - getTemperatureAvg
	 */
	public void getTemperatureAvg() {
		testLogger.info("Testing postTodayTemperature");
		t.deleteAllForRoom(TEST_ROOM);
	    t.postTemperature(TEST_ROOM, "20.5");
	    t.postTemperature(TEST_ROOM, "21.5");
	    t.postTemperature(TEST_ROOM, "24.0");
	    Assertions.assertEquals(t.getAllForRoom(TEST_ROOM).size(),3);
	    Assertions.assertEquals(t.getTemperatureAvg(TEST_ROOM).getValue(),22);
	}
	
}