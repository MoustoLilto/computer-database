package test.java;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import test.java.dao.DAOCompanyTest;
import test.java.dao.DAOComputerTest;


@RunWith(Suite.class)

@Suite.SuiteClasses({
   DAOCompanyTest.class,
   DAOComputerTest.class
})

public class TestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestRunner.class);
		System.out.println("Everything is good: "+result.wasSuccessful());
		
		if(!result.wasSuccessful()) {
			System.out.print("============== List Of the Failed Tests ==============\n");
			for (Failure failure : result.getFailures()) {
		    	System.out.println(failure.toString());
		    }
		}
	}
}
