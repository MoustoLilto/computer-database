package com.excilys.computer.database.console.observable;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.ComputerDatabaseCLI;
import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.console.rest.ComputerRestClient;
import com.excilys.computer.database.core.modele.Computer;

@Component
public class ListComputerObservable implements IObservable{
	private final ComputerRestClient restClient;
	
	public ListComputerObservable(ComputerRestClient restClient) {
		this.restClient = restClient;
	}

	public Boolean execute() {
		ComputerDatabaseCLI.clear(1);
		for (Computer computer : restClient.getAllComputer())
		{
			System.out.println(computer);
		}
		return true;
	}
}
