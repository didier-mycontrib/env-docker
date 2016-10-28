package org.mycontrib.admin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.mycontrib.database.util.DataBaseUtilHelper;

public class DataBaseAdminHelper {
	
	public static void createNewDatabase(String defaultDatabaseUrl , String newDatabaseName , String username , String password){
	   String driverClassName=null;
	   String sqlStatement = null;
	   StringTokenizer st = new StringTokenizer(defaultDatabaseUrl,":");
	   st.nextToken();//jdbc:
	   String databaseType=st.nextToken();
	   switch(databaseType){
	   case "mysql":
		   sqlStatement="CREATE DATABASE IF NOT EXISTS " + newDatabaseName;
		   driverClassName="com.mysql.jdbc.Driver";
		   break;
	   case "postgresql" :
		   sqlStatement="CREATE DATABASE " + newDatabaseName;
		   driverClassName="org.postgresql.Driver";
		   break;
	   }
	   try {
		Class.forName(driverClassName);
		   Connection cn = DriverManager.getConnection(defaultDatabaseUrl, username, password);
		   Statement statement = cn.createStatement();
		   System.out.println("sqlStatement: " + sqlStatement);
		   statement.execute(sqlStatement);
		   statement.close();
		   cn.close();
		} catch (Exception e) {
			System.err.println("***** ERROR , DATABASE " + newDatabaseName + " NOT CREATED");
			e.printStackTrace();	
		}
	}
	
	
	public static void initDatabaseSchema(DataSource ds , String databaseType , String databaseName){
		//specifig/natives Sql
		String sqlScriptClassPath = "db/" + databaseName + "-db/" + databaseType + "/schema/database-schema.sql";
		DataBaseUtilHelper.runSqlScript(ds,sqlScriptClassPath);
		
	}
	
	public static void insertInitialDefaultDataInDatabase(DataSource ds , String databaseName ){
		//generic jdbc sql
		String sqlScriptClassPath = "db/" + databaseName +"-db/default-init-data.sql";
		DataBaseUtilHelper.runSqlScript(ds,sqlScriptClassPath);
	}
	
	

}
