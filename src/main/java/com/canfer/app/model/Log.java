package com.canfer.app.model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class Log {

	private Log() {
		throw new IllegalStateException("Utility class");
	}

	public static void activity(String message, String empresa, String concepto) {

		final Level activity = Level.forName("ACTIVIDAD", 150);
		ThreadContext.put("empresa", empresa);
		ThreadContext.put("concepto", concepto);

		final Logger logger = LogManager.getLogger();

		logger.log(activity, message);

		// Clear the map
		ThreadContext.clearMap();

	}

	public static void falla(String message, String concepto) {

		final Level activity = Level.forName("FALLA", 140);
		ThreadContext.put("empresa", "NA");
		ThreadContext.put("concepto", concepto);

		final Logger logger = LogManager.getLogger();

		logger.log(activity, message);

		// Clear the map
		ThreadContext.clearMap();

	}
	
	public static void general(String message) {

		final Level activity = Level.forName("GENERAL", 300);

		final Logger logger = LogManager.getLogger();

		logger.log(activity, message);

	}
	
	

	public static void warn(String message) {

		final Logger logger = LogManager.getLogger();

		logger.warn(message);

	}

	public static void error(String message) {

		final Logger logger = LogManager.getLogger();

		logger.error(message);

	}
}