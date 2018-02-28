package main.java.com.excilys.computer.database.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.computer.database.modele.Computer;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;


public class DAOComputer {	
	final static Logger logger = LogManager.getLogger(DAOComputer.class);
	Connect connect = Connect.getInstance();
	
	private static DAOComputer daoComputer = null;
	
	private DAOComputer() {
	}
	
	public static DAOComputer getInstance() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
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
			query = RequetesComputerSQL.NOMBRE.toString();
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
	
	/**
	 * Execute la requete et remplis la liste
	 * @return la liste complete des computer
	 */
	public List<Computer> getAllComputer() {
		List<Computer> computers = new ArrayList<Computer>();
		ResultSet results = null;
		String query;
		Statement stmt;
		DAOCompany daoCompany = DAOCompany.getInstance();
			
		try {
			query = RequetesComputerSQL.ALL.toString();
			stmt = connect.getConnection().createStatement();
			results = stmt.executeQuery(query);
						
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
            	computer.setCompany(daoCompany.getCompany(companyID));

            	computers.add(computer); 													//Ajout du tuple a la liste
            }
		} catch (SQLException e1) {
			logger.error("Error gettingthe list of computers! erreur:" + e1);
		}
		
		close(results);																
		return computers;
	}
	
	/**
	 * Execute la requete et remplis la liste
	 * @param position => Position actuelle dans la base
	 * @param numberOfRows => Nombres de tuples a afficher
	 * @return la liste de quelques computers
	 */
	public List<Computer> getSomeComputers(long position, long numberOfRows){
		ResultSet results = null;
		String query;
		PreparedStatement ps = null;
		List<Computer> computers = new ArrayList<Computer>();
		DAOCompany daoCompany = DAOCompany.getInstance();
		try {
			query = RequetesComputerSQL.SOME.toString();
			ps = connect.getConnection().prepareStatement(query);
			ps.setLong(1, (numberOfRows));
			ps.setLong(2, (position));
			
			results = ps.executeQuery();
			
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
            	computer.setCompany(daoCompany.getCompany(companyID));
	        	
	        	computers.add(computer); 														//Ajout du tuple a la liste
	        }
			
		}catch (SQLException e1) {
			logger.error("Error getting the reduced list of computers! erreur:" + e1);
		}
			
		close(results);
		return computers;
	}
	
	/**
	 * Ajoute un computer a la BDD
	 * @param computer
	 * @return le nombre de tuples modifies
	 */
	public int addComputer(Computer computer) {
		ResultSet results = null;
		String query;
		PreparedStatement ps = null;
		int  Nbre_Tuples_Modifie=0;
		try {
			query = RequetesComputerSQL.ADD.toString();
			ps = connect.getConnection().prepareStatement(query);
			
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
		}	
		
		close(results);
		return Nbre_Tuples_Modifie;
	}
	
	/**
	 * Supprime un computer de la BDD
	 * @param computer
	 * @return le nombre de tuples modifies
	 */
	public int rmComputer(Computer computer) {
		ResultSet results = null;
		String query;
		PreparedStatement ps = null;
		int  Nbre_Tuples_Modifie=0;
		try {
			query = RequetesComputerSQL.DELETE.toString();
			ps = connect.getConnection().prepareStatement(query);
			ps.setLong(1, computer.getId());
			
			Nbre_Tuples_Modifie = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error deleting the computer! erreur:" + e);
		}
		close(results);
		return Nbre_Tuples_Modifie;
	}
	
	/**
	 * Execute la requete et traite le resultat
	 * @param id
	 * @return un computer specifique ou null si il ne le trouve pas
	 */
	public Computer detailComputer(long id) {
		ResultSet results = null;
		String query;
		PreparedStatement ps = null;
		Computer computer = null;
		DAOCompany daoCompany = DAOCompany.getInstance();
		try {
			query = RequetesComputerSQL.DETAIL.toString();
			ps = connect.getConnection().prepareStatement(query);
			ps.setLong(1, id);
			
			results = ps.executeQuery();
						
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
            	computer.setCompany(daoCompany.getCompany(companyID));
	        }
		} catch (SQLException e1) {
			computer = null;
			logger.error("Error getting the details of the computer! erreur:" + e1);
		}
		
		close(results);																
		if (Optional.ofNullable(computer).isPresent()) {
			return Optional.ofNullable(computer).get();
		}
		return null;
	}
	
	/**
	 * Modifie un computer specifique de la BDD
	 * @param computer
	 * @return le nombre de tuples modifies
	 */
	public int updateComputer(Computer computer) {
		ResultSet results = null;
		String query;
		PreparedStatement ps = null;
		int  Nbre_Tuples_Modifie=0;
		try {
			query = RequetesComputerSQL.UPDATE.toString();
			ps = connect.getConnection().prepareStatement(query);
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
		}
		
		close(results);
		return Nbre_Tuples_Modifie;
	}
}
