package com.marco.temperaturecenter.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemperatureRepository extends MongoRepository<TemperatureValue, String> {
	
}