package main.java.com.excilys.computer.database.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class Connect {		
	final private static Logger logger = LogManager.getLogger(Connect.class);
	private final DataSource ds;
	
	public Connect(DataSource ds) {
		this.ds = ds;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = ds.getConnection();
		} catch (SQLException e1) {
			logger.fatal("Erreur de connexion a la BDD! erreur:" + e1);
		}
		return connection;
	}

	public void closeConnection(Connection connection) {    
        if (connection != null) {
        	try {
        		connection.close();
	        } catch (SQLException e1) {
	        	logger.fatal("Erreur de fermeture de la connexion a la BDD! erreur:" + e1);
	        }
        }
	}
}