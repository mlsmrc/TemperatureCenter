package com.marco.temperaturecenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.temperaturecenter.db.value.TemperatureValue;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

	private AtomicLong uid = new AtomicLong();
    @GetMapping("/")
    public String info() {
        return "Temperature";
    }
    
    @GetMapping("/{room}")
    public List<Map<String,String>> room(@PathVariable String room) {
    	List<Map<String,String>> lt = new ArrayList<Map<String,String>>();
        lt.add(TemperatureValue.value(uid.incrementAndGet(), 30.0, room));
        lt.add(TemperatureValue.value(uid.incrementAndGet(), 20.0, room));
        
        return lt;
    }
    
    @GetMapping("/{room}/last")
    public Map<String,String> roomLast(@PathVariable String room) {
        return TemperatureValue.value(uid.incrementAndGet(), 30.0, room);
    }
}
