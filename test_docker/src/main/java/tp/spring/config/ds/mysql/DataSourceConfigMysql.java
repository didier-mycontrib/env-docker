package tp.spring.config.ds.mysql;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import( { DataSourceConfigMysqlCustomersDB.class , 
	       DataSourceConfigMysqlOrdersDB.class ,
	       DataSourceConfigMysqlPurchasesDB.class  })
@Profile("mysql")
public class DataSourceConfigMysql {
	

}
