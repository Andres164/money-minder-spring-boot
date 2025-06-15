package com.baio.money_minder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoneyMinderApplication {
	private static final Logger log = LoggerFactory.getLogger(MoneyMinderApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MoneyMinderApplication.class, args);
	}


}
