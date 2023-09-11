package com.shoeshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableAutoConfiguration

@SpringBootApplication
public class ShoeShelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeShelfApplication.class, args);
	}

}
