package main.java.com.excilys.computer.database.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.computer.database.Exceptions.ErreurUtilisitauer;
import main.java.com.excilys.computer.database.Exceptions.IntroducedSuperiorException;
import main.java.com.excilys.computer.database.Exceptions.YearLimitException;
import main.java.com.excilys.computer.database.dto.DTOCompany;
import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.dto.MapperCompany;
import main.java.com.excilys.computer.database.dto.MapperComputer;
import main.java.com.excilys.computer.database.dto.Validator;
import main.java.com.excilys.computer.database.services.ServiceCompany;

@WebServlet("/CreateComputer")
public class CreateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
			validator.controleDate(introduced);
			validator.controleDate(discontinued);
			validator.controleID(companyID);
		} catch(YearLimitException e) {
			request.setAttribute("retry", ErreurUtilisitauer.YEAR_LIMIT.value());
			doGet(request, response);
		} catch(DateTimeParseException e) {
			request.setAttribute("retry", ErreurUtilisitauer.DATE_PARSE.value());
			doGet(request, response);
		} catch(NumberFormatException e) {
			request.setAttribute("retry", ErreurUtilisitauer.NUMBER_FORMAT.value());
			doGet(request, response);
		}
		
		if (!introduced.equals("") && introduced != null && !discontinued.equals("") && discontinued != null) {
			try{
				validator.compareDate(LocalDate.parse(introduced, formatter), LocalDate.parse(discontinued, formatter)); 
			} catch(IntroducedSuperiorException e) {
				request.setAttribute("retry", ErreurUtilisitauer.INTRODUCED_SUPERIOR.value());
				doGet(request, response);
			}
		}
		
		//CREATION DE NOTRE DTO
		DTOComputer dtoComputer = new DTOComputer();
		dtoComputer.setName(name);
		dtoComputer.setIntroduced(introduced);
		dtoComputer.setDiscontinued(discontinued);
		dtoComputer.setCompanyID(companyID);
		
		System.out.println(dtoComputer);
		response.sendRedirect("ComputerDatabase");
	}
}
