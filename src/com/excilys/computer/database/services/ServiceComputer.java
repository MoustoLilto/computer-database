package com.excilys.computer.database.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.excilys.computer.database.dao.DAOComputer;
import com.excilys.computer.database.modele.Computer;

public class ServiceComputer {

	private ServiceComputer() {
		
	}
	
	private static Logger logger;
	private static ServiceComputer service = null;
	
	public static ServiceComputer getService() {
		if (service == null) {
			service = new ServiceComputer();
		}
		return service;
	}
	
	/**
	 * Permet de recuperer tous les computers
	 * @return
	 */
	public List<Computer> getAllComputer(){
		return DAOComputer.getAllComputer();
	}
	
	
	public List<Computer> getSomeComputers(int backup, int numTuple, int nbreTuples){		
		return DAOComputer.getSomeComputers(backup, numTuple, nbreTuples);		
	}
	
	/**
	 * Permet d'ajouter un computer
	 * @param computer
	 * @return 0 si ca a fonctionne, sinon-1
	 */
	public int addComputer(Computer computer)  {
		
		if (DAOComputer.addComputer(computer)==0) {
			logger.error("No rows have been added!\n\n");
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
		if (DAOComputer.rmComputer(computer)==0) {
			logger.error("No rows have been deleted!\n\n");
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
		if (DAOComputer.updateComputer(computer)==0) {
			logger.error("No rows have been updated!\n\n");
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
		return DAOComputer.detailComputer(id);
	}

}