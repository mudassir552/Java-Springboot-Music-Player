package com.example.demo.ConfigDetails;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties("accounts")
public record ConfigPropertiesDetails(String message,String name) {

	public String message() {
		return message;
	}

	public String name() {
		return name;
	}
 
	 
}
