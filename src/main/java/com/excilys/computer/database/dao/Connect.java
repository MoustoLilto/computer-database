package main.java.com.excilys.computer.database.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import java.sql.Connection;

public class Connect {		
	private static String url;
	private static String password;
	private static String username;

	final private static Logger logger = Logger.getLogger(Connect.class);	
	
	private static Connection connection = null;
	private static Connect Connect;

	private Connect() {		
	}
	
	public static Connect getInstance() {
		if (Connect==null) {
			Connect = new Connect();
		}
		return Connect;
	}
	
	public Connection getConnection() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("connect");
			url = bundle.getString("url");
			username = bundle.getString("username");
			password = bundle.getString("password");
			
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			logger.error("Erreur de connexion a la BDD! erreur:" + e1);
		} 
		return connection;
	}

	public void closeConnection() {           
        if (connection != null) {
        	try {
        		connection.close();
        		connection = null;
	        } catch (SQLException e1) {
	        	logger.error("Erreur de fermeture de la connexion a la BDD! erreur:" + e1);
	        }
        }
	}
}
