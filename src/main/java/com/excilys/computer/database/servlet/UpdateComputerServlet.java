package main.java.com.excilys.computer.database.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import main.java.com.excilys.computer.database.spring.SpringConfiguration;
import main.java.com.excilys.computer.database.validator.Validator;

@WebServlet("/UpdateComputer")
public class UpdateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*private final ServiceComputer serviceComputer;
	private final ServiceCompany serviceCompany;
	private final Validator validator;
	private final MapperCompany mapperCompany;
	private final MapperComputer mapperComputer;
	
	@Autowired
	public UpdateComputerServlet(ServiceComputer serviceComputer, ServiceCompany serviceCompany, Validator validator,
			MapperCompany mapperCompany, MapperComputer mapperComputer) {
		super();
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.validator = validator;
		this.mapperCompany = mapperCompany;
		this.mapperComputer = mapperComputer;
	}*/
	
	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	private Validator validator;
	@Autowired
	private MapperCompany mapperCompany;
	@Autowired
	private MapperComputer mapperComputer;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(serviceCompany.getAllCompany());
		request.setAttribute("allCompanies", allCompanies);
		
		String id = request.getParameter("id");
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
			validator.controleText(name);
			validator.controleID(id);
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
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		@SuppressWarnings("resource")
		ApplicationContext vApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		vApplicationContext.getAutowireCapableBeanFactory().autowireBean(this);
	}
}
