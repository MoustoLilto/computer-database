package test.java.dao;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.com.excilys.computer.database.dao.DAOComputer;
import main.java.com.excilys.computer.database.modele.Computer;
import test.java.database.TestDatabaseActions;

public class DAOComputerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestDatabaseActions testDatabaseActions = TestDatabaseActions.getInstance();
		testDatabaseActions.initDatabaseComputer();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestDatabaseActions testDatabaseActions = TestDatabaseActions.getInstance();
		testDatabaseActions.dropComputerDatabase();
	}
	
	@Test
	public void testGetAllComputer() {
		List<Computer> computers = DAOComputer.getInstance().getAllComputer();
		assertEquals(4, computers.size());
		
		Computer computer1 = new Computer(1, "MacBook Pro 15.4 inch", null, null, 1);
		assertEquals(computer1, computers.get(0));
		
		Computer computer3 = new Computer(3, "Monster Black", null, null, 3);
		assertEquals(computer3, computers.get(2));
	}

	@Test
	public void testGetSomeComputers() {
		List<Computer> computers = DAOComputer.getInstance().getSomeComputers(0, 2);
		assertEquals(2, computers.size());
		
		Computer computer1 = new Computer(1, "MacBook Pro 15.4 inch", null, null, 1);
		assertEquals(computer1, computers.get(0));
		
		Computer computer2 = new Computer(2, "MacBook retina", null, null, 1);
		assertEquals(computer2, computers.get(1));
	}

	/*@Test
	public void testAddComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testRmComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testDetailComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateComputer() {
		fail("Not yet implemented");
	}*/

}
