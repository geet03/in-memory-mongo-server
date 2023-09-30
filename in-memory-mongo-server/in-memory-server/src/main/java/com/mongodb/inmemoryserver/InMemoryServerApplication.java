package com.mongodb.inmemoryserver;

import com.mongodb.inmemoryserver.config.EnableMongoTestServer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableMongoTestServer
public class InMemoryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InMemoryServerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
