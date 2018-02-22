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
	public void testGetAllCompany() {
		List<Company> companys = DAOCompany.getInstance().getAllCompany();
		assertEquals(3, companys.size());
		
		Company company1 = new Company(1,"Apple Inc");
		assertEquals(company1, companys.get(0));
		
		Company company3 = new Company(3,"Dell");
		assertEquals(company3, companys.get(2));
	}
}
