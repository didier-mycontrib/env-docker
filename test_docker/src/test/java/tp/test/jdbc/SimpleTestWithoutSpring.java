package tp.test.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tp.withoutSpring.simple.app.MySimpleTestApp;



public class SimpleTestWithoutSpring {
	
	MySimpleTestApp myApp; // comportant cn à tester (indirectement)
		
	@Before
	public void initJdbcConnection(){

		myApp = new MySimpleTestApp();
		String dbPropertiesPath = "db/h2/db.properties";
		//String dbPropertiesPath = "db/mysql/db.properties";
		//String dbPropertiesPath = "db/postgres/db.properties";
		//String dbPropertiesPath = "db/oracle/db.properties";
		myApp.initJdbcConnection(dbPropertiesPath);
		
	}
	
	
	@Test
	public void testConnection(){
		Assert.assertNotNull(myApp.getCn());
		myApp.testConnection();
		
	}
	
	@After
	public void closeJdbcConnection(){
		myApp.closeJdbcConnection();
	}

}
