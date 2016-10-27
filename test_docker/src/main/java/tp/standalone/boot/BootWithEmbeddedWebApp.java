package tp.standalone.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tp.spring.config.web.StandaloneWebAppConfigWithEmbbededTomcat;

public class BootWithEmbeddedWebApp {
	// le boot complet avec webApp prise en charge par tomcat embarqué .
	
	
	
	
	public static void main(String[] args) {
		// on prépare la configuration de l'application en mode spring-boot
		
	     SpringApplication app = new SpringApplication(StandaloneWebAppConfigWithEmbbededTomcat.class);
		
		
		app.setWebEnvironment(true);
		//app.setLogStartupInfo(false);
		
		//setting profile (context.getEnvironment().setActiveProfiles("...") ) :
		//app.setAdditionalProfiles( "jta" , "with-thymeleaf"); 
		// "no-jta" in "default" profile; 
		
		// on lance l'application spring
		ConfigurableApplicationContext context =  app.run(args);
		
	}

	// méthode utilitaire - affiche les éléments d'une collection
	private static <T> void display(String message, Iterable<T> elements) {
		System.out.println(message);
		for (T e : elements) {
			System.out.println("\t"+e);
		}
	}

}
