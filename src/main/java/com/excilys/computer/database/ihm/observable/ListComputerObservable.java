package main.java.com.excilys.computer.database.ihm.observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.ihm.ComputerDatabaseCLI;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

@Component
public class ListComputerObservable implements IObservable{
	private final ServiceComputer service;
	
	@Autowired
	public ListComputerObservable(ServiceComputer service) {
		super();
		this.service = service;
	}

	public Boolean execute() {
		ComputerDatabaseCLI.clear(1);
		for (Computer computer : service.getAllComputer())
		{
			System.out.println(computer);
		}
		return true;
	}
}
