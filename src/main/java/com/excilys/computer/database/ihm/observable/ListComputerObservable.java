package main.java.com.excilys.computer.database.ihm.observable;

import main.java.com.excilys.computer.database.ihm.Main;
import main.java.com.excilys.computer.database.ihm.observer.IObservable;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

public class ListComputerObservable implements IObservable{
	
	ServiceComputer service = ServiceComputer.getService();
	
	public Boolean execute() {
		Main.clear(1);
		for (Computer computer : service.getAllComputer())
		{
			System.out.println(computer);
		}
		return true;
	}
}
