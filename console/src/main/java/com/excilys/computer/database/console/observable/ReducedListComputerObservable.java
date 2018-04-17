package com.excilys.computer.database.console.observable;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.console.observer.IObservable;
import com.excilys.computer.database.console.rest.ComputerRestClient;
import com.excilys.computer.database.core.modele.Computer;

@Component
public class ReducedListComputerObservable implements IObservable{
	private Scanner sc;
	private final ComputerRestClient restClient;
	
	public ReducedListComputerObservable(ComputerRestClient restClient) {
		this.restClient = restClient;
	}
	
	public Boolean execute() {
		sc = new Scanner(System.in);
		int numPage = 1;
		int numTuple = 0;
		int nbreTuples = 10;
		String choix;
		
		System.out.print("Enter the number of rows to display: ");					
		try {
			nbreTuples = sc.nextInt();
			sc.nextLine();
		}catch(Exception e) {
			System.out.println("Set by default to 10!\n");
		}
		
		do {
			System.out.print("\n<<<<<<<<<<<<<<<<<<< PAGE "+numPage+" (From row "+(numTuple+1)+" to row "+(numTuple+nbreTuples+1)+") <<<<<<<<<<<<<<<<<<<\n");
			
			for (Computer computer : restClient.getSomeComputer(numTuple, nbreTuples)){
				System.out.println(computer);
			}
			
			System.out.print("\nPRESS 'q' to quit, 'b' to go back and 'n' to go next: ");
			choix = sc.nextLine();
			try {
				if (choix.charAt(0) == 'n') {
					numTuple += nbreTuples;
					numPage++;
				}
				else if (choix.charAt(0) == 'b' && numTuple>=(0+nbreTuples)) {
					numTuple -= nbreTuples;
					numPage--;
				}
			} catch(Exception e) {
			}
		} while (!choix.equals("q"));//'q');
		return true;
	}
}
