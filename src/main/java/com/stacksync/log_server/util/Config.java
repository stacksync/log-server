package com.stacksync.log_server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Config {

	private static Properties properties;
	private static final Logger logger = Logger.getLogger(Config.class.getName());

	public static void loadProperties() throws IOException {
		logger.warn(String.format("Config argument not passed, will use default config file: '%s'", Constants.DEFAULT_CONFIG_FILE));

		loadProperties(Constants.DEFAULT_CONFIG_FILE);
	}

	public static void loadProperties(String configFileUrl) throws IOException {

		logger.info(String.format("Loading config file: '%s'", configFileUrl));

		URL log4jResource = Config.class.getResource("/log4j.xml");
		DOMConfigurator.configure(log4jResource);

		properties = new Properties();

		properties.load(new FileInputStream(configFileUrl));

		validateProperties();

		logger.info("Configuration file loaded");

		displayConfiguration();
	}

	public static Properties getProperties() {
		return properties;
	}

	private static void validateProperties() {
		checkProperty(Constants.PROP_DATASOURCE, Constants.DEFAULT_DATASOURCE);

		// Database
		checkProperty(Constants.PROP_POSTGRESQL_HOST, Constants.DEFAULT_POSTGRESQL_HOST);
		checkProperty(Constants.PROP_POSTGRESQL_PORT, Constants.DEFAULT_POSTGRESQL_PORT);
		checkProperty(Constants.PROP_POSTGRESQL_DATABASE, Constants.DEFAULT_POSTGRESQL_DATABASE);
		checkProperty(Constants.PROP_POSTGRESQL_USERNAME, Constants.DEFAULT_POSTGRESQL_USERNAME);
		checkProperty(Constants.PROP_POSTGRESQL_PASSWORD, Constants.DEFAULT_POSTGRESQL_PASSWORD);
		checkProperty(Constants.PROP_LOGS_PATH, Constants.DEFAULT_LOGS_PATH);
		checkProperty(Constants.PROP_LOGS_PROCESSED, Constants.DEFAULT_LOGS_PROCESSED);
	}

	private static void checkProperty(String key, Object defaultValue) {
		if (!properties.containsKey(key)) {
			logger.warn(String.format("Property '%s' not found, using default value: '%s'", key, defaultValue.toString()));
			properties.setProperty(key, defaultValue.toString());
		}
	}

	private static void displayConfiguration() {
		logger.info("Displaying configuration:");

		Enumeration<?> e = properties.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			logger.info(String.format("%s : %s", key, properties.getProperty(key)));
		}
	}

	public static String getDatasource() {
		return properties.getProperty(Constants.PROP_DATASOURCE);
	}

	public static String getPostgresqlHost() {
		return properties.getProperty(Constants.PROP_POSTGRESQL_HOST);
	}

	public static Integer getPostgresqlPort() {
		return Integer.parseInt(properties.getProperty(Constants.PROP_POSTGRESQL_PORT));
	}

	public static String getPostgresqlDatabase() {
		return properties.getProperty(Constants.PROP_POSTGRESQL_DATABASE);
	}

	public static String getPostgresqlUsername() {
		return properties.getProperty(Constants.PROP_POSTGRESQL_USERNAME);
	}

	public static String getPostgresqlPassword() {
		return properties.getProperty(Constants.PROP_POSTGRESQL_PASSWORD);
	}

	public static Integer getPostgresqlInitialConns() {
		return Integer.parseInt(properties.getProperty(Constants.PROP_POSTGRESQL_INITIAL_CONNS, Constants.DEFAULT_POSTGRESQL_INITIAL_CONNS));
	}

	public static Integer getPostgresqlMaxConns() {
		return Integer.parseInt(properties.getProperty(Constants.PROP_POSTGRESQL_MAX_CONNS, Constants.DEFAULT_POSTGRESQL_MAX_CONNS));
	}

	public static String getLogsPaths() {
		return properties.getProperty(Constants.PROP_LOGS_PATH);
	}
	
	public static String getLogsProcessed() {
		return properties.getProperty(Constants.PROP_LOGS_PROCESSED);
	}

}
