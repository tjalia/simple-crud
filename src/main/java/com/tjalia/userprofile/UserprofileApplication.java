package com.tjalia.userprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan("com.tjalia.userprofile")
public class UserprofileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserprofileApplication.class, args);
	}

}
