package tp.standalone.boot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mycontrib.admin.util.DataBaseAdminHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tp.dao.customers.CustomerRepository;
import tp.dao.orders.OrderRepository;
import tp.entity.customers.Customer;
import tp.entity.orders.Order;
import tp.entity.orders.ProductRef;
import tp.service.OrderAndPurchaseService;
import tp.spring.config.DomainServiceConfig;

public class PreBoot {
	// le boot en mode console/text .
	
	/*
		DataBaseAdminHelper.createNewDatabase("jdbc:mysql://mysqlHost:3306/mysql", "test", "root", "root");
		//deux limitations autour:
		// a) securité : username="root" , password="root" devraient idéalement provenir d'une saisie confidentielle
		// b) tant que la nouvelle base (ici test) n'est pas construite , la config datasource spring (avec base 'test' ne démarre pas).
		
		// ==>à absolument placer dans une mini application paralèlle  (au minimum main() d'une autre classe java)
		// DANS tp.withoutSpring.simple.app.InitDataBaseMiniApp (pour l'instant)
	*/
	
	
	public static void main(String[] args) {
		// on prépare la configuration de l'application en mode spring-boot
		SpringApplication app = new SpringApplication(DomainServiceConfig.class);
		
		app.setWebEnvironment(false);
		//app.setLogStartupInfo(false);
		
		//setting profile (context.getEnvironment().setActiveProfiles("...") ) :
		app.setAdditionalProfiles("default" , "mysql"  , "no-jta"); 
		//app.setAdditionalProfiles("default" , "oracle" ); 
		//app.setAdditionalProfiles("default" , "h2" ); 
		
		// on lance l'application spring
		ConfigurableApplicationContext context =  app.run(args);
		
		try {
			 //test temporaire:
			
			   //DataSource ds = context.getBean(DataSource.class);
			  
			   DataSource ds1 = (DataSource) context.getBean("customersDataSource");
			   DataBaseAdminHelper.initDatabaseSchema(ds1, "mysql" , "customers");
			   DataBaseAdminHelper.insertInitialDefaultDataInDatabase(ds1, "customers");
			   Connection cn = ds1.getConnection();
			   ResultSet rs = cn.createStatement().executeQuery("select * from Customer");
			   if(rs.next()){
				   System.out.println("email of customer="+rs.getString("email"));
			   }
			   rs.close();cn.close();
			 
			
			   DataSource ds2 =  (DataSource) context.getBean("ordersDataSource");
			   DataBaseAdminHelper.initDatabaseSchema(ds2, "mysql" , "orders");
			   DataBaseAdminHelper.insertInitialDefaultDataInDatabase(ds2, "orders");
			  
			   DataSource ds3 =  (DataSource) context.getBean("purchasesDataSource");
			   DataBaseAdminHelper.initDatabaseSchema(ds3, "mysql" , "purchases");
			   DataBaseAdminHelper.insertInitialDefaultDataInDatabase(ds3, "purchases");
			 
			   //fin test temporaire

		 
		} catch (Exception ex) {
			System.err.println("Exception : " + ex.getMessage());
		}finally{
		// fermeture du contexte Spring
		context.close();
		}
	}

	// méthode utilitaire - affiche les éléments d'une collection
	private static <T> void display(String message, Iterable<T> elements) {
		System.out.println(message);
		for (T e : elements) {
			System.out.println("\t"+e);
		}
	}

}
