package com.stacksync.log_server.db.postgresql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.stacksync.log_server.db.ComputerDAO;
import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Computer;

public class PostgresqlComputerDAO extends PostgresqlDAO implements ComputerDAO {

	private static final Logger logger = Logger.getLogger(PostgresqlComputerDAO.class.getName());

	public PostgresqlComputerDAO(Connection connection) {
		super(connection);
	}

	@Override
	public void add(Computer computer) throws DAOException {
		
		Object[] values = {computer.getName()};
		
		String query = "INSERT INTO computer (name) VALUES (?)";
		
		Long id = executeUpdate(query, values);
		
		if (id != null) {
			logger.debug("New computer with id "+id);
			computer.setId(id);
		}
		
	}

	@Override
	public Computer findByName(String name) throws DAOException {
		
		Object[] values = {name};
		Computer computer = null;
		
		String query = "SELECT * FROM computer WHERE name = ?";
		
		ResultSet resultSet = executeQuery(query, values);
		
		try {
			if (resultSet.next()) {
				computer = mapComputer(resultSet);
			}
		} catch (SQLException e) {
			
		}
		
		return computer;
	}
	
	private Computer mapComputer(ResultSet resultSet) throws SQLException {

		Computer computer = new Computer();
		computer.setId(resultSet.getLong("id"));
		computer.setName(resultSet.getString("name"));

		return computer;
	}

}
