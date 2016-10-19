package tp.spring.config.ds;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tp.spring.config.ds.mysql.DataSourceConfigMysql;

@Configuration
@Import({ DataSourceConfigH2.class, 
	      DataSourceConfigMysql.class,
	      DataSourceConfigPostgres.class,
	      DataSourceConfigOracle.class
	      })
public class DataSourceConfigAllProfiles {

}
