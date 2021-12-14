package com.example.jwtapp;

import com.example.jwtapp.models.AppUser;
import com.example.jwtapp.models.Message;
import com.example.jwtapp.models.Role;
import com.example.jwtapp.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.ArrayList;

@SpringBootApplication
public class JwtAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (UserService service){
		return args -> {
			service.saveRole(new Role("ROLE_ADMIN"));
			service.saveRole(new Role("ROLE_USER"));

			service.saveUser(new AppUser("admin", "1111", new ArrayList<>(), new ArrayList<>()));
			service.saveUser(new AppUser("user", "1111", new ArrayList<>(), new ArrayList<>()));

			service.addRoleToUser("admin", "ROLE_ADMIN");
			service.addRoleToUser("admin", "ROLE_USER");
			service.addRoleToUser("user", "ROLE_USER");

			service.saveMessage(new Message("admin", "some text"));
			service.saveMessage(new Message("admin", "text"));
			service.saveMessage(new Message("admin", "new text"));
			service.saveMessage(new Message("admin", "some new text"));
			service.saveMessage(new Message("admin", "another text"));
			service.saveMessage(new Message("admin", "another some text"));
			service.saveMessage(new Message("admin", "another new text"));
			service.saveMessage(new Message("admin", "another some new text"));
		};
	}
}
