package main.java.com.excilys.computer.database.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.services.ServiceCompany;

@WebServlet("/CreateComputer")
public class CreateComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateComputerServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> allCompanies = ServiceCompany.getService().getAllCompany();
		request.setAttribute("allCompanies", allCompanies);
		
		
		request.getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");  
		String discontinued = request.getParameter("discontinued"); 
		String company_id = request.getParameter("companyId");
		
		DTOComputer dtoComputer = new DTOComputer();
		dtoComputer.addComputer(name, introduced, discontinued, company_id);
		
	}

}
