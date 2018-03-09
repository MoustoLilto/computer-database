package main.java.com.excilys.computer.database.dao;

public enum RequetesComputerSQL {
	
	ALL("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"),
	SOME("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"
			+ " ORDER BY ? ? LIMIT ? OFFSET ?;"),
	ADD("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);"),
	DELETE("DELETE FROM computer WHERE id = ?;"),
	DELETE_COMPANY("DELETE FROM computer WHERE company_id = ?;"),
	DETAIL("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"
			+ " WHERE computer.id = ?;"),
	UPDATE("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;"),
	NOMBRE("SELECT COUNT(*) FROM computer;"),
	SEARCH("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"
			+ " WHERE (computer.name LIKE ? OR company.name LIKE ?) LIMIT ? OFFSET ?;"),
	SEARCH_NOMBRE("SELECT COUNT(*) FROM computer LEFT JOIN company ON company_id = company.id WHERE (computer.name LIKE ? OR company.name LIKE ?);"),
	SOME_WITH_ORDER("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"
			+ " ORDER BY --- --- LIMIT ? OFFSET ?;"),
	SEARCH_WITH_ORDER("SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name FROM computer LEFT JOIN company ON company_id = company.id"
		+ " WHERE (computer.name LIKE ? OR company.name LIKE ?) ORDER BY --- --- LIMIT ? OFFSET ?;");
	private String requete = "";
	
	RequetesComputerSQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}