package tp.spring.config.ds;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:db/purchases-db/db.properties")
//@Conditional({ MyXyCondition.class})
public class DataSourceConfigPurchasesDB extends AbstractDataSourceConfig {
	
	/*
	@Value("${purchases.db.type}")
	private String dbType;
	*/ //not yet used here
	
	@Value("${purchases.db.jdbcDriver}")
	private String jdbcDriver;
	
	@Value("${purchases.db.xaDataSourceClass}")
	private String xaDataSourceClass;
	
	@Value("${purchases.db.serverName}")
	private String serverName;
	@Value("${purchases.db.portNumber}")
	private String portNumber;
	@Value("${purchases.db.databaseName}")
	private String databaseName;
	
	@Value("${purchases.db.url}")
	private String dbUrl;
	
	@Value("${purchases.db.username}")
	private String dbUsername;
	
	@Value("${purchases.db.password}")
	private String dbPassword;
	
	@Profile("default") //NB: @Profile("default") different de "pas de @Profile"
	//"no-jta" in "default" Profile
	@Bean(name="purchasesDataSource")
	public DataSource dataSource() {
			return super.dataSourceToOverride(jdbcDriver ,dbUrl, dbUsername, dbPassword);
	}
	
	@Profile("jta")
	@Bean(name="purchasesDataSource",initMethod = "init", destroyMethod = "close")
	public DataSource xaDataSource() {
		return super.xaDataSourceToOverride(xaDataSourceClass,databaseName,portNumber,
                dbUrl,serverName,dbUsername,dbPassword);
	}
}
