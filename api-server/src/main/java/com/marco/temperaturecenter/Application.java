package com.marco.temperaturecenter;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    
	@Autowired
	Logger logger;
	
    public static void main(String[] args) {
    	
    	Logger logger = Logger.getLogger(Application.class.getName());
        SpringApplication.run(Application.class, args);
        logger.info("Temperature Server API - Server ready");
    }

    
}
