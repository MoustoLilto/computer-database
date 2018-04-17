package com.excilys.computer.database.console.observable;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.ComputerDatabaseCLI;
import com.excilys.computer.database.console.observer.IObservable;

@Component
public class AccueilObservable implements IObservable{
	public AccueilObservable() {
	}
	
	public String accueil() {
		String accueil = "=======================     MENU     ==========================\n";
		accueil += "1: To display the LIST of computers!\n";
		accueil += "2: To display the DETAILS of 1 computers!\n";
		accueil += "3: To display the LIST of companies!\n";
		accueil += "4: To CREATE a computer!\n";
		accueil += "5: To DELETE a computer!\n";
		accueil += "6: To DELETE a company!\n";
		accueil += "7: To UPDATE a computer!\n";
		accueil += "8: To display BY PAGE the list of computers!\n";
		accueil += "exit: To quit!\n";
		accueil += "==================================================================\n";
		
		return accueil;
	}
	
	public Boolean execute() {
		ComputerDatabaseCLI.clear(20);
		System.out.print(accueil());
		return true;
	}
}
