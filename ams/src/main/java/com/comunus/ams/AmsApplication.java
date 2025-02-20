package com.comunus.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PropertySources({@PropertySource(value = "file:${catalina.base}/ams.properties", ignoreResourceNotFound = true)})
public class AmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsApplication.class, args);
		
		System.out.println(":::::::::LLLLKKKKKKKKKKK");
	}

}
