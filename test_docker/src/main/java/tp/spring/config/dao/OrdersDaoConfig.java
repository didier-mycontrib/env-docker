package tp.spring.config.dao;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tp.spring.config.ds.DataSourceConfigOrdersDB;
import tp.spring.config.jpa.JpaConfig;
import tp.spring.config.jta.JtaConfig;

@Configuration
@Import( { DataSourceConfigOrdersDB.class , 
           JpaConfig.class , JtaConfig.class }) 
//@ImportResource("classpath:/xy.xml")
@EnableTransactionManagement() 
//@ComponentScan(basePackages={"tp.dao.jpa","org.mycontrib.generic"}) //to find and interpret @Autowired, @Inject , @Component , ...
@EnableJpaRepositories(
	    basePackages = "tp.dao.orders", 
	    entityManagerFactoryRef = "ordersEntityManagerFactory", 
	    transactionManagerRef = "ordersTransactionManager"
	)
@PropertySource("classpath:db/orders-db/db.properties")
public  class OrdersDaoConfig extends JpaConfig{
	
	@Value("${orders.db.type}")
	private String dbType;
	
	
	@Override
	public String jpaEntiyPackagesToScan() {
		return "tp.entity.orders";
	}
	

	@Bean("jpaVendorAdapterForOrdersDB")
	
	public JpaVendorAdapter jpaVendorAdapterForOrdersDB(){
		return jpaVendorAdapterToOverride(dbType);
	}
	
	
	@Bean("ordersEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory(@Qualifier("jpaVendorAdapterForOrdersDB") JpaVendorAdapter jpaVendorAdapter ,
			                                         @Qualifier("ordersDataSource")DataSource dataSource) {
		//dataSource eventuellement en mode "jta/xa" si profile "jta"
		return super.entityManagerFactoryToOverride(dbType,jpaVendorAdapter,dataSource,"orders");
	}
	
	// Transaction Manager for JPA or ...
	@Profile("!jta") 
	@Bean(name= { "ordersTransactionManager" , "transactionManager"})
	//default name is "transactionManager" for GenericDao (IL NE PEUT N'Y EN AVOIR QU'UN (dans ce profile) avec le nom "transactionManager")
	// other names are ALIAS for  SPRING-DATA : WITH OR WITHOUT JTA
	public PlatformTransactionManager transactionManager(@Qualifier("ordersEntityManagerFactory") 
	                                                     EntityManagerFactory entityManagerFactory) {
			return super.jpaTransactionManagerToOverride(entityManagerFactory);
	}
	
	
	@Profile("jta")
	@Bean(name={ "ordersTransactionManager" })
	//default name "transactionManager" is an alias for "springAtomikosJtaTransactionManager" : unique and global one  (JTA version) 
	// other names are ALIAS for DAO , SPRING-DATA : WITH OR WITHOUT JTA
	public PlatformTransactionManager jtaTransactionManager(@Qualifier("springAtomikosJtaTransactionManager") 
	                                     PlatformTransactionManager  jtaTransactionManager) {
			return jtaTransactionManager;
	}


	
}
