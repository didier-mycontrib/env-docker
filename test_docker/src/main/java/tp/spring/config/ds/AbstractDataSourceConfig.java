package tp.spring.config.ds;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

//@PropertySource("classpath:db/h2 or mysql or postgres or oracle /db.properties")in subclass
public abstract class   AbstractDataSourceConfig {
	
	@Value("${db.jdbcDriver}")
	private String jdbcDriver;
	
	@Value("${db.xaDataSourceClass}")
	private String xaDataSourceClass;
	
	@Value("${db.url}")
	private String dbUrl;
	
	@Value("${db.username}")
	private String dbUsername;
	
	@Value("${db.password}")
	private String dbPassword;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer(); //to interpret ${} in @Value()
	}

	/*
	//@Bean(name="xy???zDataSource") selon sous-classe concrete
	public DataSource dataSourceToOverride() {
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
	public DataSource dataSourceToOverride() {
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
		
		return dataSource;
	}
	
	//Profile("jta") @Bean(name="xy???zDataSource",initMethod = "init", destroyMethod = "close")  selon sous-classe concrete
		public DataSource xaDataSourceToOverride(String uniqueXaResourceName) {
			AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
			
			if(xaDataSourceClass.equals("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource")){
				MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
				mysqlXaDataSource.setUrl(dbUrl);
				mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
				mysqlXaDataSource.setPassword(dbPassword);
				mysqlXaDataSource.setUser(dbUsername);
				xaDataSource.setXaDataSource(mysqlXaDataSource);
			}
			
			xaDataSource.setUniqueResourceName(uniqueXaResourceName);
			return xaDataSource;
		}
		
     /*
      
      @Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(this.environment.getProperty("dataSource.url"));
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(this.environment.getProperty("dataSource.password"));
		mysqlXaDataSource.setUser(this.environment.getProperty ("dataSource.password"));
		//dataSource.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads");
		return xaDataSource;
	}
			
      */

}
