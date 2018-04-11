package com.excilys.computer.database.console.observable;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.ComputerDatabaseCLI;
import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.core.modele.Computer;
import com.excilys.computer.database.services.ServiceComputer;

@Component
public class ListComputerObservable implements IObservable{
	private final ServiceComputer service;
	
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
