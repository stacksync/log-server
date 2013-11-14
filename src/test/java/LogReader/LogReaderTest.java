package LogReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.stacksync.log_server.LogReader;
import com.stacksync.log_server.db.ComputerDAO;
import com.stacksync.log_server.db.ConnectionPool;
import com.stacksync.log_server.db.ConnectionPoolFactory;
import com.stacksync.log_server.db.DAOFactory;
import com.stacksync.log_server.db.LogDAO;
import com.stacksync.log_server.db.ReportDAO;
import com.stacksync.log_server.exceptions.DAOConfigurationException;
import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Computer;
import com.stacksync.log_server.model.Log;
import com.stacksync.log_server.model.Report;
import com.stacksync.log_server.util.Config;

public class LogReaderTest {
	
	public static ConnectionPool pool;

	@BeforeClass
	public static void createDBPool() throws IOException {
		try {
	
			Config.loadProperties();
			pool = ConnectionPoolFactory.getConnectionPool("postgresql");
	
			// it will try to connect to the DB, throws exception if not possible.
			Connection conn = pool.getConnection();
			conn.close();

		} catch (DAOConfigurationException e) {
			System.out.println("Connection to database failed. Reason: " + e.getMessage());
			System.exit(3);
		} catch (SQLException e) {
			System.out.println("Connection to database failed. Reason: " + e.getMessage());
			System.exit(4);
		}
	}
	
	@Test
	public void readLog() throws DAOException, SQLException, IOException {
		
		createDBPool();
		Connection connection = pool.getConnection();
		
		DAOFactory factory = new DAOFactory(Config.getDatasource());
		ComputerDAO computerDAO = factory.getComputerDAO(connection);
		ReportDAO reportDAO = factory.getReportDAO(connection);
		LogDAO logDAO = factory.getLogDAO(connection);
	
		LogReader reader = new LogReader("/home/cotes/ast_cotes201309021317__2013-09-10-10-00-17.gz");

		Computer computer = reader.getComputer();
		Computer aux = computerDAO.findByName(computer.getName());
		if (aux == null) {
			computerDAO.add(computer);
		} else {
			computer = aux;
		}
		
		Report report = reader.getReport();
		report.setComputer(computer);
		reportDAO.add(report);
		
		while (reader.hasNext()) {
			Log info = reader.next();
			info.setReport(report);
			logDAO.add(info);
		}
		
	}

}
