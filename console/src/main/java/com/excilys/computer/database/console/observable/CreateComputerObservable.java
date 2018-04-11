package com.excilys.computer.database.console.observable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.core.modele.Computer;
import com.excilys.computer.database.services.ServiceComputer;

@Component
public class CreateComputerObservable implements IObservable {	
	private Scanner sc;
	final private ServiceComputer service;
	
	public CreateComputerObservable(ServiceComputer service) {
		this.service = service;
		this.sc = new Scanner(System.in);
	}

	public void ajoutNom(Computer computer) {
		System.out.print("Enter the name of the computer: ");					
		String str = sc.nextLine();
		computer.setName(str);
	}
	
	public void ajoutIntroduced(Computer computer) {
		int validation;
		do {
			validation=0;
			System.out.print("Do you want to add the introduced date? (y/n): ");		
			try{
				char choix = sc.nextLine().charAt(0);
				if (choix == 'y') {			
					System.out.print("Enter the introduced date (DD/MM/YYYY): ");
					String date = sc.nextLine();
			
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					try {
						LocalDate introd = LocalDate.parse(date, formatter);
						if (introd.getYear() > 1970) {
							computer.setIntroduced(introd);
							validation = 1;
						}
						else {
							System.out.print("The year must be superior to 1970!\n");
							validation = 0;
						}
					}catch(DateTimeParseException e) {
						System.out.print("Please enter a valid Date on the good format!\n");
						validation = 0;
					}				
				} else if(choix == 'n') {
					System.out.print("Set to null\n");
					validation = 1;
				} else {
					System.out.print("Set to null! Next time please enter 'y' or 'n'\n");
					validation = 1;
				}
			} catch(Exception e) {
				System.out.print("Next time please enter 'y' or 'n'\n");
			}
		} while(validation==0);
	}
	
	public void ajoutDiscontinued(Computer computer) {
		if (computer.getIntroduced()!=null) {
			int validation;
			do {
				validation=0;
				System.out.print("Do you want to add the discontinued date? (y/n): ");		
				try{
					char choix = sc.nextLine().charAt(0);
					if (choix == 'y') {			
						System.out.print("Enter the discontinued date (DD/MM/YYYY): ");
						String date = sc.nextLine();
				
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						try {
							LocalDate discont = LocalDate.parse(date, formatter);
							if ((discont.isAfter(computer.getIntroduced())) && (discont.getYear() > 1970)) {
								computer.setDiscontinued(discont);
								validation = 1;
							}
							else {
								System.out.print("Please enter a date greater than the introduced date and the year must be superior to 1970!\n");
								validation = 0;
							}
						}catch(DateTimeParseException e) {
							System.out.print("Please enter a valid Date on the good format!\n");
							validation = 0;
						}				
					} else if(choix == 'n') {
						System.out.print("Set to null\n");
						validation = 1;
					} else {
						System.out.print("Set to null! Next time please enter 'y' or 'n'\n");
						validation = 1;
					}
				} catch(Exception e) {
					System.out.print("Next time please enter 'y' or 'n'\n");
				}
			} while(validation==0);
		}
	}
	
	public Boolean execute() {
		Computer computer = new Computer();
		
		ajoutNom(computer);
		ajoutIntroduced(computer);		
		ajoutDiscontinued(computer);
		
		if (service.addComputer(computer)==0) {
			System.out.print("Computer '" + computer.getName() + "' is added!\n\n");
			return true;
		}
		System.out.print("Error adding the computer '" + computer.getName() + "'\n\n");
		return false;
	}
}
