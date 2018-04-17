package com.excilys.computer.database.console.observable;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.console.rest.ComputerRestClient;

@Component
public class RmComputerObservable implements IObservable {	
	Scanner sc;
	private final ComputerRestClient restClient;
	
	public RmComputerObservable(ComputerRestClient restClient) {
		this.restClient = restClient;
	}

	public long ajoutId() {
		long id = -1;
		sc = new Scanner(System.in);
		System.out.print("Enter the id of the computer: ");				
		try {
			id = sc.nextLong();
		} catch(Exception e) {
			return -1;
		}
		return id ;
	}
	
	public Boolean execute() {
		long id = ajoutId();
		if (id != -1) {
			if (restClient.rmComputer(id)==0) {
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
