package com.marco.temperaturecenter.db.temperature;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marco.temperaturecenter.db.data.temperature.TemperatureMonthlyValue;

public interface TemperatureMonthlyRepo extends MongoRepository<TemperatureMonthlyValue, String> {

}
