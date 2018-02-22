package main.java.com.excilys.computer.database.ihm.observable;

import java.util.Scanner;

import main.java.com.excilys.computer.database.ComputerDatabase;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

public class DetailComputerObservable implements IObservable {	
	
	private Scanner sc;

	/**
	 * Retourne un computer identifie par son id
	 */
	public Computer idComputer() {
		ServiceComputer service = ServiceComputer.getService();
		sc = new Scanner(System.in);
		
		System.out.print("Enter the id of the computer: ");					
		try {
			long id = sc.nextLong();
			return service.detailComputer(id);
		} catch(Exception e) {
			return null;
		}		
	}
	
	/**
	 * Affiche les details du computer
	 */
	public Boolean execute() {
		
		Computer computer = idComputer();
		ComputerDatabase.clear(1);
		if (computer != null)
			System.out.println(computer);
		else 
			System.out.println("Sorry a computer with this id don't exist!\n");
		
		return true;
	}
}