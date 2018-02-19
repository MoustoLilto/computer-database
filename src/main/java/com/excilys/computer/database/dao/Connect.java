package main.java.com.excilys.computer.database.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

public class Connect {
	/*Properties prop = new Properties();
	InputStream input = null;
	
	try {

		input = new FileInputStream("src/main/resources/connect.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		System.out.println(prop.getProperty("url"));
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	private static Properties prop = new Properties();
	private static InputStream input = null;
	
	private static String url;						// = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false"; 
	private static String password;					// = "qwerty1234";
	private static String username;					// = "admincdb";
	
	private static Connection connect = null;
	private static Logger logger;
	
	private static void infoBDD(String url, String password, String username) {
		try {
			input = new FileInputStream("src/main/resources/connect.properties");	
			prop.load(input);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		} catch (IOException e) {
			logger.error("Erreur d'ouverture du 'connect.properties' erreur:" + e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Erreur de fermeture du 'connect.properties' erreur:" + e);
				}
			}
		}
	}
	
	private Connect() {
		try {
			infoBDD(url, password, username);
			connect = (Connection) DriverManager.getConnection(url, username,  password);
		} catch (SQLException e1) {
			logger.error("Erreur de connexion a la BDD! erreur:" + e1);
		} 
	}
	
	public static Connection getConnection()
    {           
        if (connect == null) {   
        	new Connect(); 
        }
        return connect;
    }

	public static void closeConnection()
    {           
        if (connect != null) {   
        	try {
        		connect.close();
        		connect = null;
	        } catch (SQLException e1) {
	        	logger.error("Erreur de fermeture de la connexion a la BDD! erreur:" + e1);
			}
        }
    }
}
