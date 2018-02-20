package main.java.com.excilys.computer.database.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import main.java.com.excilys.computer.database.modele.Computer;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class DAOComputer {
	private static ResultSet results;
	private static String query;
	private static Statement stmt;
	private static PreparedStatement ps;
	private static int  Nbre_Tuples_Modifie;
	
	final private static Logger logger = Logger.getLogger(DAOComputer.class);
	private static List<Computer> computers=null;
	
	/**
	 * Permet d'initialiser la liste des computers
	 */
	private DAOComputer() {
		computers = new ArrayList<Computer>();
	}
	
	/**
	 * Remet a zero la liste des ordianteurs, permettant ainsi de la recharger par la suite
	 */
	public static void commit() {
		if (computers!=null) {
			computers=null;
		}
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
	 * @return la liste complete des computer
	 */
	public static List<Computer> getAllComputer() {
		if (computers==null){
			new DAOComputer();
			
			try {
				query = RequetesComputerSQL.ALL.toString();
				stmt = (Statement) Connect.getConnection().createStatement();
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
	            	computer.setCompany_id(results.getLong(5));

	            	computers.add(computer); 													//Ajout du tuple a la liste
	            }
			} catch (SQLException e1) {
				logger.error("Error gettingthe list of computers! erreur:" + e1);
			}
			
			close();
		}																
		return computers;
	}
	
	/**
	 * Execute la requete et remplis la liste
	 * @param position => Position actuelle dans la base
	 * @param numberOfRows => Nombres de tuples a afficher
	 * @return la liste de quelques computers
	 */
	public static List<Computer> getSomeComputers(int backup, long position, long numberOfRows){
		if (backup == position) {
			return computers;
		}
		else {
			commit();
			new DAOComputer();
			try {
				query = RequetesComputerSQL.SOME.toString();
				ps = (PreparedStatement) Connect.getConnection().prepareStatement(query);
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
		        	computer.setCompany_id(results.getLong(5));	
		        	
		        	computers.add(computer); 														//Ajout du tuple a la liste
		        }
				
			}catch (SQLException e1) {
				logger.error("Error getting the reduced list of computers! erreur:" + e1);
			}
			
			close();
			return computers;
		}
	}
	
	/**
	 * Ajoute un computer a la BDD
	 * @param computer
	 * @return le nombre de tuples modifies
	 */
	public static int addComputer(Computer computer) {
		try {
			query = RequetesComputerSQL.ADD.toString();
			ps = (PreparedStatement) Connect.getConnection().prepareStatement(query);
			
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
			
			Nbre_Tuples_Modifie = ps.executeUpdate();									
		} catch (SQLException e1) {
			logger.error("Error adding the computer! erreur:" + e1);
		}	
		
		close();
		commit();
		return Nbre_Tuples_Modifie;
	}
	
	/**
	 * Supprime un computer de la BDD
	 * @param computer
	 * @return le nombre de tuples modifies
	 */
	public static int rmComputer(Computer computer) {
		try {
			query = RequetesComputerSQL.DELETE.toString();
			ps = (PreparedStatement) Connect.getConnection().prepareStatement(query);
			ps.setLong(1, computer.getId());
			
			Nbre_Tuples_Modifie = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error deleting the computer! erreur:" + e);
		}
		close();
		commit();
		return Nbre_Tuples_Modifie;
	}
	
	/**
	 * Execute la requete et traite le resultat
	 * @param id
	 * @return un computer specifique ou null si il ne le trouve pas
	 */
	public static Computer detailComputer(long id) {
		Computer computer = null;
		try {
			query = RequetesComputerSQL.DETAIL.toString();
			ps = (PreparedStatement) Connect.getConnection().prepareStatement(query);
			ps.setLong(1, id);
			
			results = ps.executeQuery();
						
			while (results.next()) {
	        	computer = new Computer(); 														//Creation du tuple
	        	
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
	        	computer.setCompany_id(results.getLong(5));	
	        }
		} catch (SQLException e1) {
			computer = null;
			logger.error("Error getting the details of the computer! erreur:" + e1);
		}
		
		close();																
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
	public static int updateComputer(Computer computer) {
		
		try {
			query = RequetesComputerSQL.UPDATE.toString();
			ps = (PreparedStatement) Connect.getConnection().prepareStatement(query);
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
			ps.setLong(4, computer.getId());
			
			Nbre_Tuples_Modifie = ps.executeUpdate();			
		} catch (SQLException e) {
			logger.error("Error updating the computer! erreur:" + e);
		}
		
		close();
		commit();
		return Nbre_Tuples_Modifie;
	}
}
