package com.intuit.businessprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class BusinessprofileApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessprofileApplication.class, args);
	}

}
