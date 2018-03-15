package main.java.com.excilys.computer.database.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.exceptions.IllegalCharacterException;
import main.java.com.excilys.computer.database.exceptions.NumberFormatExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.PageLimitException;
import main.java.com.excilys.computer.database.exceptions.TuplesLimitException;
import main.java.com.excilys.computer.database.exceptions.champInconnueException;
import main.java.com.excilys.computer.database.mapper.MapperComputer;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;
import main.java.com.excilys.computer.database.spring.WebSpringConfiguration;
import main.java.com.excilys.computer.database.validator.Validator;

@Controller
@WebServlet("/ComputerDatabase")
public class ComputerDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*private static final long serialVersionUID = 1L;
	private final ServiceComputer serviceComputer;
	private final Validator validator;
	private final MapperComputer mapperComputer;
	
	@Autowired
	public ComputerDatabase(ServiceComputer serviceComputer, Validator validator, MapperComputer mapperComputer) {
		this.serviceComputer = serviceComputer;
		this.validator = validator;
		this.mapperComputer = mapperComputer;
	}*/
	
	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private Validator validator;
	@Autowired
	private MapperComputer mapperComputer;
	
	private int nbreTuples = 50;
	private int numeroPage = 1;
	private int nbrPageMax = 1;
	private int numberOfRows = 1;
	private int numTuple = 0;
	private String orderBy = "computer.id";
	private String order = "ASC";
	
	public void orderManagement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, champInconnueException {
		String ordreBy = request.getParameter("orderBy");
		if ( ordreBy!= null && !ordreBy.equals("")) {
			if (!validator.controleAttribute(ordreBy)) {
				throw new champInconnueException();
			}
			if (ordreBy.equals(orderBy)) {
				orderBy = ordreBy;
				if (order.equals("ASC")) {
					order = "DESC";
				}
				else {
					order = "ASC";
				}
			} else {
				orderBy = ordreBy;
				order = "ASC";
			}
		}
	}
	
	public void nbrTupleManagement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatExceptionCDB, TuplesLimitException {
		String nbreTuple = request.getParameter("tuples");
		if ( nbreTuple!= null && !nbreTuple.equals("")) {
			try{
				validator.controleNbrTuples(nbreTuple, numberOfRows);
			} catch(NumberFormatExceptionCDB e) {
				throw new NumberFormatExceptionCDB();
			} catch(TuplesLimitException e) {
				throw new TuplesLimitException();
			}
			numeroPage = 1;
		}
		nbreTuples = nbreTuple == null ? nbreTuples : Integer.parseInt(nbreTuple);
	}
	
	public void numPageManagement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatExceptionCDB, PageLimitException {
		String numPage = request.getParameter("page");
		if ( numPage!= null && !numPage.equals("")) {
			try{
				validator.controlePage(numPage, nbrPageMax);
			} catch(NumberFormatExceptionCDB e) {
				throw new NumberFormatExceptionCDB();
			} catch(PageLimitException e) {
				throw new PageLimitException();
			}
		}
		numeroPage = numPage == null ? numeroPage : Integer.parseInt(numPage);
	}
	
	public void nbrPageMaxManagement() {
		nbrPageMax = (int) (Math.ceil((double)numberOfRows/(double)nbreTuples));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> computers = null;
		List<DTOComputer> allComputers = null;
		
		try {
			orderManagement(request, response);
		} catch (champInconnueException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
			return;
		}
		String recherche = request.getParameter("search");
		if (recherche != null && !recherche.equals("")) {
			try {
				validator.controleText(recherche);
			} catch (IllegalCharacterException e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
				return;
			}
			numberOfRows = serviceComputer.getSearchNumber(recherche);
			numeroPage = 1;
		} 
		else {
			numberOfRows = serviceComputer.getNombre();
		}
		try {
			nbrTupleManagement(request, response);
		} catch(NumberFormatExceptionCDB e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
			return;
		} catch(TuplesLimitException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
			return;
		}
		nbrPageMaxManagement();
		try {
			numPageManagement(request, response);
		} catch(NumberFormatExceptionCDB e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
			return;
		} catch(PageLimitException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/404.jsp").forward(request,response);
			return;
		}
		numTuple = (numeroPage*nbreTuples)-nbreTuples;
		
		if (recherche != null && !recherche.equals("")) {
			computers = serviceComputer.seachComputers(recherche, numTuple, nbreTuples, orderBy, order);
			allComputers = mapperComputer.listToDTO(computers);	
			request.setAttribute("search", recherche);
		}
		else {
			computers = serviceComputer.getSomeComputers((numTuple), nbreTuples,  orderBy, order);
			allComputers = mapperComputer.listToDTO(computers);
		}
		
		request.setAttribute("numberOfRows", numberOfRows);
		request.setAttribute("numeroPage", numeroPage);
		request.setAttribute("nbrPageMax", nbrPageMax);
		request.setAttribute("allComputers", allComputers);
		
		request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selection = request.getParameter("selection");		
		if (selection != null && !selection.equals("")) {
			List<String> computersIDString = new ArrayList<String>(Arrays.asList(selection.split(",")));
			for (String computerIDString : computersIDString) {
				long computerID = Long.parseLong(computerIDString);
				Computer computer = serviceComputer.detailComputer(computerID);
				if (computer != null) {
					serviceComputer.rmComputer(computer);
				}
			}
		}
		doGet(request, response);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(WebSpringConfiguration.class);
		vApplicationContext.getAutowireCapableBeanFactory().autowireBean(this);
	}
}
