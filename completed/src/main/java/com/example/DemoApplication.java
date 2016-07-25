package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

public static void main(String[] args) {
								SpringApplication.run(DemoApplication.class, args);
}

@Bean
public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			// save a couple of users
			repository.save(new User("Jack", "Bauer"));
			repository.save(new User("Chloe", "O'Brian"));
			repository.save(new User("Kim", "Bauer"));
			repository.save(new User("David", "Palmer"));
			repository.save(new User("Michelle", "Dessler"));

			// fetch all users
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User user : repository.findAll()) {
											log.info(user.toString());
			}
			log.info("");

			// fetch an individual user by ID
			User user = repository.findOne(1L);
			log.info("User found with findOne(1L):");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

			// fetch users by last name
			log.info("User found with findByUserName('Bauer'):");
			log.info("--------------------------------------------");
			for (User bauer : repository.findByUserName("Bauer")) {
											log.info(bauer.toString());
			}
			log.info("");
		};
	}
}
