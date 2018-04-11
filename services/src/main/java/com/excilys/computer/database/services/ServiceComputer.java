package com.excilys.computer.database.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.computer.database.persistence.dao.DAOCompany;
import com.excilys.computer.database.persistence.dao.DAOComputer;
import com.excilys.computer.database.core.modele.Company;
import com.excilys.computer.database.core.modele.Computer;

@Service
public class ServiceComputer {
	private final DAOComputer daoComputer;
	private final DAOCompany daoCompany;
	
	public ServiceComputer(DAOComputer daoComputer, DAOCompany daoCompany) {
		this.daoComputer = daoComputer;
		this.daoCompany = daoCompany;
	}

	public int getNombre() {
		return daoComputer.getNombre();
	}
	
	public Company getCompany(long companyID) {
		return daoCompany.getCompany(companyID);
	}
	
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
	
	public int addComputer(Computer computer)  {
		daoComputer.addComputer(computer);
		return 0;
	}
	
	public int rmComputer(Computer computer) {
		if (detailComputer(computer.getId())==null) {
			return -1;
		}
		daoComputer.rmComputer(computer);
		return 0;
	}
	
	public int updateComputer(Computer computer) {
		daoComputer.updateComputer(computer);
		return 0;
	}
	
	public Computer detailComputer(long id) {
		return daoComputer.detailComputer(id);
	}
}