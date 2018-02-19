package main.java.com.excilys.computer.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.excilys.computer.database.modele.Company;
import com.mysql.jdbc.Statement;


public class DAOCompany {
	private static ResultSet results;
	private static String query;
	private static Statement stmt;
	
	private static List<Company> companys=null;  								//La liste des compagnies
	private static Logger logger;
	
	/**
	 * Permet d'initialiser la liste des companies
	 */
	private DAOCompany() {
		companys = new ArrayList<Company>();
	}
	
	/**
	 * Permet de fermer le ResultSet et la connection
	 */
	public static void close() {
		if (results != null) {   
        	try {
        		results.close();
        		results = null;
	        } catch (SQLException e1) {
	        	logger.error("Erreur de fermeture du ResultSet! erreur:" + e1);
			}
        }
		Connect.closeConnection();
	}
	
	/**
	 * Execute la requete et remplis la liste
	 * @return the list of all companies
	 */
	public static List<Company> getAllCompany() {
		if (companys==null) {
			new DAOCompany();
			
			try {
				query = RequetesCompanySQL.ALL.toString();
				stmt = (Statement) Connect.getConnection().createStatement();
				results = stmt.executeQuery(query);
				
				while (results.next()) {
					Company company = new Company(); 							//Creation du tuple
	            	
					company.setId(results.getLong(1));							//Ajout des attributs au tuple
					company.setName(results.getString(2));

					companys.add(company); 										//Ajout du tuple a la liste
	            }
			} catch (SQLException e1) {
				logger.error("Erreur de traitement de l'execution de la requete pour company! erreur:" + e1);
			}
			
			close();
		}
		return companys;
	}
}