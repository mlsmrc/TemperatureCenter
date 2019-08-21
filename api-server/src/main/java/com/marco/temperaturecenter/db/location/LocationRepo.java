package com.marco.temperaturecenter.db.location;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marco.temperaturecenter.db.data.location.Location;

public interface LocationRepo extends MongoRepository<Location, String> {
}