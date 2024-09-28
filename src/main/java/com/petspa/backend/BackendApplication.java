package com.petspa.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	private Environment environment;

	// Mã ANSI cho màu sắc
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) {
		//System.out.print("\033[H\033[2J");
		System.out.println(ANSI_CYAN + "Application is starting/restarting..." + ANSI_RESET);
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run() {
		return args -> {
			String port = environment.getProperty("server.port");
			System.out.println(ANSI_GREEN + "Server running on " + ANSI_BLUE + "http://localhost:" + port + ANSI_RESET);
			//show link to access swagger
			System.out.println(ANSI_GREEN + "Swagger UI: " + ANSI_BLUE + "http://localhost:" + port + "/swagger-ui.html" + ANSI_RESET);
			System.out.println(
					ANSI_WHITE + "Press " + ANSI_YELLOW + "`ctrl + c`" + ANSI_WHITE + " to stop!" + ANSI_RESET);
		};
	}
}
