package org.mycontrib.database.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

public class DataBaseUtilHelper {
	
	
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

}
