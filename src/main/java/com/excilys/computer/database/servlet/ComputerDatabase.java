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
import main.java.com.excilys.computer.database.dto.MapperCompany;
import main.java.com.excilys.computer.database.dto.MapperComputer;
import main.java.com.excilys.computer.database.dto.Validator;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

@WebServlet("/ComputerDatabase")
public class ComputerDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServiceComputer serviceComputer = ServiceComputer.getService();
	Validator validator = Validator.getIntsance();
	MapperCompany mapperCompany = MapperCompany.getInstance();
	MapperComputer mapperComputer = MapperComputer.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int numberOfRows = ServiceComputer.getService().getNombre();
		request.setAttribute("numberOfRows", numberOfRows);
		
		List<DTOComputer> allComputers = mapperComputer.listToDTO(ServiceComputer.getService().getAllComputer());
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
}
