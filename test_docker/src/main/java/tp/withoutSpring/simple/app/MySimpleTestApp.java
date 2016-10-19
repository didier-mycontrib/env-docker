package tp.withoutSpring.simple.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tp.util.PropertiesUtil;

public class MySimpleTestApp {
	
	private static Logger logger = LoggerFactory.getLogger(MySimpleTestApp.class);	

	private Connection cn; // à tester
	
	public static void main(String[] args) {
		
		MySimpleTestApp myApp = new MySimpleTestApp();
		//String dbPropertiesPath = "db/h2/db.properties";
		String dbPropertiesPath = "db/mysql/customers-db/db.properties";
		//String dbPropertiesPath = "db/postgres/db.properties";
		//String dbPropertiesPath = "db/oracle/db.properties";
		myApp.initJdbcConnection(dbPropertiesPath);
		myApp.testConnection();
		myApp.closeJdbcConnection();		
	}
	
	public void initJdbcConnection(String dbPropertiesPath){
		
		Properties props = PropertiesUtil.loadProperties(dbPropertiesPath);
		String jdbcDriver= props.getProperty("db.jdbcDriver");
		String url = props.getProperty("db.url");
		String username = props.getProperty("db.username");
		String password = props.getProperty("db.password");
		logger.debug("jdbcDriver:" +jdbcDriver );
		logger.debug("jdbc url:" +url );
		logger.debug("username/password:" +username+"/"+password );
		try {
			Class.forName(jdbcDriver);
			cn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void testConnection(){
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("select * from Customer");
			while(rs.next()){
				System.out.println("id="+rs.getLong("id") + " - name="+rs.getString("firstName") + " "  +rs.getString("lastName"));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeJdbcConnection(){
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getCn() {
		return cn;
	}

	public void setCn(Connection cn) {
		this.cn = cn;
	}
	
	

	
	

}
