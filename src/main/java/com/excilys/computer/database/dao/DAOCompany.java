package main.java.com.excilys.computer.database.dao;

import java.sql.Connection;
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
	private static Connect connect = Connect.getInstance();
	
	private static DAOCompany daoCompany = null;
	
	private DAOCompany() {	
	}
	
	public static DAOCompany getInstance() {
		if (daoCompany == null) {
			daoCompany = new DAOCompany();
		}
		return daoCompany;
	}
	
	public int getNombre() {
		String query = RequetesCompanySQL.NOMBRE.toString();
		int nombre = 0;
		
		try (Statement stmt = connect.getConnection().createStatement()){
			ResultSet results = stmt.executeQuery(query);
			
			while (results.next()) {
				nombre = results.getInt(1);
			}
		} catch(SQLException e) {
			logger.error("Erreur de recuperation du nombre de tuples dans la BDD, erreur: "+e);
		} finally {
			connect.closeConnection();
		}
		return nombre;
	}
	
	public Company getCompany(long companyId) {
		String query = RequetesCompanySQL.DETAIL.toString();
		Company company = null;
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setLong(1, companyId);
			ResultSet results = ps.executeQuery();
						
			while (results.next()) {
				company = new Company();
				
				company.setId(results.getLong(1));	
				company.setName(results.getString(2));
			} 
		} catch (SQLException e1) {
			company = null;
			logger.error("Error getting the details of the company! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		if (Optional.ofNullable(company).isPresent()) {
			return Optional.ofNullable(company).get();
		}
		return null;
	}
	
	public List<Company> getAllCompany() {
		String query = RequetesCompanySQL.ALL.toString();
		List<Company> companys = new ArrayList<Company>();
		
		try (Statement stmt = connect.getConnection().createStatement()){
			ResultSet results = stmt.executeQuery(query);
			
			while (results.next()) {
				Company company = new Company(); 							//Creation du tuple
				
				company.setId(results.getLong(1));							//Ajout des attributs au tuple
				company.setName(results.getString(2));

				companys.add(company); 										//Ajout du tuple a la liste
            }
		} catch (SQLException e1) {
			logger.error("Erreur de traitement de l'execution de la requete pour company! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		return companys;
	}
	
	public int rmCompany(Company company) {
		String query1 = RequetesComputerSQL.DELETE_COMPANY.toString();
		String query = RequetesCompanySQL.DELETE.toString();
		int  Nbre_Tuples_Modifie=0;
		
		try (Connection connection = connect.getConnection()){
			connection.setAutoCommit(false);
			PreparedStatement ps1 = connection.prepareStatement(query1);
			ps1.setLong(1, company.getId());
			Nbre_Tuples_Modifie = ps1.executeUpdate();
			
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, company.getId());
			Nbre_Tuples_Modifie += ps.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Error deleting the company and the computers related to it! erreur:" + e);
		} finally {
			connect.closeConnection();
		}
		return Nbre_Tuples_Modifie;
	}
}