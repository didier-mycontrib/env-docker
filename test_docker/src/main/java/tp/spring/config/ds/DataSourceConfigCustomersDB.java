package tp.spring.config.ds;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:db/customers-db/db.properties")
//@Conditional({ MyXyCondition.class})
public class DataSourceConfigCustomersDB extends AbstractDataSourceConfig {
	
	/*
	@Value("${customers.db.type}")
	private String dbType;
	*/ //not yet used here
	
	@Value("${customers.db.jdbcDriver}")
	private String jdbcDriver;
	
	@Value("${customers.db.xaDataSourceClass}")
	private String xaDataSourceClass;
	
	@Value("${customers.db.serverName}")
	private String serverName;
	
	@Value("${customers.db.portNumber}")
	private String portNumber;
	
	@Value("${customers.db.databaseName}")
	private String databaseName;
	
	@Value("${customers.db.url}")
	private String dbUrl;
	
	@Value("${customers.db.username}")
	private String dbUsername;
	
	@Value("${customers.db.password}")
	private String dbPassword;
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer(); //to interpret ${} in @Value()
	}
	
	@Profile("no-jta")
	@Bean(name="customersDataSource")
	public DataSource dataSource() {
		return super.dataSourceToOverride(jdbcDriver ,dbUrl, dbUsername, dbPassword);
	}
	
	@Profile("jta")
	@Bean(name="customersDataSource",initMethod = "init", destroyMethod = "close")
	public DataSource xaDataSource() {
		return super.xaDataSourceToOverride(xaDataSourceClass,databaseName,portNumber,
				                            dbUrl,serverName,dbUsername,dbPassword);
	}
}
