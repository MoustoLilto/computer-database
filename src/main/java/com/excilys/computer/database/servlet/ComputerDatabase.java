package main.java.com.excilys.computer.database.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.exceptions.NumberFormatExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.PageLimitException;
import main.java.com.excilys.computer.database.exceptions.TuplesLimitException;
import main.java.com.excilys.computer.database.mapper.MapperCompany;
import main.java.com.excilys.computer.database.mapper.MapperComputer;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;
import main.java.com.excilys.computer.database.validator.Validator;

@WebServlet("/ComputerDatabase")
public class ComputerDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServiceComputer serviceComputer = ServiceComputer.getService();
	Validator validator = Validator.getIntsance();
	MapperCompany mapperCompany = MapperCompany.getInstance();
	MapperComputer mapperComputer = MapperComputer.getInstance();
	
	int nbreTuples = 50;
	int numeroPage = 1;
	int nbrPageMax = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> computers = null;
		List<DTOComputer> allComputers = null;
		int numberOfRows = 0;
		String recherche = request.getParameter("search");
		if (recherche != null && !recherche.equals("")) {
			computers = serviceComputer.seachComputers(recherche);
			allComputers = mapperComputer.listToDTO(computers);
			numberOfRows = allComputers.size();
		} else {
			numberOfRows = ServiceComputer.getService().getNombre();
		}
		request.setAttribute("numberOfRows", numberOfRows);
		
		String numPage = request.getParameter("page");
		String nbreTuple = request.getParameter("tuples");
		if ( nbreTuple!= null && !nbreTuple.equals("")) {
			try{
				validator.controleNbrTuples(nbreTuple, numberOfRows);
			}catch(NumberFormatExceptionCDB e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
				return;
			} catch(TuplesLimitException e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
				return;
			}
			numeroPage = 1;
		}
		nbreTuples = nbreTuple == null ? nbreTuples : Integer.parseInt(nbreTuple);
		
		nbrPageMax = (int) (Math.ceil(numberOfRows/nbreTuples)+1);
		if ( numPage!= null && !numPage.equals("")) {
			try{
				validator.controlePage(numPage, nbrPageMax);
			}catch(NumberFormatExceptionCDB e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/500.jsp").forward(request,response);
				return;
			} catch(PageLimitException e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/404.jsp").forward(request,response);
				return;
			}
		}
		numeroPage = numPage == null ? numeroPage : Integer.parseInt(numPage);
		
		request.setAttribute("numeroPage", numeroPage);
		request.setAttribute("nbrPageMax", nbrPageMax);
		
		if (recherche == null && recherche.equals("")) {
			int numTuple = 0;
			numTuple = (numeroPage*nbreTuples+1)-nbreTuples;
			computers = serviceComputer.getSomeComputers(numTuple, nbreTuples);
			allComputers = mapperComputer.listToDTO(computers);
			request.setAttribute("allComputers", allComputers);
		}
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
}
