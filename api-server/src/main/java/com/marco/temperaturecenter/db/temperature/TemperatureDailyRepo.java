package com.marco.temperaturecenter.db.temperature;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marco.temperaturecenter.db.data.temperature.TemperatureDailyValue;



public interface TemperatureDailyRepo extends MongoRepository<TemperatureDailyValue, String> {
}