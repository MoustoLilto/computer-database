package main.java.com.excilys.computer.database.ihm.observable;

import main.java.com.excilys.computer.database.ihm.Main;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;

public class AccueilObservable implements IObservable{
	
	public String accueil() {
		String accueil = "=======================     MENU     ==========================\n";
		accueil += "1: To display the LIST of computers!\n";
		accueil += "2: To display the DETAILS of 1 computers!\n";
		accueil += "3: To display the LIST of companies!\n";
		accueil += "4: To CREATE a computer!\n";
		accueil += "5: To DELETE a computer!\n";
		accueil += "6: To UPDATE a computer!\n";
		accueil += "7: To display BY PAGE the list of computers!\n";
		accueil += "exit: To quit!\n";
		accueil += "==================================================================\n";
		
		return accueil;
	}
	
	public Boolean execute() {
		Main.clear(20);
		System.out.print(accueil());
		return true;
	}
}
