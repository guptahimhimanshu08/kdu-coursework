package com.kdu.eventsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.kdu")
@EnableJpaRepositories(basePackages = "com.kdu.eventsphere.domain")
@EntityScan(basePackages = "com.kdu.eventsphere.domain")
@EnableJpaAuditing
public class EventsphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsphereApplication.class, args);
	}

}
