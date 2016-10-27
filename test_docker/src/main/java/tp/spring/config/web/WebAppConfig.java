package tp.spring.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tp.spring.config.DomainServiceConfig;

@Configuration
@Import( { DomainServiceConfig.class , MvcConfig.class}) 
public class WebAppConfig {
	
   
}
