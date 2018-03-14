package main.java.com.excilys.computer.database.ihm.observable;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

@Component
public class RmComputerObservable implements IObservable {	
	Scanner sc;
	
	@Autowired
	ServiceComputer service;
	
	public int ajoutId(Computer computer) {
		sc = new Scanner(System.in);
		System.out.print("Enter the id of the computer: ");				
		try {
			long id = sc.nextLong();
			computer.setId(id);
		} catch(Exception e) {
			return -1;
		}
		return 0 ;
	}
	
	public Boolean execute() {
		Computer computer = new Computer(); 
		
		if (ajoutId(computer)==0) {
			if (service.rmComputer(computer)==0) {
				System.out.print("Computer is deleted!\n\n");
				return true;
			}
			
			System.out.print("Error deleting the computer, check the input ID!\n\n");
			return true;
		}
		System.out.print("Please enter next time a valid ID!\n\n");
		return true;
	}

}
