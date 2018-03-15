package main.java.com.excilys.computer.database.ihm.observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.ihm.ComputerDatabaseCLI;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.services.ServiceCompany;

@Component
public class ListCompanyObservable implements IObservable {
	final private ServiceCompany service;
	
	@Autowired
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
