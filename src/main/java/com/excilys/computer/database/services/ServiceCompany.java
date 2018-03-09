package main.java.com.excilys.computer.database.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.modele.Company;

public class ServiceCompany {
	final static Logger logger = LogManager.getLogger(ServiceCompany.class);
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
	
	public int getNombre() {
		return DAOCompany.getInstance().getNombre();
	}
	
	/**
	 * Permet de recuperer toutes les compagnies
	 * @return
	 */
	public List<Company> getAllCompany(){
		List<Company> allCompanies = daoCompany.getAllCompany();
		return allCompanies;
	}
	
	public Company getCompany(long companyID) {
		return daoCompany.getCompany(companyID);
	}
	
	public int rmCompany(Company company) {
		if (getCompany(company.getId())==null) {
			return -1;
		}
		if (daoCompany.rmCompany(company)==0) {
			logger.info("No rows have been deleted!\n\n");
			return -1;
		}
		return 0;
	}
}

