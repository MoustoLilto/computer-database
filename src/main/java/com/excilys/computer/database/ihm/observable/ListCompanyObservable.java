package main.java.com.excilys.computer.database.ihm.observable;

import main.java.com.excilys.computer.database.ihm.Main;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.services.ServiceCompany;

public class ListCompanyObservable implements IObservable {
	
	ServiceCompany service = ServiceCompany.getService();
	
	public Boolean execute() {
		Main.clear(1);
		for (Company company : service.getAllCompany())
		{
			System.out.println(company);
		}
		return true;
	}
}
