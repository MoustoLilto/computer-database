package main.java.com.excilys.computer.database.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.dao.DAOComputer;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

public class ServiceComputer {
	private static ServiceComputer service = null;
	DAOComputer daoComputer = DAOComputer.getInstance();
	
	final static Logger logger = LogManager.getLogger(ServiceComputer.class);

	private ServiceComputer() {
		
	}
	
	public static ServiceComputer getService() {
		if (service == null) {
			service = new ServiceComputer();
		}
		return service;
	}
	
	public int getNombre() {
		return DAOComputer.getInstance().getNombre();
	}
	
	public Company getCompany(long companyID) {
		return DAOCompany.getInstance().getCompany(companyID);
	}
	
	/**
	 * Permet de recuperer tous les computers
	 * @return
	 */
	public List<Computer> getAllComputer(){
		return daoComputer.getAllComputer();
	}
	
	
	public List<Computer> getSomeComputers(int numTuple, int nbreTuples, String orderBy, String order){		
		return daoComputer.getSomeComputers(numTuple, nbreTuples, orderBy, order);		
	}
	
	public int getSearchNumber(String recherche) {
		return daoComputer.getSearchNumber(recherche);
	}
	
	public List<Computer> seachComputers(String recherche, long position, long numberOfRows, String orderBy, String order){
		return daoComputer.searchComputers(recherche, position, numberOfRows, orderBy, order);
	}
	
	/**
	 * Permet d'ajouter un computer
	 * @param computer
	 * @return 0 si ca a fonctionne, sinon-1
	 */
	public int addComputer(Computer computer)  {
		
		if (daoComputer.addComputer(computer)==0) {
			logger.info("No rows have been added!\n\n");
			return -1;
		}
		return 0;
	}
	
	/**
	 * Permet de supprimer un computer
	 * @param computer
	 * @return 0 si ca a fonctionne, sinon-1
	 */
	public int rmComputer(Computer computer) {
		if (detailComputer(computer.getId())==null) {
			return -1;
		}
		if (daoComputer.rmComputer(computer)==0) {
			logger.info("No rows have been deleted!\n\n");
			return -1;
		}
		return 0;
	}
	
	/**
	 * Permet de modifier un computer
	 * @param computer
	 * @return 0 si ca a fonctionne, sinon-1
	 */
	public int updateComputer(Computer computer) {
		if (daoComputer.updateComputer(computer)==0) {
			logger.info("No rows have been updated!\n\n");
			return -1;
		}
		return 0;
	}
	
	/**
	 * Retourne un computer specifique identifie par son id
	 * @param id
	 * @return
	 */
	public Computer detailComputer(long id) {
		return daoComputer.detailComputer(id);
	}
}