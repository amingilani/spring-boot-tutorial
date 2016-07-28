package myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MyappApplication {

public static void main(String[] args) {
								SpringApplication.run(MyappApplication.class, args);
}

// private static final Logger log = LoggerFactory.getLogger(MyappApplication.class);
//
// @Bean
// public CommandLineRunner demo(UserRepository repository) {
// 		return (args) -> {
// 				// save a couple of users
// 				repository.save(new User("user1", "pass1"));
// 				repository.save(new User("user2", "pass2"));
//
// 				// fetch all customers
// 				log.info("Users found with findAll():");
// 				log.info("-------------------------------");
// 				for (User user : repository.findAll()) {
// 												log.info(user.toString());
// 				}
// 				log.info("");
//
// 				// fetch an individual user by ID
// 				User user = repository.findOne(1L);
// 				log.info("User found with findOne(1L):");
// 				log.info("--------------------------------");
// 				log.info(user.toString());
// 				log.info("");
//
// 				// fetch customers by last name
// 				log.info("User found with findByUserName('user1'):");
// 				log.info("--------------------------------------------");
// 				for (User user1 : repository.findByUserName("user1")) {
// 												log.info(user1.toString());
// 				}
// 				log.info("");
// 		};
// }
}
