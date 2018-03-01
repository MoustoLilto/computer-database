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
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceCompany;
import main.java.com.excilys.computer.database.services.ServiceComputer;

@WebServlet("/UpdateComputer")
public class UpdateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServiceComputer serviceComputer = ServiceComputer.getService();
	Validator validator = Validator.getIntsance();
	MapperCompany mapperCompany = MapperCompany.getInstance();
	MapperComputer mapperComputer = MapperComputer.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(ServiceCompany.getService().getAllCompany());
		request.setAttribute("allCompanies", allCompanies);
		
		String id = request.getParameter("id");
		//request.setAttribute("id", id);
		Computer computerBase = serviceComputer.detailComputer(Long.parseLong(id));
		DTOComputer dtoComputerBase = mapperComputer.toDTO(computerBase);
		request.setAttribute("dtoComputerBase", dtoComputerBase);
		request.getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		//RECUPERATION DES DONNEES
		String id = request.getParameter("id");
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");;  
		String discontinued = request.getParameter("discontinued");; 
		String companyID = request.getParameter("companyId");
		
		//VERIFICATION DES DONNES
		try {
			validator.controleID(id);
			validator.controleDate(introduced);
			validator.controleDate(discontinued);
			validator.controleID(companyID);
		} catch(YearLimitException e) {
			request.setAttribute("retry", ErreurUtilisitauer.YEAR_LIMIT.value());
			doGet(request, response);
			return;
		} catch(DateTimeParseException e) {
			request.setAttribute("retry", ErreurUtilisitauer.DATE_PARSE.value());
			doGet(request, response);
			return;
		} catch(NumberFormatException e) {
			request.setAttribute("retry", ErreurUtilisitauer.NUMBER_FORMAT.value());
			doGet(request, response);
			return;
		}
		
		if (!discontinued.equals("") && discontinued != null) {
			if(!introduced.equals("") && introduced != null) {
				try{
					validator.compareDate(LocalDate.parse(introduced, formatter), LocalDate.parse(discontinued, formatter)); 
				} catch(IntroducedSuperiorException e) {
					request.setAttribute("retry", ErreurUtilisitauer.INTRODUCED_SUPERIOR.value());
					doGet(request, response);
					return;
				}
			}
			else {
				request.setAttribute("retry", ErreurUtilisitauer.INTRODUCED_SUPERIOR.value());
				doGet(request, response);
				return;
			}
		}
		
		//CREATION DE NOTRE DTO
		DTOComputer dtoComputer = new DTOComputer();
		dtoComputer.setId(id);
		dtoComputer.setName(name);
		dtoComputer.setIntroduced(introduced);
		dtoComputer.setDiscontinued(discontinued);
		dtoComputer.setCompanyID(companyID);
		//System.out.println(dtoComputer);
		
		//CREATION DU COMPUTER ET AJOUT DANS LA BDD
		Computer computer = mapperComputer.toComputer(dtoComputer);
		//System.out.println(computer);
		serviceComputer.updateComputer(computer);
		
		response.sendRedirect("ComputerDatabase");
	}
}
