package com.excilys.computer.database.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer.database.persistence.dao.DAOCompany;
import com.excilys.computer.database.persistence.dao.DAOComputer;
import com.excilys.computer.database.core.modele.Company;

@Service
public class ServiceCompany {
	final static Logger logger = LogManager.getLogger(ServiceCompany.class);
	private final DAOCompany daoCompany;
	private final DAOComputer daocomputer;
	
	public ServiceCompany(DAOCompany daoCompany,DAOComputer daocomputer) {
		this.daoCompany = daoCompany;
		this.daocomputer = daocomputer;
	}

	public int getNombre() {
		return daoCompany.getNombre();
	}
	
	public List<Company> getAllCompany(){
		return daoCompany.getAllCompany();
	}
	
	public Company getCompany(long companyID) {
		return daoCompany.getCompany(companyID);
	}
	
	@Transactional
	public int rmCompany(Company company) {
		if (getCompany(company.getId())==null) {
			return -1;
		}
		daocomputer.rmComputerByCompany(company);
		daoCompany.rmCompany(company);
		return 0;
	}
}

