package com.excilys.computer.database.console.observable;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.ComputerDatabaseCLI;
import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.console.rest.CompanyRestClient;
import com.excilys.computer.database.core.modele.Company;

@Component
public class ListCompanyObservable implements IObservable {
	final private CompanyRestClient restClient;
	
	public ListCompanyObservable(CompanyRestClient restClient) {
		this.restClient = restClient;
	}

	public Boolean execute() {
		ComputerDatabaseCLI.clear(1);
		for (Company company : restClient.getAllCompany())
		{
			System.out.println(company);
		}
		return true;
	}
}
