package tp.standalone.boot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tp.dao.customers.CustomerRepository;
import tp.dao.orders.OrderRepository;
import tp.entity.customers.Customer;
import tp.entity.orders.Order;
import tp.entity.orders.ProductRef;
import tp.service.OrderAndPurchaseService;
import tp.spring.config.DomainServiceConfig;

public class Boot {
	// le boot en mode console/text .
	
	
	
	
	public static void main(String[] args) {
		// on prépare la configuration de l'application en mode spring-boot
		SpringApplication app = new SpringApplication(DomainServiceConfig.class);
		
		app.setWebEnvironment(false);
		//app.setLogStartupInfo(false);
		
		//setting profile (context.getEnvironment().setActiveProfiles("...") ) :
		app.setAdditionalProfiles("default" , "mysql"  , "jta"); 
		//app.setAdditionalProfiles("default" , "oracle" ); 
		//app.setAdditionalProfiles("default" , "h2" ); 
		
		// on lance l'application spring
		ConfigurableApplicationContext context =  app.run(args);
		
		try {
			
		// appels specifiques:
		
			/* 
			//ancienne version (sans spring-data et dao automatique :
			CustomerDAO customerDao = context.getBean(CustomerDAO.class);
			System.out.println("customer 1 (via dao): " + customerDao.getEntityById(1L).toString());
			Customer savedCustomer2 = customerDao.persistNewEntity(new Customer(null,"prenom2" , "nom2" , "nouvel_email2" , "0101010101" , null));
			System.out.println("savedCustomer (via dao): "+savedCustomer2.toString());
			System.out.println("reloded customer (via dao): " + customerDao.getEntityById(savedCustomer2.getId()).toString());
			*/
			
			CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
			System.out.println("customer  (via spring-data): " + customerRepository.findOne(1L).toString());
			
			Customer savedCustomer = customerRepository.save(new Customer(null,"prenom" , "nom" , "nouvel_email" , "0101010101" , null));
			System.out.println("savedCustomer (via spring-data): "+savedCustomer.toString());
			System.out.println("reloded customer (via spring-data): " + customerRepository.findOne(savedCustomer.getId()).toString());
		
			
			OrderRepository orderRepository = context.getBean(OrderRepository.class);
			System.out.println("order 1: " + orderRepository.findOne(1L).toString());
			
			OrderAndPurchaseService orderAndPurchaseService = context.getBean(OrderAndPurchaseService.class);
			List<ProductRef> listOfProductRef = new ArrayList<ProductRef>();
			listOfProductRef.add(new ProductRef(5L,"stylo bille noir " , 1.5));
			listOfProductRef.add(new ProductRef(6L,"cahier 48 pages " , 2.5));
			
			//a tester avec customerId=1L (existant) ou 999L (inexistant) , avec profile "jta" ou "no-jta"
			Long newOrderId = orderAndPurchaseService.purchaseOrder(1L, listOfProductRef);
			
			Order newOrder = orderRepository.findOne(newOrderId);
			System.out.println("new order : " + newOrder.toString());
			for(Integer numLine : newOrder.getOrderLines().keySet() ){
				System.out.println("\t" + numLine + ":" + newOrder.getOrderLines().get(numLine));
			}
			
		 
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
