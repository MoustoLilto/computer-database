package main.java.com.excilys.computer.database.ihm;

import java.util.Scanner;

import main.java.com.excilys.computer.database.ihm.observable.AccueilObservable;
import main.java.com.excilys.computer.database.ihm.observable.CreateComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.DetailComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.ListCompanyObservable;
import main.java.com.excilys.computer.database.ihm.observable.ListComputerObservable;
import main.java.com.excilys.computer.database.ihm.observable.ReducedListComputerObservable;
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
		ListComputerObservable list_Computer_Observable = new ListComputerObservable();
		ListCompanyObservable list_Company_Observable = new ListCompanyObservable();
		CreateComputerObservable create_Computer_Observable = new CreateComputerObservable();
		RmComputerObservable rm_Computer_Observable = new RmComputerObservable();
		DetailComputerObservable detail_Computer_Observable = new DetailComputerObservable();
		UpdateComputerObservable update_Computer_Observable = new UpdateComputerObservable();
		ReducedListComputerObservable reducedList_Computer_Observable = new ReducedListComputerObservable();
		AccueilObservable accueilObservable = new AccueilObservable();
		
		//PRISE EN COMPTE DES OBSERVABLES
		observer.Register("", accueilObservable);
		observer.Register("1", list_Computer_Observable);
		observer.Register("2", detail_Computer_Observable);
		observer.Register("3", list_Company_Observable);
		observer.Register("4", create_Computer_Observable);
		observer.Register("5", rm_Computer_Observable);
		observer.Register("6", update_Computer_Observable);
		observer.Register("7", reducedList_Computer_Observable);
		
		affichage(observer);
		return;
	}
}
