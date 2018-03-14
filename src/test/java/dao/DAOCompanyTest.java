/*package test.java.dao;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.modele.Company;
import test.java.database.TestDatabaseActions;

@Component
public class DAOCompanyTest {
	@Autowired
	TestDatabaseActions testDatabaseActions;
	@Autowired
	DAOCompany daoCompany;
	
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
	public void testGetCompany() {
		Company company = daoCompany.getCompany(0);
		
		Company company0 = new Company(0,"Apple Inc");
		assertEquals(company0, company);
	}

	@Test
	public void testGetAllCompany() {
		List<Company> companys = daoCompany.getAllCompany();
		assertEquals(3, companys.size());
		
		Company company0 = new Company(0,"Apple Inc");
		assertEquals(company0, companys.get(0));
		
		Company company2 = new Company(2,"Dell");
		assertEquals(company2, companys.get(2));
	}
	
	@Test
	public void testRmCompany() {
		int nbrTuplesModifie = 0;
		
		assertEquals(3, testDatabaseActions.getNombreCompany());
		
		Company company0 = new Company(0,"Apple Inc");
		nbrTuplesModifie = daoCompany.rmCompany(company0);
		assertEquals(2, testDatabaseActions.getNombreCompany());
		assertEquals(1, nbrTuplesModifie);
		
		Company company2 = new Company(2,"Dell");
		nbrTuplesModifie = daoCompany.rmCompany(company2);
		assertEquals(1, testDatabaseActions.getNombreCompany());
		assertEquals(3, nbrTuplesModifie);
		
		Company company5 = new Company(5,"Yolo");
		nbrTuplesModifie = daoCompany.rmCompany(company5);
		assertEquals(1, testDatabaseActions.getNombreCompany());
		assertEquals(0, nbrTuplesModifie);
	}
}*/
