package test.java.dao;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.modele.Company;
import test.java.database.TestDatabaseActions;

public class DAOCompanyTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestDatabaseActions testDatabaseActions = TestDatabaseActions.getInstance();
		testDatabaseActions.initDatabaseCompany();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestDatabaseActions testDatabaseActions = TestDatabaseActions.getInstance();
		testDatabaseActions.dropCompanyDatabase();
	}
	
	@Test
	public void testGetCompany() {
		Company company = DAOCompany.getInstance().getCompany(0);
		
		Company company0 = new Company(0,"Apple Inc");
		assertEquals(company0, company);
	}

	@Test
	public void testGetAllCompany() {
		List<Company> companys = DAOCompany.getInstance().getAllCompany();
		assertEquals(3, companys.size());
		
		Company company0 = new Company(0,"Apple Inc");
		assertEquals(company0, companys.get(0));
		
		Company company2 = new Company(2,"Dell");
		assertEquals(company2, companys.get(2));
	}
}
