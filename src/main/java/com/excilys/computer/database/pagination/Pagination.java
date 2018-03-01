package main.java.com.excilys.computer.database.pagination;

import java.util.List;

import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

public class Pagination {
	private static Pagination pagination = null;
	ServiceComputer serviceComputer = ServiceComputer.getService();
	private int numPage;
	
	private Pagination() {
	}
	
	public static Pagination getInstance() {
		if (pagination == null) {
			pagination = new Pagination();
		}
		return pagination;
	}
	
	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public int nbrPages(int nbreTuples) {
		int bddNombre = ServiceComputer.getService().getNombre();
		int nbrPageMax = (int) Math.ceil(bddNombre/nbreTuples);
		return nbrPageMax;
	}
	
	public List<Computer> computersPage(int numPage) {
		int nbreTuples = 50;
		int nbrPageMax = nbrPages(nbreTuples);
		if (numPage > nbrPageMax || numPage < 0) {
			return null;
		}
		
		int numTuple = numPage*nbreTuples+1;
		List<Computer> computersPage = serviceComputer.getSomeComputers(numTuple, (nbreTuples));
		return computersPage;
	}
}
