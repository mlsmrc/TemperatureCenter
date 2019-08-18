package com.marco.temperaturecenter.db.temperature;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marco.temperaturecenter.db.data.temperature.TemperatureCurrentValue;

public interface TemperatureCurrentRepo extends MongoRepository<TemperatureCurrentValue, String> {
}