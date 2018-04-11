package com.excilys.computer.database.console;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computer.database.console.observable.AccueilObservable;
import com.excilys.computer.database.console.observable.CreateComputerObservable;
import com.excilys.computer.database.console.observable.DetailComputerObservable;
import com.excilys.computer.database.console.observable.ListCompanyObservable;
import com.excilys.computer.database.console.observable.ListComputerObservable;
import com.excilys.computer.database.console.observable.ReducedListComputerObservable;
import com.excilys.computer.database.console.observable.RmCompanyObservable;
import com.excilys.computer.database.console.observable.RmComputerObservable;
import com.excilys.computer.database.console.observable.UpdateComputerObservable;
import com.excilys.computer.database.console.observer.Observer;

public class ComputerDatabaseCLI {
	public static void clear(int nbre) {
		for (int i=0; i<nbre; i++) {
			System.out.println();
		}
	}
	
	public static void affichage(Observer observer, AccueilObservable accueilObservable) {
		String str;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		accueilObservable.execute();
		do {
			ComputerDatabaseCLI.clear(5);
			System.out.print("[Press Enter for MENU] Your choice: ");
			str = sc.nextLine();
			observer.Trigger(str);
		} while (!str.equals("exit"));
		System.out.print("\nThank you for visiting! :)\n");
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(CliSpringConfiguration.class);
		
		Observer observer = vApplicationContext.getBean(Observer.class);
		
		ListComputerObservable listComputerObservable = vApplicationContext.getBean(ListComputerObservable.class);
		ListCompanyObservable listCompanyObservable = vApplicationContext.getBean(ListCompanyObservable.class);
		CreateComputerObservable createComputerObservable = vApplicationContext.getBean(CreateComputerObservable.class);
		RmComputerObservable rmComputerObservable = vApplicationContext.getBean(RmComputerObservable.class);
		DetailComputerObservable detailComputerObservable = vApplicationContext.getBean(DetailComputerObservable.class);
		UpdateComputerObservable updateComputerObservable = vApplicationContext.getBean(UpdateComputerObservable.class);
		ReducedListComputerObservable reducedListComputerObservable = vApplicationContext.getBean(ReducedListComputerObservable.class);
		RmCompanyObservable rmCompanyObservable =  vApplicationContext.getBean(RmCompanyObservable.class);
		AccueilObservable accueilObservable = vApplicationContext.getBean(AccueilObservable.class);
		
		observer.Register("", accueilObservable);
		observer.Register("1", listComputerObservable);
		observer.Register("2", detailComputerObservable);
		observer.Register("3", listCompanyObservable);
		observer.Register("4", createComputerObservable);
		observer.Register("5", rmComputerObservable);
		observer.Register("6", rmCompanyObservable);
		observer.Register("7", updateComputerObservable);
		observer.Register("8", reducedListComputerObservable);
		
		affichage(observer,accueilObservable);
		return;
	}
}
