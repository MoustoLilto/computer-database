package main.java.com.excilys.computer.database.Exceptions;

public enum ErreurUtilisitauer {
	YEAR_LIMIT(1),
	DATE_PARSE(2),
	INTRODUCED_SUPERIOR(3),
	NUMBER_FORMAT(4);
	
	private int requete;
	
	ErreurUtilisitauer(int requete){
		this.requete = requete;
	}
	
	public int value() {
		return requete;
	}

}
