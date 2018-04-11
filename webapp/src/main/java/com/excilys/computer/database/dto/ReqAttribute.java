package com.excilys.computer.database.dto;

public class ReqAttribute {
	public String orderBy;
	public String beforeOrderBy;
	public String order;
	public String nbreTuples;
	public String numeroPage;
	public String recherche;
	
	public ReqAttribute(String orderBy, String beforeOrderBy, String order, String nbreTuples, String numeroPage, String recherche) {
		this.orderBy = orderBy;
		this.beforeOrderBy = beforeOrderBy;
		this.order = order;
		this.nbreTuples = nbreTuples;
		this.numeroPage = numeroPage;
		this.recherche = recherche;
	}
	
	public ReqAttribute() {
	}
}
