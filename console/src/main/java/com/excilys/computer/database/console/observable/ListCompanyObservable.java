package com.excilys.computer.database.console.observable;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.ComputerDatabaseCLI;
import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.core.modele.Company;
import com.excilys.computer.database.services.ServiceCompany;

@Component
public class ListCompanyObservable implements IObservable {
	final private ServiceCompany service;
	
	public ListCompanyObservable(ServiceCompany service) {
		this.service = service;
	}

	public Boolean execute() {
		ComputerDatabaseCLI.clear(1);
		for (Company company : service.getAllCompany())
		{
			System.out.println(company);
		}
		return true;
	}
}
