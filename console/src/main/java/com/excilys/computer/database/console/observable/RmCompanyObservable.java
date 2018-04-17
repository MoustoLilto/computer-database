package com.excilys.computer.database.console.observable;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.console.rest.CompanyRestClient;

@Component
public class RmCompanyObservable implements IObservable {	
	private Scanner sc;
	private final CompanyRestClient restClient;
	
	public RmCompanyObservable(CompanyRestClient restClient) {
		this.restClient = restClient;
	}

	public long ajoutId() {
		long id = -1;
		sc = new Scanner(System.in);
		System.out.print("Enter the id of the company: ");				
		try {
			id = sc.nextLong();
		} catch(Exception e) {
			System.out.println("erreur: "+ e);
		}
		return id;
	}
	
	public Boolean execute() {
		long id = ajoutId();
		if (id != -1) {
			if (restClient.rmCompany(id)==0) {
				System.out.print("Company and all computers related to it are deleted!\n\n");
				return true;
			}
			System.out.print("Error deleting the company, check the input ID!\n\n");
			return true;
		}
		System.out.print("Please enter next time a valid ID!\n\n");
		return true;
	}
}
