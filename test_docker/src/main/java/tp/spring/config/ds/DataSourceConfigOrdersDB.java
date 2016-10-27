package tp.spring.config.ds;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:db/orders-db/db.properties")
//@Conditional({ MyXyCondition.class})
public class DataSourceConfigOrdersDB extends AbstractDataSourceConfig {
	
	/*
	@Value("${orders.db.type}")
	private String dbType; //mysql or h2 or postgres or oracle or ...
	*/ //not yet used here
	
	@Value("${orders.db.jdbcDriver}")
	private String jdbcDriver;
	
	@Value("${orders.db.xaDataSourceClass}")
	private String xaDataSourceClass;
	
	@Value("${orders.db.serverName}")
	private String serverName;
	
	@Value("${orders.db.portNumber}")
	private String portNumber;
	
	@Value("${orders.db.databaseName}")
	private String databaseName;
	
	@Value("${orders.db.url}")
	private String dbUrl; //complete jdbc url
	
	@Value("${orders.db.username}")
	private String dbUsername;
	
	@Value("${orders.db.password}")
	private String dbPassword;
	
	
	@Profile("default") //NB: @Profile("default") different de "pas de @Profile"
	//"no-jta" in "default" Profile
	@Bean(name="ordersDataSource")
	public DataSource dataSource() {
		return super.dataSourceToOverride(jdbcDriver ,dbUrl, dbUsername, dbPassword);
	}
	
	@Profile("jta")
	@Bean(name="ordersDataSource",initMethod = "init", destroyMethod = "close")
	public DataSource xaDataSource() {
			return super.xaDataSourceToOverride(xaDataSourceClass,databaseName,portNumber,
                dbUrl,serverName,dbUsername,dbPassword);
	}
}
