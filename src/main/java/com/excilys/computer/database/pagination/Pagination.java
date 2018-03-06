package main.java.com.excilys.computer.database.pagination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class Pagination extends BodyTagSupport{
	private static final long serialVersionUID = 1L;
	
	int numPage;
	int nbrPageMax;

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public void setNbreTuples(int nbrPageMax) {
		this.nbrPageMax = nbrPageMax;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public List<Integer> compteurPage(){
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
			List<Integer> compteurs = compteurPage();
			for (int compteur : compteurs) {
				pageContext.getOut().print("<li><a href=\"ComputerDatabase?page="+compteur+"\">"+compteur+"</a></li>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
