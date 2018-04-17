package com.excilys.computer.database.console.observable;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.ComputerDatabaseCLI;
import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.console.rest.ComputerRestClient;
import com.excilys.computer.database.core.modele.Computer;

@Component
public class DetailComputerObservable implements IObservable {	
	private Scanner sc;
	private final ComputerRestClient restClient;
	
	public DetailComputerObservable(ComputerRestClient restClient) {
		this.restClient = restClient;
	}

	public Computer idComputer() {
		sc = new Scanner(System.in);
		
		System.out.print("Enter the id of the computer: ");					
		try {
			long id = sc.nextLong();
			return restClient.getComputer(id);
		} catch(Exception e) {
			return null;
		}		
	}
	
	public Boolean execute() {
		ComputerDatabaseCLI.clear(1);
		
		Computer computer = idComputer();
		if (computer != null) {
			System.out.println(computer);
		}
		else {
			System.out.println("Sorry a computer with this id don't exist!\n");
		}
		
		return true;
	}
}