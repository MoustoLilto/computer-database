package com.excilys.computer.database.dao;

public enum RequetesCompanySQL {
	
	ALL("SELECT id, name FROM company;");
	
	private String requete = "";
	
	RequetesCompanySQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}