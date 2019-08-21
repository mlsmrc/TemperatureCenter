package com.marco.temperaturecenter.common;

import java.io.File;

import org.testcontainers.containers.DockerComposeContainer;

@SuppressWarnings("rawtypes")
public class MongoTest extends DockerComposeContainer{

	public MongoTest()
	{
		super(new File("src/test/resources/mongodb/docker-compose.yml"));
		withExposedService("mongo", 27017);
	}
}
