package com.parser.jsonxmlparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class JsonXmlParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonXmlParserApplication.class, args);
	}

}
