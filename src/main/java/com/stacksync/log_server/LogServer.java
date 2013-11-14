package com.stacksync.log_server;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.stacksync.log_server.model.Computer;
import com.stacksync.log_server.model.Log;
import com.stacksync.log_server.model.Report;
import com.stacksync.log_server.util.ArgsOptions;
import com.stacksync.log_server.util.Config;
import com.stacksync.log_server.db.ComputerDAO;
import com.stacksync.log_server.db.ConnectionPoolFactory;
import com.stacksync.log_server.db.LogDAO;
import com.stacksync.log_server.db.ReportDAO;
import com.stacksync.log_server.exceptions.DAOConfigurationException;
import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.db.ConnectionPool;
import com.stacksync.log_server.db.DAOFactory;

public class LogServer {

	private static final Logger logger = Logger.getLogger(LogServer.class.getName());
	
	private ConnectionPool pool = null;
	private Connection connection;
	
	private ComputerDAO computerDAO;
	private ReportDAO reportDAO;
	private LogDAO logDAO;
	
	public LogServer() throws SQLException {
		createDBPool();
		this.connection = pool.getConnection();
		
		DAOFactory factory = new DAOFactory(Config.getDatasource());
		this.computerDAO = factory.getComputerDAO(connection);
		this.reportDAO = factory.getReportDAO(connection);
		this.logDAO = factory.getLogDAO(connection);
	}
	
	private void createDBPool() {
		try {

			String datasource = Config.getDatasource();
			pool = ConnectionPoolFactory.getConnectionPool(datasource);

			// it will try to connect to the DB, throws exception if not possible.
			Connection conn = pool.getConnection();
			conn.close();

			logger.info("Connection to database succeded");
		} catch (DAOConfigurationException e) {
			logger.error("Connection to database failed. Reason: " + e.getMessage());
			System.exit(3);
		} catch (SQLException e) {
			logger.error("Connection to database failed. Reason: " + e.getMessage());
			System.exit(4);
		}
	}
	
	public void processLogs() throws IOException {
		
		logger.info("Start to process logs.");
		String path = Config.getLogsPaths();
		String processedFolder = Config.getLogsProcessed();
		
		File logsFolder = new File(path);
		String[] logs = logsFolder.list();
		
		File saveFolder = new File(path + "/" + processedFolder);
		if (!saveFolder.exists()) {
			saveFolder.mkdir();
		}
		
		LogReader reader;
		for (String logFile : logs){
			
			logger.info("Processing log: " + logFile);
			try {
				
				if (logFile.equals(processedFolder))
					continue;
				
				reader = new LogReader(path+"/"+logFile);
				Computer computer = reader.getComputer();
				Computer aux = this.computerDAO.findByName(computer.getName());
				if (aux == null) {
					this.computerDAO.add(computer);
					logger.debug("Added computer to DB: " + computer.toString());
				} else {
					computer = aux;
					logger.debug("Computer already exists: " + computer.toString());
				}
				
				Report report = reader.getReport();
				report.setComputer(computer);
				this.reportDAO.add(report);
				logger.debug("Added report to DB: " + report.toString());
				
				while (reader.hasNext()) {
					Log info = reader.next();
					if (info == null) {
						continue;
					}
					info.setReport(report);
					this.logDAO.add(info);
				}
				
				reader.remove();
				
				moveFile(path, path +"/"+ processedFolder, logFile);
				
			} catch (DAOException e){
				logger.error(e);
				//TODO save to error list??
			}
		}
		
		logger.info("All logs processed.");
	}
	
	private void moveFile(String from, String to, String logFile) {
		
		File file = new File(from+"/"+logFile);
		file.renameTo(new File(to+"/"+logFile));
	}

	public static void main(String[] args) throws IOException {
		
		ArgsOptions.readAndApplyOptions(args);
		
		try {
			logger.info("Initializing the StackSync log-server core");
			LogServer logServer = new LogServer();
			logServer.processLogs();
		} catch (SQLException ex) {
			logger.error(ex);
		}
	}

}
