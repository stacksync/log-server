package com.stacksync.log_server.util;

public class Constants {

	/* PROPERTIES */
	public static final String PROP_DATASOURCE = "datasource";
	public static final String DEFAULT_CONFIG_FILE = "config.properties";
	public static final String PROP_LOGS_PATH = "logs.path";
	public static final String PROP_LOGS_PROCESSED = "logs.processed.folder";

	// PostgreSQL
	public static final String PROP_POSTGRESQL_HOST = "postgresql.host";
	public static final String PROP_POSTGRESQL_PORT = "postgresql.port";
	public static final String PROP_POSTGRESQL_DATABASE = "postgresql.database";
	public static final String PROP_POSTGRESQL_USERNAME = "postgresql.user";
	public static final String PROP_POSTGRESQL_PASSWORD = "postgresql.password";
	public static final String PROP_POSTGRESQL_INITIAL_CONNS = "postgresql.initial_cons";
	public static final String PROP_POSTGRESQL_MAX_CONNS = "postgresql.max_cons";

	/* GENERAL */
	public static final String DEFAULT_DATASOURCE = "postgresql";
	public static final String DEFAULT_LOGS_PATH = "/var/log/stacksync";
	public static final String DEFAULT_LOGS_PROCESSED = "processed";

	/* POSTGRESQL */
	public static final String DEFAULT_POSTGRESQL_HOST = "localhost";
	public static final String DEFAULT_POSTGRESQL_PORT = "5432";
	public static final String DEFAULT_POSTGRESQL_DATABASE = "logs";
	public static final String DEFAULT_POSTGRESQL_DRIVER = "org.postgresql.Driver";
	public static final String DEFAULT_POSTGRESQL_USERNAME = "stacksync";
	public static final String DEFAULT_POSTGRESQL_PASSWORD = "stacksync";
	public static final String DEFAULT_POSTGRESQL_INITIAL_CONNS = "1";
	public static final String DEFAULT_POSTGRESQL_MAX_CONNS = "100";
	
}
