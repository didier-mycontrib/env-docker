package tp.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tp.util.PropertiesUtil;

public class MyApp {
	
	private static Logger logger = LoggerFactory.getLogger(MyApp.class);	

	private Connection cn; // à tester
	
	public void initJdbcConnection(){
		//Properties props = PropertiesUtil.loadProperties("db/h2/db.properties");
		//Properties props = PropertiesUtil.loadProperties("db/mysql/db.properties");
		Properties props = PropertiesUtil.loadProperties("db/postgres/db.properties");
		//Properties props = PropertiesUtil.loadProperties("db/oracle/db.properties");
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
				System.out.println("id="+rs.getLong("id") + " - name="+rs.getString("name"));
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

	
	public static void main(String[] args) {
		
		MyApp myApp = new MyApp();
		myApp.initJdbcConnection();
		myApp.testConnection();
		myApp.closeJdbcConnection();
		
	}

}
