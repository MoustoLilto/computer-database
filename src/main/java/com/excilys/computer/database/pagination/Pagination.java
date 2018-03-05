package main.java.com.excilys.computer.database.pagination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.dto.MapperComputer;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

public class Pagination extends BodyTagSupport{
	private static final long serialVersionUID = 1L;
	MapperComputer mapperComputer = MapperComputer.getInstance();
	
	ServiceComputer serviceComputer = ServiceComputer.getService();
	int numPage;
	int nbreTuples;

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public void setNbreTuples(int nbreTuples) {
		this.nbreTuples = nbreTuples;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int nbrPages() {
		int bddNombre = ServiceComputer.getService().getNombre();
		int nbrPageMax = (int) Math.ceil(bddNombre/nbreTuples);
		return nbrPageMax;
	}
	
	public List<Computer> computersPage(int nbrPageMax) {
		int numTuple = 0;
		List<Computer> computersPage = null;
		if (numPage<1 || numPage>nbrPageMax) {
			computersPage = serviceComputer.getSomeComputers(0, 50);
		}
		else {
			numTuple= (numPage*nbreTuples+1)-nbreTuples;
			computersPage = serviceComputer.getSomeComputers(numTuple, nbreTuples);
		}
		return computersPage;
	}
	
	public List<Integer> compteurPage(int nbrPageMax){
		List<Integer> compteurPages = new ArrayList<Integer>();
		if ((numPage + 4) <= nbrPageMax) {
			for (int i=0; i<4; i++) {
				compteurPages.add(numPage+i);
			}
		}
		else if (numPage+4 > nbrPageMax) {
			for (int i=numPage; i<=nbrPageMax; i++) {
				compteurPages.add(i);
			}
		}
		return compteurPages;
	}
	
	public int doEndTag() throws JspException {
		try {
			int nbrPageMax = nbrPages();
			List<Computer> computers = computersPage(nbrPageMax);
			List<DTOComputer> allComputers = mapperComputer.listToDTO(computers);
			pageContext.getOut().println(nbreTuples + numPage);
			pageContext.setAttribute("allComputers", allComputers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
