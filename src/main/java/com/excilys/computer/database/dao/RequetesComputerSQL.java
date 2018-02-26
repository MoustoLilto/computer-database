package main.java.com.excilys.computer.database.dao;

public enum RequetesComputerSQL {
	
	ALL("SELECT id, name, introduced, discontinued, company_id FROM computer;"),
	SOME("SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ? OFFSET ?;"),
	ADD("INSERT INTO computer (name, introduced, discontinued) VALUES (?, ?, ?);"),
	DELETE("DELETE FROM computer WHERE id = ?;"),
	DETAIL("SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?;"),
	UPDATE("UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?;"),
	NOMBRE("SELECT COUNT(*) FROM computer;");
	
	private String requete = "";
	
	RequetesComputerSQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}