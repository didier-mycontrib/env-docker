package tp.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tp.spring.config.dao.CustomersDaoConfig;
import tp.spring.config.dao.OrdersDaoConfig;
import tp.spring.config.dao.PurchasesDaoConfig;
import tp.spring.config.jta.JtaConfig;

@Configuration
@Import( { CustomersDaoConfig.class,
	       OrdersDaoConfig.class,
	       PurchasesDaoConfig.class,
	       JtaConfig.class}) 
@EnableTransactionManagement() //"transactionManager" (not "txManager") is expected !!!
//plus besoin de @ComponentScan(basePackages={"..." , "tp.dao.customers.jpa"} depuis utilisation spring-data
@ComponentScan(basePackages={"tp.service.impl" }) //to find and interpret @Autowired, @Inject , @Component , ...
public class DomainServiceConfig {
	
	
	
}
