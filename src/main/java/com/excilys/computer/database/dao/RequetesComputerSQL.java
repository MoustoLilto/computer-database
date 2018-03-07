package main.java.com.excilys.computer.database.dao;

public enum RequetesComputerSQL {
	
	//ALL("SELECT id, name, introduced, discontinued, company_id FROM computer;"),
	ALL("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"),
	SOME("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id LIMIT ? OFFSET ?;"),
	ADD("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);"),
	DELETE("DELETE FROM computer WHERE id = ?;"),
	DETAIL("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?;"),
	UPDATE("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;"),
	NOMBRE("SELECT COUNT(*) FROM computer;"),
	SEARCH("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id WHERE (computer.name LIKE ? OR company.name LIKE ?;");
	
	private String requete = "";
	
	RequetesComputerSQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}