package com.stacksync.log_server.db;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionPool {

	public abstract Connection getConnection() throws SQLException;

}
