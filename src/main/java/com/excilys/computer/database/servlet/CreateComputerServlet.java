package main.java.com.excilys.computer.database.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.computer.database.dto.DTOCompany;
import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.exceptions.DateTimeParseExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.IllegalCharacterException;
import main.java.com.excilys.computer.database.exceptions.IntroducedSuperiorException;
import main.java.com.excilys.computer.database.exceptions.NumberFormatExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.YearLimitException;
import main.java.com.excilys.computer.database.mapper.MapperCompany;
import main.java.com.excilys.computer.database.mapper.MapperComputer;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceCompany;
import main.java.com.excilys.computer.database.services.ServiceComputer;
import main.java.com.excilys.computer.database.validator.Validator;

@WebServlet("/CreateComputer")
public class CreateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServiceComputer serviceComputer = ServiceComputer.getService();
	Validator validator = Validator.getIntsance();
	MapperCompany mapperCompany = MapperCompany.getInstance();
	MapperComputer mapperComputer = MapperComputer.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(ServiceCompany.getService().getAllCompany());
		request.setAttribute("allCompanies", allCompanies);
		
		request.getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		//RECUPERATION DES DONNEES
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");;  
		String discontinued = request.getParameter("discontinued");; 
		String companyID = request.getParameter("companyId");
		
		//VERIFICATION DES DONNES
		try {
			validator.controleText(name);
			validator.controleDate(introduced);
			validator.controleDate(discontinued);
			validator.controleID(companyID);
		} catch(YearLimitException e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
			return;
		} catch(DateTimeParseExceptionCDB e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
			return;
		} catch(NumberFormatExceptionCDB e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
			return;
		} catch (IllegalCharacterException e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
			return;
		}
		
		if (!discontinued.equals("") && discontinued != null) {
			if(!introduced.equals("") && introduced != null) {
				try{
					validator.compareDate(LocalDate.parse(introduced, formatter), LocalDate.parse(discontinued, formatter)); 
				} catch(IntroducedSuperiorException e) {
					request.setAttribute("error", e.getMessage());
					doGet(request, response);
					return;
				}
			}
			else {
				IntroducedSuperiorException e = new IntroducedSuperiorException();
				request.setAttribute("error", e.getMessage());
				doGet(request, response);
				return;
			}
		}
		
		//CREATION DE NOTRE DTO
		DTOComputer dtoComputer = new DTOComputer();
		dtoComputer.setName(name);
		dtoComputer.setIntroduced(introduced);
		dtoComputer.setDiscontinued(discontinued);
		dtoComputer.setCompanyID(companyID);
		//System.out.println(dtoComputer);
		
		//CREATION DU COMPUTER ET AJOUT DANS LA BDD
		Computer computer = mapperComputer.toComputer(dtoComputer);
		//System.out.println(computer);
		serviceComputer.addComputer(computer);
		
		response.sendRedirect("ComputerDatabase");
	}
}
