package main.java.com.excilys.computer.database.dao;

public enum RequetesCompanySQL {
	
	ALL("SELECT id, name FROM company;"),
	NOMBRE("SELECT COUNT(*) FROM company;"),
	DETAIL("SELECT id, name FROM company WHERE id = ?;"),
	DELETE("DELETE FROM company WHERE id = ?;");
	
	private String requete = "";
	
	RequetesCompanySQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}