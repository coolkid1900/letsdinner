package org.hackthon.letsdinner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LetsdinnerApplication {
    private static Logger logger = LoggerFactory.getLogger(LetsdinnerApplication.class);
	public static void main(String[] args) {
		logger.info("Application starting...");
		SpringApplication.run(LetsdinnerApplication.class, args);
		logger.info("Application started.");
	}

}
