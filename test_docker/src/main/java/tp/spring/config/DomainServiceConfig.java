package tp.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tp.spring.config.dao.CustomersDaoConfig;
import tp.spring.config.dao.OrdersDaoConfig;
import tp.spring.config.dao.PurchasesDaoConfig;

@Configuration
@Import( { CustomersDaoConfig.class,
	       OrdersDaoConfig.class,
	       PurchasesDaoConfig.class}) 
@EnableTransactionManagement() //"transactionManager" (jta or no-jta) already configured in ...DaoConfig and JtaConfig
//plus besoin de @ComponentScan(basePackages={"..." , "tp.dao.customers.jpa"} depuis utilisation spring-data
@ComponentScan(basePackages={"tp.service.impl" }) //to find and interpret @Autowired, @Inject , @Component , ...
public class DomainServiceConfig {
	
	
	
}
