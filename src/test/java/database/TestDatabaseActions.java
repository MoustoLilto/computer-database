package test.java.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import main.java.com.excilys.computer.database.dao.Connect;

public class TestDatabaseActions {
	Connection connection = Connect.getInstance().getConnection();
	final private static Logger logger = Logger.getLogger(TestDatabaseActions.class);
	
	private static TestDatabaseActions testDatabaseActions = null;
	
	private TestDatabaseActions() {
	}
	
	public static TestDatabaseActions getInstance() {
		if (testDatabaseActions == null) {
			testDatabaseActions = new TestDatabaseActions();
		}
		return testDatabaseActions;
	}
	
	public void initDatabaseCompany() {
		try {
			Statement statement = connection.createStatement();
			
			statement.execute(RequetesBaseTestSQL.CREATION_COMPANY.toString());
			connection.commit();
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPANY_1.toString());
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPANY_2.toString());
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPANY_3.toString());
			connection.commit();
		} catch (SQLException e) {
			logger.error("Erreur de création et de peuplement pour Company de test! erreur:" + e);
		}
	}
	
	public void initDatabaseComputer() {
		try {
			Statement statement = connection.createStatement();
			statement.execute(RequetesBaseTestSQL.CREATION_COMPUTER.toString());
			connection.commit();
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPUTER_1.toString());
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPUTER_2.toString());
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPUTER_3.toString());
			statement.executeUpdate(RequetesBaseTestSQL.INSERT_COMPUTER_4.toString());
			connection.commit();
		} catch (SQLException e) {
			logger.error("Erreur de création et de peuplement pour Computer de test! erreur:" + e);
		}
	}
	
	public void dropCompanyDatabase() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(RequetesBaseTestSQL.DELETION_COMPANY.toString());
			connection.commit();
		} catch (SQLException e) {
			logger.error("Erreur de suppression de la company dans BDD de test! erreur:" + e);
		}
	}
	
	public void dropComputerDatabase() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(RequetesBaseTestSQL.DELETION_COMPUTER.toString());
			connection.commit();
		} catch (SQLException e) {
			logger.error("Erreur de suppression de computer dans la BDD de test! erreur:" + e);
		}
	}
}
