package tp.spring.config.ds;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.xa.PGXADataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import oracle.jdbc.xa.client.OracleXADataSource;

//@PropertySource("classpath:db/h2 or mysql or postgres or oracle /db.properties")in subclass
public abstract class   AbstractDataSourceConfig {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractDataSourceConfig.class);
	
	
	/*
	//@Bean(name="xy???zDataSource") selon sous-classe concrete
	public DataSource dataSourceToOverride(String jdbcDriver , String dbUrl, String dbUsername, String dbPassword) {
		//org.apache.commons.dbcp.BasicDataSource (si .jar de commons-dbcp , commons-pool)
		//org.springframework.jdbc.datasource.DriverManagerDataSource
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);
		
		return dataSource;
	}*/
	
	
	
	// @Bean(name="xy???zDataSource") selon sous-classe concrete
	public DataSource dataSourceToOverride(String jdbcDriver , String dbUrl, String dbUsername, String dbPassword) {
		//with hibernate-c3p0 in pom.xml
		ComboPooledDataSource dataSource = new com.mchange.v2.c3p0.ComboPooledDataSource();
		
		try {
			dataSource.setDriverClass(jdbcDriver);
			dataSource.setJdbcUrl(dbUrl);
			dataSource.setUser(dbUsername);
			dataSource.setPassword(dbPassword);
			dataSource.setMinPoolSize(1);
			dataSource.setAcquireIncrement(5);
			dataSource.setMaxPoolSize(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("**** DataSource with driver = "+jdbcDriver + " and url = " + dbUrl);
		return dataSource;
	}
	
	//Profile("jta") @Bean(name="xy???zDataSource",initMethod = "init", destroyMethod = "close")  selon sous-classe concrete
		public DataSource xaDataSourceToOverride(String xaDataSourceClass , String databaseName,String portNumber,String dbUrl,String serverName, String dbUsername, String dbPassword) {
			AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
			
			switch(xaDataSourceClass){
			case "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource":
				MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
				mysqlXaDataSource.setUrl(dbUrl);
				mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
				mysqlXaDataSource.setPassword(dbPassword);
				mysqlXaDataSource.setUser(dbUsername);
				xaDataSource.setXaDataSource(mysqlXaDataSource);
				break;
			case "org.h2.jdbcx.JdbcDataSource":
				org.h2.jdbcx.JdbcDataSource h2XaDataSource = new org.h2.jdbcx.JdbcDataSource();
				h2XaDataSource.setUrl(dbUrl);
				h2XaDataSource.setPassword(dbPassword);
				h2XaDataSource.setUser(dbUsername);
				xaDataSource.setXaDataSource(h2XaDataSource);
				break;
            case "org.postgresql.xa.PGXADataSource":
            	PGXADataSource pgXaDataSource = new PGXADataSource();
				pgXaDataSource.setDatabaseName(databaseName);
				pgXaDataSource.setPortNumber(Integer.parseInt(portNumber));
				pgXaDataSource.setServerName(serverName);
				pgXaDataSource.setPassword(dbPassword);
				pgXaDataSource.setUser(dbUsername);
				xaDataSource.setXaDataSource(pgXaDataSource);
				
				//NB: le serveur "posgresql" doit être configuré avec  max_prepared_transactions = 10 (ou autre valeur > 0)
				break;
            case "oracle.jdbc.xa.client.OracleXADataSource":
            	try {
					OracleXADataSource oracleXaDataSource = new OracleXADataSource();
					/*
					oracleXaDataSource.setDatabaseName(databaseName);
					oracleXaDataSource.setPortNumber(Integer.parseInt(portNumber));
					oracleXaDataSource.setServerName(serverName);*/
					oracleXaDataSource.setURL(dbUrl);
					oracleXaDataSource.setPassword(dbPassword);
					oracleXaDataSource.setUser(dbUsername);
					xaDataSource.setXaDataSource(oracleXaDataSource);
				} catch (SQLException e) {
						e.printStackTrace();
				}
            	break;
			}
			logger.debug("**** XADataSource (wrapped by atomikos) of class = "+xaDataSourceClass);
			xaDataSource.setUniqueResourceName(databaseName+"XaDataSource");
			return xaDataSource;
		}
		
    
}
