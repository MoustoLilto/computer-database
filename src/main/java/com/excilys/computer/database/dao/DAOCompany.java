package main.java.com.excilys.computer.database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.computer.database.modele.Company;

import java.sql.Statement;


public class DAOCompany {
	final private static Logger logger = LogManager.getLogger(DAOCompany.class);
	Connect connect = Connect.getInstance();
	
	private static DAOCompany daoCompany = null;
	
	private DAOCompany() {	
	}
	
	public static DAOCompany getInstance() {
		if (daoCompany == null) {
			daoCompany = new DAOCompany();
		}
		return daoCompany;
	}
	
	/**
	 * Permet de fermer le ResultSet et la connection
	 */
	public void close(ResultSet results) {
		if (results != null) {   
        	try {
        		results.close();
        		results = null;
        	} catch (SQLException e1) {
	        	logger.error("Erreur de fermeture du ResultSet! erreur:" + e1);
	        }
        }
		connect.closeConnection();
	}
	
	public int getNombre() {
		ResultSet results = null;
		String query;
		Statement stmt = null;
		int nombre = 0;
		try {
			query = RequetesCompanySQL.NOMBRE.toString();
			stmt = connect.getConnection().createStatement();
			results = stmt.executeQuery(query);
			
			while (results.next()) {
				nombre = results.getInt(1);
			}
		} catch(SQLException e) {
			logger.error("Erreur de recuperation du nombre de tuples dans la BDD, erreur: "+e);
		}
		return nombre;
	}
	
	public Company getCompany(long companyId) {
		ResultSet results = null;
		String query;
		PreparedStatement ps = null;
		Company company = null;
		try {
			query = RequetesCompanySQL.DETAIL.toString();
			ps = connect.getConnection().prepareStatement(query);
			ps.setLong(1, companyId);
			
			results = ps.executeQuery();
						
			while (results.next()) {
				company = new Company();
				company.setId(results.getLong(1));	
				company.setName(results.getString(2));
			} 
		} catch (SQLException e1) {
			company = null;
			logger.error("Error getting the details of the company! erreur:" + e1);
		}
			
		close(results);																
		if (Optional.ofNullable(company).isPresent()) {
			return Optional.ofNullable(company).get();
		}
		return null;
	}
	
	/**
	 * Execute la requete et remplis la liste
	 * @return the list of all companies
	 */
	public List<Company> getAllCompany() {
		ResultSet results = null;
		String query;
		Statement stmt = null;
		List<Company> companys = new ArrayList<Company>();		
		try {
			query = RequetesCompanySQL.ALL.toString();
			stmt = connect.getConnection().createStatement();
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
		close(results);
		return companys;
	}
}