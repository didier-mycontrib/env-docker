package tp.standalone.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tp.dao.CustomerDAO;
import tp.entity.Customer;
import tp.spring.config.DomainAndPersistenceConfig;

public class Boot {
	// le boot en mode console/text .
	public static void main(String[] args) {
		// on prépare la configuration de l'application en mode spring-boot
		SpringApplication app = new SpringApplication(DomainAndPersistenceConfig.class);
		
		app.setWebEnvironment(false);
		//app.setLogStartupInfo(false);
		
		// on lance l'application spring
		ConfigurableApplicationContext context = app.run(args);
		
		try {
		// appels specifiques:
			CustomerDAO customerDao = context.getBean(CustomerDAO.class);
		   /* //test temporaire:
		   DataSource ds = context.getBean(DataSource.class);
		   Connection cn = ds.getConnection();
		   cn.createStatement().executeUpdate("CREATE TABLE Client(numero integer primary key, nom varchar(64));");
		   cn.createStatement().executeUpdate("INSERT INTO Client values(1,'toto');");
		   
		   ResultSet rs = cn.createStatement().executeQuery("select * from Client");
		   if(rs.next()){
			   System.out.println("nom="+rs.getString("nom"));
		   }
		   */
			System.out.println("customer 1: " + customerDao.getEntityById(1L).toString());
			
			Customer savedCustomer = customerDao.persistNewEntity(new Customer(null,"new customer"));
			System.out.println("savedCustomer: "+savedCustomer.toString());
			System.out.println("reloded customer: " + customerDao.getEntityById(savedCustomer.getId()).toString());
		 
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
