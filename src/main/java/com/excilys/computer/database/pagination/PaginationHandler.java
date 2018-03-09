package main.java.com.excilys.computer.database.pagination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PaginationHandler extends BodyTagSupport{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(PaginationHandler.class);	
	
	int numPage;
	int nbrPageMax;
	String recherche;
	
	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public void setNbrPageMax(int nbrPageMax) {
		this.nbrPageMax = nbrPageMax;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public List<Integer> compteurPagePrecedent(){
		List<Integer> compteurPages = new ArrayList<Integer>();
		if ((numPage - 2) >= 1) {
			for (int i=-2; i<0; i++) {
				compteurPages.add(numPage+i);
			}
		}
		else if ((numPage - 1) == 1) {
			compteurPages.add(1);
		}
		return compteurPages;
	}
	
	public List<Integer> compteurPageSuivant(){
		List<Integer> compteurPages = new ArrayList<Integer>();
		if ((numPage + 3) <= nbrPageMax) {
			for (int i=1; i<3; i++) {
				compteurPages.add(numPage+i);
			}
		}
		else if ((numPage + 3) > nbrPageMax) {
			for (int i=numPage+1; i<=nbrPageMax; i++) {
				compteurPages.add(i);
			}
		}
		return compteurPages;
	}
	
	public int doEndTag() throws JspException {
		try {
			List<Integer> compteursPrecedent = compteurPagePrecedent();
			List<Integer> compteursSuivant = compteurPageSuivant();
			
			//CHEVRONS
			if (numPage >1) {
				pageContext.getOut().print("<li>\n" + 
						"                    <a href=\"ComputerDatabase?page="+1+"&search="+recherche+"\" aria-label=\"Previous\">\n" + 
						"                      <span aria-hidden=\"true\">&laquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
				pageContext.getOut().print("<li>\n" + 
						"                    <a href=\"ComputerDatabase?page="+(numPage-1)+"&search="+recherche+"\" aria-label=\"Previous\">\n" + 
						"                      <span aria-hidden=\"true\">&lsaquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
			}
			//PAGES
			for (int compteur : compteursPrecedent) {
				pageContext.getOut().print("<li><a href=\"ComputerDatabase?page="+compteur+"&search="+recherche+"\">"+compteur+"</a></li>");
			}
			
			pageContext.getOut().print("<li><a href=\"ComputerDatabase?page="+numPage+"&search="+recherche+"\">"+"["+numPage+"]"+"</a></li>");
			
			for (int compteur : compteursSuivant) {
				pageContext.getOut().print("<li><a href=\"ComputerDatabase?page="+compteur+"&search="+recherche+"\">"+compteur+"</a></li>");
			}
			
			//CHEVRONS 
			if (numPage < nbrPageMax) {
				pageContext.getOut().print("<li>\n" + 
						"                    <a href=\"ComputerDatabase?page="+(numPage+1)+"&search="+recherche+"\" aria-label=\"Next\">\n" + 
						"                      <span aria-hidden=\"true\">&rsaquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
			
				pageContext.getOut().print("<li>\n" + 
						"                    <a href=\"ComputerDatabase?page="+nbrPageMax+"&search="+recherche+"\" aria-label=\"Next\">\n" + 
						"                      <span aria-hidden=\"true\">&raquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
			}
		} catch (IOException e) {
			logger.error("Erreur d'ecriture avec la taglib de pagination, erreur: " + e);
		}
		return EVAL_PAGE;
	}
}
