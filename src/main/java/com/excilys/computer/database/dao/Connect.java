package main.java.com.excilys.computer.database.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class Connect {		
	private static String url;
	private static String password;
	private static String username;
	private static String driver;

	final private static Logger logger = LogManager.getLogger(Connect.class);	
	
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
			driver = bundle.getString("driver");
			
			Class.forName(driver).newInstance();
			
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			logger.fatal("Erreur de connexion a la BDD! erreur:" + e1);
		} catch (InstantiationException e) {
			logger.fatal("Erreur d'instanciation a la BDD! erreur:" + e);
		} catch (IllegalAccessException e) {
			logger.fatal("Erreur droits d'acces a la BDD! erreur:" + e);
		} catch (ClassNotFoundException e) {
			logger.fatal("ClassForname non trouve! erreur:" + e);
		} 
		return connection;
	}

	public void closeConnection() {           
        if (connection != null) {
        	try {
        		connection.close();
        		connection = null;
	        } catch (SQLException e1) {
	        	logger.fatal("Erreur de fermeture de la connexion a la BDD! erreur:" + e1);
	        }
        }
	}
}
