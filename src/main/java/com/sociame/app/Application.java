package com.sociame.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		log.info("Starting application...");

		var app = SpringApplication.run(Application.class, args);

		Runtime.getRuntime().addShutdownHook(new ShutdownHook(app));
	}

	private static class ShutdownHook extends Thread {

		private final ConfigurableApplicationContext context;

		ShutdownHook(ConfigurableApplicationContext context) {
			this.context = context;
		}

		@Override
		public void run() {
			log.info("Gracefully shutting down server. Goodbye!");
			context.stop();
		}

	}

}
