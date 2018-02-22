package main.java.com.excilys.computer.database.services;

import java.util.List;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.modele.Company;

public class ServiceCompany {
	private static ServiceCompany service = null;
	DAOCompany daoCompany = DAOCompany.getInstance();
	
	private ServiceCompany() {
	}
	
	public static ServiceCompany getService() {
		if (service == null) {
			service = new ServiceCompany();
		}
		return service;
	}
	
	/**
	 * Permet de recuperer toutes les compagnies
	 * @return
	 */
	public List<Company> getAllCompany(){
		List<Company> allCompanies = daoCompany.getAllCompany();
		return allCompanies;
	}
	
}

