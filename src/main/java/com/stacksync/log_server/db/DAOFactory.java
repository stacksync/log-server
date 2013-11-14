package com.stacksync.log_server.db;

import java.sql.Connection;

import com.stacksync.log_server.db.postgresql.PostgresqlComputerDAO;
import com.stacksync.log_server.db.postgresql.PostgresqlLogDAO;
import com.stacksync.log_server.db.postgresql.PostgresqlReportDAO;

public class DAOFactory {

	private String type;

	public DAOFactory(String type) {
		this.type = type;
	}

	public ReportDAO getReportDAO(Connection connection) {
		return new PostgresqlReportDAO(connection);
	}
	
	public ComputerDAO getComputerDAO(Connection connection) {
		return new PostgresqlComputerDAO(connection);
	}
	
	public LogDAO getLogDAO(Connection connection) {
		return new PostgresqlLogDAO(connection);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
