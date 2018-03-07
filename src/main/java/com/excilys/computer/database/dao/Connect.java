package main.java.com.excilys.computer.database.dao;

import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class Connect {		
	private static String url;
	private static String password;
	private static String username;
	private static String driver;

	final private static Logger logger = LogManager.getLogger(Connect.class);	
	
	private static Connection connection = null;
	private static HikariDataSource ds = new HikariDataSource();
	private static Connect connect = null;

	private Connect() {
		ResourceBundle bundle = ResourceBundle.getBundle("connect");
		url = bundle.getString("url");
		username = bundle.getString("username");
		password = bundle.getString("password");
		driver = bundle.getString("driver");
		
		ds.setJdbcUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDriverClassName(driver);
	}
	
	public static Connect getInstance() {
		if (connect==null) {
			if (connect==null) {
				connect = new Connect();
			}
		}
		return connect;
	}
	
	public Connection getConnection() {
		try {
			connection = ds.getConnection();
		} catch (SQLException e1) {
			logger.fatal("Erreur de connexion a la BDD! erreur:" + e1);
		}
		return connection;
	}

	public void closeConnection() {    
        if (connection != null) {
        	try {
        		connection.close();
	        } catch (SQLException e1) {
	        	logger.fatal("Erreur de fermeture de la connexion a la BDD! erreur:" + e1);
	        } finally {
	        	connection = null;
	        }
        }
	}
}