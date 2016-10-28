package tp.spring.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import tp.spring.config.DomainServiceConfig;

@Configuration
@Import( { DomainServiceConfig.class}) 
@ComponentScan(basePackages={"tp.web.mvc" }) 
@EnableWebMvc
public class MvcConfig /* extends WebMvcConfigurerAdapter*/ {
	
	//version standard (sans thymeleaf): 
	@Profile("!with-thymeleaf") //NB: @Profile("default") different de "aucun @Profile" et différent de @Profile("!with-thymeleaf")
	@Bean
    public ViewResolver viewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
	
	
	
	//version avec thymeleaf: 
	@Profile("with-thymeleaf")
	@Bean 
	ITemplateResolver thymeleafTemplateResolver(){
		ServletContextTemplateResolver templateResolver = new org.thymeleaf.templateresolver.ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".xhtml");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
		
	}
	@Profile("with-thymeleaf")
	@Bean  
	SpringTemplateEngine thymeleafTemplateEngine(org.thymeleaf.templateresolver.ITemplateResolver templateResolver){
		SpringTemplateEngine templateEngine = new org.thymeleaf.spring4.SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
	@Profile("with-thymeleaf")		
	@Bean
	public ViewResolver thymeleafViewResolver (org.thymeleaf.spring4.SpringTemplateEngine templateEngine) {
		    ThymeleafViewResolver viewResolver = new org.thymeleaf.spring4.view.ThymeleafViewResolver();
	        viewResolver.setOrder(1);
	        viewResolver.setTemplateEngine(templateEngine);
	        return viewResolver;
	}	
   
}
