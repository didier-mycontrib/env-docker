package tp.standalone.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tp.spring.config.web.StandaloneWebAppConfigWithEmbbededTomcat;

public class BootWithEmbeddedWebApp {
	// le boot complet avec webApp prise en charge par tomcat embarqué .
	
	
	
	
	public static void main(String[] args) {
		if(PreBoot.testDatabasesBeforeLaunchingApp(true,true)==false){
			System.err.println("one database is not already initialized - application cannot start");
			System.exit(1);
		}
			
		// on prépare la configuration de l'application en mode spring-boot
		
	     SpringApplication app = new SpringApplication(StandaloneWebAppConfigWithEmbbededTomcat.class);
		
		
		app.setWebEnvironment(true);
		//app.setLogStartupInfo(false);
		
		//setting profile (context.getEnvironment().setActiveProfiles("...") ) :
		//app.setAdditionalProfiles( "jta" , "with-thymeleaf"); 
		// "no-jta" in "default" profile; 
		
		// on lance l'application spring
		ConfigurableApplicationContext context =  app.run(args);
		
		System.out.println("localhost:8080/testDockerSpringBootWeb");
		
	}

}
