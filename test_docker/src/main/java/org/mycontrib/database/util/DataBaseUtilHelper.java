package org.mycontrib.database.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mycontrib.database.util.copy.of.org.apache.ibatis.io.Resources;
import org.mycontrib.database.util.copy.of.org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tp.standalone.boot.PreBoot;

public class DataBaseUtilHelper {
	
	private static Logger logger = LoggerFactory.getLogger(DataBaseUtilHelper.class) ;
	
	
	public static void runSqlScript(DataSource ds , String sqlScriptClassPath){
		try {
			Connection cn = ds.getConnection();
			runSqlScript(cn,sqlScriptClassPath);
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void runSqlScript(Connection cn , String sqlScriptClassPath){
		try {
			ScriptRunner runner = new ScriptRunner(cn);
			//runner.setLogWriter(null);runner.setErrorLogWriter(null);
			Reader reader = Resources.getResourceAsReader(sqlScriptClassPath);
			runner.runScript(reader);
			cn.commit();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean test_database_schema_initialization(DataSource ds,String mainTableName) {
		boolean res=true;
		Connection cn = null;
		 try {
			 cn = ds.getConnection();
			   ResultSet rs = cn.createStatement().executeQuery("select count(*) from " + mainTableName);
			   if(rs.next()){
				   logger.info("number of rows in  table " +mainTableName + " : " +rs.getString(1));
			   }
			   rs.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			res=false;
		}
		finally{
			try { cn.close(); } catch (SQLException e) {	}
		}
		return res;
	}

}
