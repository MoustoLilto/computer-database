package com.excilys.computer.database.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

public class Connect {
	private static String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false"; 
	private static String password = "qwerty1234";
	private static String username = "admincdb";
	
	private static Connection connect = null;
	private static Logger logger;
	
	private Connect() {
		try {
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
