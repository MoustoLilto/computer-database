package main.java.com.excilys.computer.database.pagination;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LinksHandler extends BodyTagSupport{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(LinksHandler.class);	
	
	String destination;
	String ordreBy;
	String nbreTuple;
	String numPage;
	String recherche;
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setOrdreBy(String ordreBy) {
		this.ordreBy = ordreBy;
	}
	public void setNbreTuple(String nbreTuple) {
		this.nbreTuple = nbreTuple;
	}
	public void setNumPage(String numPage) {
		this.numPage = numPage;
	}
	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}
	
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		ServletRequest request = pageContext.getRequest();
		ServletResponse response = pageContext.getResponse();
		
		request.setAttribute("orderBy", ordreBy);
		request.setAttribute("tuples", nbreTuple);
		request.setAttribute("page", numPage);
		request.setAttribute("search", recherche);
		
		try {
			request.getRequestDispatcher(destination).forward(request,response);
		} catch (ServletException e) {
			logger.error("Erreur servlet avec la taglib de lien, erreur: " + e);
		} catch (IOException e) {
			logger.error("Erreur d'ecriture avec la taglib de lien, erreur: " + e);
		}
		return EVAL_PAGE;
	}
}
