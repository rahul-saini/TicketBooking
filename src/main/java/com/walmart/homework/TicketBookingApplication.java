package com.walmart.homework;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketBookingApplication {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource secondaryDataSource() {
	    return DataSourceBuilder.create().build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TicketBookingApplication.class, args);
	}
}
