package com.excilys.computer.database.dto;

import java.util.List;

import com.excilys.computer.database.core.modele.Computer;

public class Attribute {
	public int nbreTuples;
	public int numeroPage;
	public int nbrPageMax;
	public int numberOfRows;
	public int numTuple;
	public String orderBy;
	public String order;
	public String recherche;
	public List<Computer> computers = null;
	public List<DTOComputer> allComputers = null;
	
	public Attribute() {
		this.nbreTuples = 50;
		this.numeroPage = 1;
		this.nbrPageMax = 1;
		this.numberOfRows = 1;
		this.numTuple = 0;
		this.orderBy = "computer.id";
		this.order = "ASC";
		this.recherche = "";
		this.computers = null;
		this.allComputers = null;
	}
	public Attribute(int nbreTuples, int numeroPage, int nbrPageMax, int numberOfRows, int numTuple, String orderBy, String beforeOrderBy,
			String order, String recherche, List<Computer> computers, List<DTOComputer> allComputers) {
		this.nbreTuples = nbreTuples;
		this.numeroPage = numeroPage;
		this.nbrPageMax = nbrPageMax;
		this.numberOfRows = numberOfRows;
		this.numTuple = numTuple;
		this.orderBy = orderBy;
		this.order = order;
		this.recherche = recherche;
		this.computers = computers;
		this.allComputers = allComputers;
	}
	
	public int getNbreTuples() {
		return nbreTuples;
	}
	public void setNbreTuples(int nbreTuples) {
		this.nbreTuples = nbreTuples;
	}
	public int getNumeroPage() {
		return numeroPage;
	}
	public void setNumeroPage(int numeroPage) {
		this.numeroPage = numeroPage;
	}
	public int getNbrPageMax() {
		return nbrPageMax;
	}
	public void setNbrPageMax(int nbrPageMax) {
		this.nbrPageMax = nbrPageMax;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public int getNumTuple() {
		return numTuple;
	}
	public void setNumTuple(int numTuple) {
		this.numTuple = numTuple;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getRecherche() {
		return recherche;
	}
	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}
	public List<Computer> getComputers() {
		return computers;
	}
	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
	public List<DTOComputer> getAllComputers() {
		return allComputers;
	}
	public void setAllComputers(List<DTOComputer> allComputers) {
		this.allComputers = allComputers;
	}
}
