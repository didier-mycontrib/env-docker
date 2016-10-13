package tp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tp.util.PropertiesUtil;



public class TestMySqlConnectionWithDocker {
	
	
	private Connection cn; // à tester
	
	@Before
	public void initJdbcConnection(){

		Properties props = PropertiesUtil.loadProperties("db/h2/db.properties");
		String jdbcDriver= props.getProperty("db.jdbcDriver");
		String url = props.getProperty("db.url");
		String username = props.getProperty("db.username");
		String password = props.getProperty("db.password");
		try {
			Class.forName(jdbcDriver);
			cn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testConnection(){
		Assert.assertNotNull(cn);
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Customer");
			while(rs.next()){
				System.out.println("id="+rs.getLong("id") + " - name="+rs.getString("name"));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@After
	public void closeJdbcConnection(){
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
