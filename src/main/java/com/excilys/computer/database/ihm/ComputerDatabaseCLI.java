package main.java.com.excilys.computer.database.ihm;

import java.util.Scanner;

import main.java.com.excilys.computer.database.ihm.observable.AccueilObservable;
import main.java.com.excilys.computer.database.ihm.observable.CreateComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.DetailComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.ListCompanyObservable;
import main.java.com.excilys.computer.database.ihm.observable.ListComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.ReducedListComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.RmCompanyObservable;
import main.java.com.excilys.computer.database.ihm.observable.RmComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.UpdateComputerObservable;
import main.java.com.excilys.computer.database.ihm.observer.Observer;

public class ComputerDatabaseCLI {	
	
	public static void clear(int nbre) {
		for (int i=0; i<nbre; i++) {
			System.out.println();
		}
	}
	
	public static void affichage(Observer observer) {
		AccueilObservable accueilObservable = new AccueilObservable();
		accueilObservable.execute();
		
		String str;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		do {
			ComputerDatabaseCLI.clear(5);
			System.out.print("[Press Enter for MENU] Your choice: ");
			str = sc.nextLine();
			observer.Trigger(str);
			
		} while (!str.equals("exit"));
		
		System.out.print("\nThank you for visiting! :)\n");
	}
	
	public static void main(String[] args) {
		Observer observer = new Observer();
		
		//INITIALISATION DES OBSERVABLES
		ListComputerObservable listComputerObservable = new ListComputerObservable();
		ListCompanyObservable listCompanyObservable = new ListCompanyObservable();
		CreateComputerObservable createComputerObservable = new CreateComputerObservable();
		RmComputerObservable rmComputerObservable = new RmComputerObservable();
		DetailComputerObservable detailComputerObservable = new DetailComputerObservable();
		UpdateComputerObservable updateComputerObservable = new UpdateComputerObservable();
		ReducedListComputerObservable reducedListComputerObservable = new ReducedListComputerObservable();
		RmCompanyObservable rmCompanyObservable =  new RmCompanyObservable();
		AccueilObservable accueilObservable = new AccueilObservable();
		
		//PRISE EN COMPTE DES OBSERVABLES
		observer.Register("", accueilObservable);
		observer.Register("1", listComputerObservable);
		observer.Register("2", detailComputerObservable);
		observer.Register("3", listCompanyObservable);
		observer.Register("4", createComputerObservable);
		observer.Register("5", rmComputerObservable);
		observer.Register("6", rmCompanyObservable);
		observer.Register("7", updateComputerObservable);
		observer.Register("8", reducedListComputerObservable);
		
		affichage(observer);
		return;
	}
}
