package com.stacksync.log_server.db.postgresql;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.stacksync.log_server.db.LogDAO;
import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Log;

public class PostgresqlLogDAO extends PostgresqlDAO implements LogDAO {

	private static final Logger logger = Logger.getLogger(PostgresqlLogDAO.class.getName());

	public PostgresqlLogDAO(Connection connection) {
		super(connection);
	}

	@Override
	public void add(Log log) throws DAOException {
		Object[] values = {log.getReport().getId(), new java.sql.Timestamp(log.getLogDate().getTime()), log.getPriority(),
				log.getThread(), log.getFilePackage(), log.getFileName(), log.getLine(),
				log.getMessage()};
		
		String query = "INSERT INTO logs (report_id, client_date, priority, thread," +
				"package, file_name, file_line, message) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		Long id = executeUpdate(query, values);
		
		if (id != null) {
			logger.debug("New log with id "+id);
			log.setId(id);
		}
		
	}

	@Override
	public void update(Log log) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
