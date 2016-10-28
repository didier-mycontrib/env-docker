package tp.spring.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tp.spring.config.DomainServiceConfig;

@Configuration
@Import( {   MvcConfig.class , DomainServiceConfig.class}) 
public class WebAppConfig {
	
   
}
