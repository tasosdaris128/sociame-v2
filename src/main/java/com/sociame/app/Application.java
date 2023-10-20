package com.sociame.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("Starting application...");

		var app = SpringApplication.run(Application.class, args);

		Runtime.getRuntime().addShutdownHook(new ShutdownHook(app));
	}

	private static class ShutdownHook extends Thread {

		private ConfigurableApplicationContext context;

		ShutdownHook(ConfigurableApplicationContext context) {
			this.context = context;
		}

		@Override
		public void run() {
			logger.info("Gracefully shutting down server. Goodbye!");
			context.stop();
		}

	}

}
