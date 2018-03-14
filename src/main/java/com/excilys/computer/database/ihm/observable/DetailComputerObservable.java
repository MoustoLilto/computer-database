package main.java.com.excilys.computer.database.ihm.observable;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.ihm.ComputerDatabaseCLI;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

@Component
public class DetailComputerObservable implements IObservable {	
	private Scanner sc;
	
	@Autowired
	ServiceComputer service;

	public Computer idComputer() {
		sc = new Scanner(System.in);
		
		System.out.print("Enter the id of the computer: ");					
		try {
			long id = sc.nextLong();
			return service.detailComputer(id);
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