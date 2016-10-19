package tp.spring.config.ds.mysql;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import tp.spring.config.ds.AbstractDataSourceConfig;

@Configuration
@PropertySource("classpath:db/mysql/customers-db/db.properties")
public class DataSourceConfigMysqlCustomersDB extends AbstractDataSourceConfig {
	
	@Profile("no-jta")
	@Bean(name="customersDataSource")
	public DataSource dataSource() {
		return super.dataSourceToOverride();
	}
	
	@Profile("jta")
	@Bean(name="customersDataSource",initMethod = "init", destroyMethod = "close")
	public DataSource xaDataSource() {
		return super.xaDataSourceToOverride("customersXaDataSource");
	}
}
