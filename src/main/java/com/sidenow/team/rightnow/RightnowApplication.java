package com.sidenow.team.rightnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RightnowApplication {

	public static void main(String[] args) {
		SpringApplication.run(RightnowApplication.class, args);
	}

}
