package com.excilys.computer.database.pagination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.computer.database.dto.Attribute;

public class PaginationHandler extends BodyTagSupport{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(PaginationHandler.class);	
	
	private Attribute attribute;
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public List<Integer> compteurPagePrecedent(){
		List<Integer> compteurPages = new ArrayList<Integer>();
		if ((attribute.numeroPage - 2) >= 1) {
			for (int i=-2; i<0; i++) {
				compteurPages.add(attribute.numeroPage+i);
			}
		}
		else if ((attribute.numeroPage - 1) == 1) {
			compteurPages.add(1);
		}
		return compteurPages;
	}
	
	public List<Integer> compteurPageSuivant(){
		List<Integer> compteurPages = new ArrayList<Integer>();
		if ((attribute.numeroPage + 3) <= attribute.nbrPageMax) {
			for (int i=1; i<3; i++) {
				compteurPages.add(attribute.numeroPage+i);
			}
		}
		else if ((attribute.numeroPage + 3) > attribute.nbrPageMax) {
			for (int i=attribute.numeroPage+1; i<=attribute.nbrPageMax; i++) {
				compteurPages.add(i);
			}
		}
		return compteurPages;
	}
	
	public int doEndTag() throws JspException {
		try {
			List<Integer> compteursPrecedent = compteurPagePrecedent();
			List<Integer> compteursSuivant = compteurPageSuivant();
			
			if (attribute.numeroPage >1) {
				pageContext.getOut().print("<li>\n" + 
						"					 <a href=\"dashboard?tuples="+attribute.nbreTuples+"&search="+attribute.recherche+"&page="+1+"&orderBy="+attribute.orderBy+"&order="+attribute.order+"\" aria-label=\"Previous\">\n" + 
						"                      <span aria-hidden=\"true\">&laquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
				pageContext.getOut().print("<li>\n" + 
						"					 <a href=\"dashboard?tuples="+attribute.nbreTuples+"&search="+attribute.recherche+"&page="+(attribute.numeroPage-1)+"&orderBy="+attribute.orderBy+"&order="+attribute.order+"\" aria-label=\"Previous\">\n" +
						"                      <span aria-hidden=\"true\">&lsaquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
			}
			
			for (int compteur : compteursPrecedent) {
				pageContext.getOut().print("<li><a href=\"dashboard?tuples="+attribute.nbreTuples+"&search="+attribute.recherche+"&page="+compteur+"&orderBy="+attribute.orderBy+"&order="+attribute.order+"\">"+compteur+"</a></li>");
			}
			
			pageContext.getOut().print("<li> <a href=\"#\"/>["+attribute.numeroPage+"]</a></li>");
			
			for (int compteur : compteursSuivant) {
				pageContext.getOut().print("<li><a href=\"dashboard?tuples="+attribute.nbreTuples+"&search="+attribute.recherche+"&page="+compteur+"&orderBy="+attribute.orderBy+"&order="+attribute.order+"\">"+compteur+"</a></li>");
			}
			
			if (attribute.numeroPage < attribute.nbrPageMax) {
				pageContext.getOut().print("<li>\n" + 
						"                    <a href=\"dashboard?tuples="+attribute.nbreTuples+"&search="+attribute.recherche+"&page="+(attribute.numeroPage+1)+"&orderBy="+attribute.orderBy+"&order="+attribute.order+"\" aria-label=\"Next\">\n" + 
						"                      <span aria-hidden=\"true\">&rsaquo;</span>\n" + 
						"                  </a>\n" + 
						"              </li>");
			
				pageContext.getOut().print("<li>\n" + 
						"                    <a href=\"dashboard?tuples="+attribute.nbreTuples+"&search="+attribute.recherche+"&page="+attribute.nbrPageMax+"&orderBy="+attribute.orderBy+"&order="+attribute.order+"\" aria-label=\"Next\">\n" + 
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
