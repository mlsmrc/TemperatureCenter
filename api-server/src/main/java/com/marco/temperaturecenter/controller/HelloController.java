package com.marco.temperaturecenter.controller;

import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {
    
    @GetMapping("/server")
    public Map<String,String> itsTheServer()
    {
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("status",HttpStatus.ACCEPTED.toString());
    	map.put("message","It's the domotica server");
		return map;
    }
}
