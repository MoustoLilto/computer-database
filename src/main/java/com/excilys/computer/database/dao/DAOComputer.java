package main.java.com.excilys.computer.database.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

@Repository
public class DAOComputer {	
	final static Logger logger = LogManager.getLogger(DAOComputer.class);
	private final Connect connect;
	
	@Autowired
	public DAOComputer(Connect connect) {
		this.connect = connect;
	}

	public int getNombre() {
		String query = RequetesComputerSQL.NOMBRE.toString();
		int nombre = 0;
		
		try (Connection connection = connect.getConnection(); Statement stmt = connection.createStatement(); ResultSet results = stmt.executeQuery(query)){
			while (results.next()) {
				nombre = results.getInt(1);
			}
		} catch(SQLException e) {
			logger.error("Erreur de recuperation du nombre de tuples dans la BDD, erreur: "+e);
		}
		return nombre;
	}
	
	public List<Computer> getAllComputer() {
		List<Computer> computers = new ArrayList<Computer>();
		String query = RequetesComputerSQL.ALL.toString();
			
		try (Connection connection = connect.getConnection(); Statement stmt = connection.createStatement(); ResultSet results = stmt.executeQuery(query)){
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
		}
		return computers;
	}
	
	public List<Computer> getSomeComputers(long position, long numberOfRows, String orderBy, String order){
		String[] splittedQuerry = RequetesComputerSQL.SOME_WITH_ORDER.toString().split("---");
		String query = splittedQuerry[0] + orderBy +splittedQuerry[1] + order + splittedQuerry[2];
		List<Computer> computers = new ArrayList<Computer>();
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
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
			results.close();
		}catch (SQLException e1) {
			logger.error("Error getting the reduced list of computers! erreur:" + e1);
		}
		return computers;
	}
	
	public int getSearchNumber(String recherche) {
		String query = RequetesComputerSQL.SEARCH_NOMBRE.toString();
		int nombre = 0;
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, "%" + recherche + "%");
			ps.setString(2, "%" + recherche + "%");
			ResultSet results = ps.executeQuery();
					
			while (results.next()) {
				nombre = results.getInt(1);
			}
			results.close();
		} catch(SQLException e) {
			logger.error("Erreur de recuperation du nombre de tuples dans la BDD lie a la recherche, erreur: "+e);
		}
		return nombre;
	}
	
	public List<Computer> searchComputers(String recherche, long position, long numberOfRows, String orderBy, String order){
		String[] splittedQuerry = RequetesComputerSQL.SEARCH_WITH_ORDER.toString().split("---");
		String query = splittedQuerry[0] + orderBy +splittedQuerry[1] + order + splittedQuerry[2];
		List<Computer> computers = new ArrayList<Computer>();
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
			ps.setString(1, "%" + recherche + "%");
			ps.setString(2, "%" + recherche + "%");
			ps.setLong(3, (numberOfRows));
			ps.setLong(4, (position));
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
			results.close();
		} catch (SQLException e1) {
			logger.error("Error getting the list related to the search string! erreur:" + e1);
		}
		return computers;
	}
	
	public int addComputer(Computer computer) {
		String query = RequetesComputerSQL.ADD.toString();
		int  nbrTuplesModifies=0;
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
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
			
			nbrTuplesModifies = ps.executeUpdate();									
		} catch (SQLException e1) {
			logger.error("Error adding the computer! erreur:" + e1);
		}
		return nbrTuplesModifies;
	}
	
	public int rmComputer(Computer computer) {
		String query = RequetesComputerSQL.DELETE.toString();
		int  nbrTuplesModifies=0;
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
			ps.setLong(1, computer.getId());
			
			nbrTuplesModifies = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error deleting the computer! erreur:" + e);
		}
		return nbrTuplesModifies;
	}
	
	public Computer detailComputer(long id) {
		String query = RequetesComputerSQL.DETAIL.toString();
		Computer computer = null;
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
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
			results.close();
		} catch (SQLException e1) {
			computer = null;
			logger.error("Error getting the details of the computer! erreur:" + e1);
		}
		return computer;
	}
	
	public int updateComputer(Computer computer) {
		String query = RequetesComputerSQL.UPDATE.toString();
		int  nbrTuplesModifies=0;
		
		try (Connection connection = connect.getConnection(); PreparedStatement ps = connection.prepareStatement(query)){
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
			
			nbrTuplesModifies = ps.executeUpdate();			
		} catch (SQLException e) {
			logger.error("Error updating the computer! erreur:" + e);
		}
		return nbrTuplesModifies;
	}
}