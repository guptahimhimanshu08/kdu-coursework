package com.kdu.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the GraphQL Spring Boot application.
 * Configures and launches the application with all necessary components.
 */
@SpringBootApplication
public class GraphqlApplication {

	private static final Logger logger = LoggerFactory.getLogger(GraphqlApplication.class);

	/**
	 * Application entry point.
	 * 
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		logger.info("Starting GraphQL Application...");
		SpringApplication.run(GraphqlApplication.class, args);
		logger.info("GraphQL Application started successfully");
	}

}
