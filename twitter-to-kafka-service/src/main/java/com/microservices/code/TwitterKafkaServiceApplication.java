package com.microservices.code;
 
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.microservices.*")
public class TwitterKafkaServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TwitterKafkaServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");
    }
}