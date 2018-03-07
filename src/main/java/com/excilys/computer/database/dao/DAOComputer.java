package main.java.com.excilys.computer.database.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

public class DAOComputer {	
	final static Logger logger = LogManager.getLogger(DAOComputer.class);
	private static Connect connect = Connect.getInstance();
	private static DAOComputer daoComputer = null;
	
	private DAOComputer() {
	}
	
	public static DAOComputer getInstance() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}
	
	public int getNombre() {
		String query = RequetesComputerSQL.NOMBRE.toString();
		int nombre = 0;
		
		try (Statement stmt= connect.getConnection().createStatement()){
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
	
	public List<Computer> getAllComputer() {
		List<Computer> computers = new ArrayList<Computer>();
		String query = RequetesComputerSQL.ALL.toString();
			
		try (Statement stmt = connect.getConnection().createStatement()){
			ResultSet results = stmt.executeQuery(query);
						
			while (results.next()) {
            	Computer computer = new Computer(); 										//Creation du tuple
            	
            	computer.setId(results.getLong(1));											//Ajout des attributs au tuple
            	computer.setName(results.getString(2));
            	if (results.getTimestamp(3) == null) {
            		computer.setIntroduced(null);
            	}
            	else {
            		computer.setIntroduced(results.getTimestamp(3).toLocalDateTime().toLocalDate());
            	}
            	if (results.getTimestamp(4) == null) {
            		computer.setDiscontinued(null);
            	}
            	else {
            		computer.setDiscontinued(results.getTimestamp(4).toLocalDateTime().toLocalDate());
            	}
            	long companyID = results.getLong(5);
	        	String companyName = results.getString(6);
	        	Company company = null;
	        	if (companyID != 0) {
	        		company = new Company(companyID, companyName);
	        	}
	        	computer.setCompany(company);

            	computers.add(computer); 													//Ajout du tuple a la liste
            }
		} catch (SQLException e1) {
			logger.error("Error gettingthe list of computers! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		return computers;
	}
	
	public List<Computer> getSomeComputers(long position, long numberOfRows){
		String query = RequetesComputerSQL.SOME.toString();
		List<Computer> computers = new ArrayList<Computer>();
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setLong(1, (numberOfRows));
			ps.setLong(2, (position));
			ResultSet results = ps.executeQuery();
			
			while (results.next()) {
				Computer computer = new Computer();												//Creation du tuple
	        	
	        	computer.setId(results.getLong(1));												//Ajout des attributs au tuple
	        	computer.setName(results.getString(2));
	        	if (results.getTimestamp(3) == null) {
	        		computer.setIntroduced(null);
	        	}
	        	else {
	        		computer.setIntroduced(results.getTimestamp(3).toLocalDateTime().toLocalDate());
	        	}
	        	if (results.getTimestamp(4) == null) {
	        		computer.setDiscontinued(null);
	        	}
	        	else {
	        		computer.setDiscontinued(results.getTimestamp(4).toLocalDateTime().toLocalDate());
	        	}
	        	long companyID = results.getLong(5);
	        	String companyName = results.getString(6);
	        	Company company = null;
	        	if (companyID != 0) {
	        		company = new Company(companyID, companyName);
	        	}
	        	computer.setCompany(company);
	        	
	        	computers.add(computer); 														//Ajout du tuple a la liste
	        }
			
		}catch (SQLException e1) {
			logger.error("Error getting the reduced list of computers! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		return computers;
	}
	
	public List<Computer> searchComputers(String recherche){
		String query = RequetesComputerSQL.SEARCH.toString();
		List<Computer> computers = new ArrayList<Computer>();
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setString(1, recherche);
			ps.setString(2, recherche);
			ResultSet results = ps.executeQuery();
						
			while (results.next()) {
				Computer computer = new Computer();
	        	computer.setId(results.getLong(1));												//Ajout des attributs au tuple
	        	computer.setName(results.getString(2));
	        	if (results.getTimestamp(3) == null) {
	        		computer.setIntroduced(null);
	        	}
	        	else {
	        		computer.setIntroduced(results.getTimestamp(3).toLocalDateTime().toLocalDate());
	        	}
	        	if (results.getTimestamp(4) == null) {
	        		computer.setDiscontinued(null);
	        	}
	        	else {
	        		computer.setDiscontinued(results.getTimestamp(4).toLocalDateTime().toLocalDate());
	        	}
	        	long companyID = results.getLong(5);
	        	String companyName = results.getString(6);
	        	Company company = null;
	        	if (companyID != 0) {
	        		company = new Company(companyID, companyName);
	        	}
	        	computer.setCompany(company);
	        	
	        	computers.add(computer);
	        }
		} catch (SQLException e1) {
			logger.error("Error getting the list related to the search string! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		return computers;
	}
	
	public int addComputer(Computer computer) {
		String query = RequetesComputerSQL.ADD.toString();
		int  Nbre_Tuples_Modifie=0;
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				ps.setDate(2,null);
			}
			else { 
				ps.setDate(2, Date.valueOf(computer.getIntroduced()));
			}
			if (computer.getDiscontinued() == null) {
				ps.setDate(3,null);
			}
			else { 
				ps.setDate(3, Date.valueOf(computer.getDiscontinued()));
			}
			if (computer.getCompany() == null) {
				ps.setNull(4, Types.LONGNVARCHAR);
			}
			else { 
				ps.setLong(4, computer.getCompany().getId());
			}
			
			Nbre_Tuples_Modifie = ps.executeUpdate();									
		} catch (SQLException e1) {
			logger.error("Error adding the computer! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		return Nbre_Tuples_Modifie;
	}
	
	public int rmComputer(Computer computer) {
		String query = RequetesComputerSQL.DELETE.toString();
		int  Nbre_Tuples_Modifie=0;
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setLong(1, computer.getId());
			
			Nbre_Tuples_Modifie = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error deleting the computer! erreur:" + e);
		} finally {
			connect.closeConnection();
		}
		return Nbre_Tuples_Modifie;
	}
	
	public Computer detailComputer(long id) {
		String query = RequetesComputerSQL.DETAIL.toString();
		Computer computer = null;
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setLong(1, id);
			ResultSet results = ps.executeQuery();
						
			while (results.next()) {
				computer = new Computer();
	        	computer.setId(results.getLong(1));												//Ajout des attributs au tuple
	        	computer.setName(results.getString(2));
	        	if (results.getTimestamp(3) == null) {
	        		computer.setIntroduced(null);
	        	}
	        	else {
	        		computer.setIntroduced(results.getTimestamp(3).toLocalDateTime().toLocalDate());
	        	}
	        	if (results.getTimestamp(4) == null) {
	        		computer.setDiscontinued(null);
	        	}
	        	else {
	        		computer.setDiscontinued(results.getTimestamp(4).toLocalDateTime().toLocalDate());
	        	}
	        	long companyID = results.getLong(5);
	        	String companyName = results.getString(6);
	        	Company company = null;
	        	if (companyID != 0) {
	        		company = new Company(companyID, companyName);
	        	}
	        	computer.setCompany(company);
	        }
		} catch (SQLException e1) {
			computer = null;
			logger.error("Error getting the details of the computer! erreur:" + e1);
		} finally {
			connect.closeConnection();
		}
		if (Optional.ofNullable(computer).isPresent()) {
			return Optional.ofNullable(computer).get();
		}
		return null;
	}
	
	public int updateComputer(Computer computer) {
		String query = RequetesComputerSQL.UPDATE.toString();
		int  Nbre_Tuples_Modifie=0;
		
		try (PreparedStatement ps = connect.getConnection().prepareStatement(query)){
			ps.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				ps.setDate(2,null);
			}
			else { 
				ps.setDate(2, Date.valueOf(computer.getIntroduced()));
			}
			if (computer.getDiscontinued() == null) {
				ps.setDate(3,null);
			}
			else { 
				ps.setDate(3, Date.valueOf(computer.getDiscontinued()));
			}
			if (computer.getCompany() == null) {
				ps.setNull(4, Types.LONGNVARCHAR);
			}
			else { 
				ps.setLong(4, computer.getCompany().getId());
			}
			ps.setLong(5, computer.getId());
			
			Nbre_Tuples_Modifie = ps.executeUpdate();			
		} catch (SQLException e) {
			logger.error("Error updating the computer! erreur:" + e);
		} finally {
			connect.closeConnection();
		}
		return Nbre_Tuples_Modifie;
	}
}
