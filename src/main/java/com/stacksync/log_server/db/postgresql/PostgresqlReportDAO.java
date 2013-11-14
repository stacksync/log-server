package com.stacksync.log_server.db.postgresql;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.stacksync.log_server.db.ReportDAO;
import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Report;

public class PostgresqlReportDAO extends PostgresqlDAO implements ReportDAO {

	private static final Logger logger = Logger.getLogger(PostgresqlReportDAO.class.getName());

	public PostgresqlReportDAO(Connection connection) {
		super(connection);
	}

	@Override
	public void add(Report report) throws DAOException {
		Object[] values = {new java.sql.Timestamp(report.getUploadDate().getTime()), report.getComputer().getId()};
		
		String query = "INSERT INTO report (upload_date, computer_id) VALUES (?, ?)";
		
		Long id = executeUpdate(query, values);
		
		if (id != null) {
			logger.debug("New report with id "+id);
			report.setId(id);
		}
		
	}

	@Override
	public void update(Report report) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DAOException {
		// TODO Auto-generated method stub
		
	}



}
