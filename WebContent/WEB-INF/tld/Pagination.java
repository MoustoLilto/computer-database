import main.java.com.excilys.computer.database.services.ServiceComputer;

public class Pagination {
	private int numPage = 1;
	private int numTuple = 0;
	private int nbreTuples = 10;
	private int nbrPageMax;
	private int bddNombre;
	
	public int nbrPages(int nbreTuples) {
		int bddNombre = ServiceComputer.getService().getNombre();
		int nbrPageMax = (int) Math.ceil(bddNombre/nbreTuples);
		return nbrPageMax;
	}

}
