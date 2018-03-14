/*package test.java.dao;


import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.dao.DAOComputer;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;
import test.java.database.TestDatabaseActions;

@Component
public class DAOComputerTest {
	@Autowired
	TestDatabaseActions testDatabaseActions;
	@Autowired
	DAOComputer daoComputer;

	@Before
	public void setUp() throws Exception {
		testDatabaseActions.initDatabaseCompany();
		testDatabaseActions.initDatabaseComputer();
	}

	@After
	public void tearDown() throws Exception {
		testDatabaseActions.dropCompanyDatabase();
		testDatabaseActions.dropComputerDatabase();
	}
	
	@Test
	public void testGetAllComputer() {
		List<Computer> computers = daoComputer.getAllComputer();
		assertEquals(4, computers.size());
		
		Company company1 = new Company(1,"IBM");
		Computer computer0 = new Computer(0, "MacBook Pro 15.4 inch", null, null, company1);
		assertEquals(computer0, computers.get(0));
		
		Company company3 = new Company(2,"Dell");
		Computer computer2 = new Computer(2, "Monster Black", null, null, company3);
		assertEquals(computer2, computers.get(2));
	}

	@Test
	public void testGetSomeComputers() {
		List<Computer> computers = daoComputer.getSomeComputers(0, 2, "computer.id", "ASC");
		assertEquals(2, computers.size());
		
		Company company1 = new Company(1,"IBM");
		Computer computer0 = new Computer(0, "MacBook Pro 15.4 inch", null, null, company1);
		assertEquals(computer0, computers.get(0));
		
		Computer computer1 = new Computer(1, "MacBook retina", null, null, company1);
		assertEquals(computer1, computers.get(1));
	}

	@Test
	public void testAddComputer() {
		int nbrTuplesModifie = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		assertEquals(4, testDatabaseActions.getNombreComputer());
		
		Company company1 = new Company(1,"IBM");
		Computer computer4 = new Computer("OrdiTest", null, null, company1);
		nbrTuplesModifie = daoComputer.addComputer(computer4);
		assertEquals(5, testDatabaseActions.getNombreComputer());
		assertEquals(1, nbrTuplesModifie);
		
		Computer computer5 = new Computer("Test avec date valide", LocalDate.parse("13/02/1997", formatter), LocalDate.parse("13/05/2050", formatter), company1);
		nbrTuplesModifie = daoComputer.addComputer(computer5);
		assertEquals(6, testDatabaseActions.getNombreComputer());
		assertEquals(1, nbrTuplesModifie);
		
		Computer computer6 = new Computer("Test qui doit retourner Exception avec mysql!", LocalDate.parse("13/02/0001", formatter), LocalDate.parse("13/05/1975", formatter), company1);
		nbrTuplesModifie = daoComputer.addComputer(computer6);
		assertEquals(7, testDatabaseActions.getNombreComputer());
		assertEquals(1, nbrTuplesModifie);
	}

	@Test
	public void testRmComputer() {
		int nbrTuplesModifie = 0;
		
		assertEquals(4, testDatabaseActions.getNombreComputer());
		
		Company company1 = new Company(1,"IBM");
		Computer computer2 = new Computer(2, "Monster Black", null, null, company1);
		nbrTuplesModifie = daoComputer.rmComputer(computer2);
		assertEquals(3, testDatabaseActions.getNombreComputer());
		assertEquals(1, nbrTuplesModifie);
		
		Computer computer5 = new Computer(5, "Yolo", null, null, company1);
		nbrTuplesModifie = daoComputer.rmComputer(computer5);
		assertEquals(3, testDatabaseActions.getNombreComputer());
		assertEquals(0, nbrTuplesModifie);
	}

	@Test
	public void testDetailComputer() {
		Company company3 = new Company(2,"Dell");
		Computer computer2 = new Computer(2, "Monster Black", null, null, company3);
		assertEquals(computer2, daoComputer.detailComputer(2));
		
		assertEquals(null, daoComputer.detailComputer(5));
	}

	@Test
	public void testUpdateComputer() {
		int nbrTuplesModifie = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Company company1 = new Company(1,"IBM");
		Computer computer2 = new Computer(2, "Yolo", null, null, company1);
		nbrTuplesModifie = daoComputer.updateComputer(computer2);
		assertEquals(1, nbrTuplesModifie);
		
		Computer computer3 = new Computer(3, "Test avec date donnee!", LocalDate.parse("13/02/0001", formatter), LocalDate.parse("13/05/1975", formatter), company1);
		nbrTuplesModifie = daoComputer.updateComputer(computer3);
		assertEquals(1, nbrTuplesModifie);
		
		Computer computer5 = new Computer(6, "Test avec date valide", LocalDate.parse("13/02/1997", formatter), LocalDate.parse("13/05/2050", formatter), company1);
		nbrTuplesModifie = daoComputer.updateComputer(computer5);
		assertEquals(0, nbrTuplesModifie);
	}
}*/
