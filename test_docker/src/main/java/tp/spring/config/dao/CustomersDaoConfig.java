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

import tp.spring.config.ds.DataSourceConfigCustomersDB;
import tp.spring.config.jpa.JpaConfig;
import tp.spring.config.jta.JtaConfig;

@Configuration
@Import( { DataSourceConfigCustomersDB.class , 
           JpaConfig.class , JtaConfig.class }) 
//@ImportResource("classpath:/xy.xml")
@EnableTransactionManagement() 
//@ComponentScan(basePackages={"tp.dao.jpa","org.mycontrib.generic"}) //to find and interpret @Autowired, @Inject , @Component , ...
@EnableJpaRepositories(
	    basePackages = "tp.dao.customers", 
	    entityManagerFactoryRef = "customersEntityManagerFactory", 
	    transactionManagerRef = "customersTransactionManager"
	)
@PropertySource("classpath:db/customers-db/db.properties")
public  class CustomersDaoConfig extends JpaConfig{
	
	@Value("${customers.db.type}")
	private String dbType;
	
	
	@Override
	public String jpaEntiyPackagesToScan() {
		return "tp.entity.customers";
	}
	
	
	@Bean("jpaVendorAdapterForCustomersDB")
	public JpaVendorAdapter jpaVendorAdapterForCustomersDB(){
		return jpaVendorAdapterToOverride(dbType);
	}
	

	@Bean("customersEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory(  @Qualifier("jpaVendorAdapterForCustomersDB") JpaVendorAdapter jpaVendorAdapter ,
			                                           @Qualifier("customersDataSource")DataSource dataSource) {
		//dataSource eventuellement en mode "xa" si profile "xa"
		return super.entityManagerFactoryToOverride(dbType,jpaVendorAdapter ,dataSource,"customers");
	}
	
	// Transaction Manager for JPA or ...
	@Profile("!jta") //NB: @Profile("default") different de "pas de @Profile"
	//"no-jta" in "default" Profile
	@Bean(name= { "customersTransactionManager" /*, "transactionManager"*/} )
	//default name is "transactionManager" for GenericDao (IL NE PEUT N'Y EN AVOIR QU'UN (dans ce profile) avec le nom "transactionManager")
	// other names are ALIAS for  SPRING-DATA : WITH OR WITHOUT JTA
	public PlatformTransactionManager transactionManager(@Qualifier("customersEntityManagerFactory") 
	                                                     EntityManagerFactory entityManagerFactory) {
		    return super.jpaTransactionManagerToOverride(entityManagerFactory);
	}
	
	@Profile("jta")
	@Bean(name= {  "customersTransactionManager" })
	//default name "transactionManager" is an alias for "springAtomikosJtaTransactionManager" : unique and global one  (JTA version) 
	// other names are ALIAS for DAO , SPRING-DATA : WITH OR WITHOUT JTA
	public PlatformTransactionManager jtaTransactionManager(@Qualifier("springAtomikosJtaTransactionManager") 
	                                     PlatformTransactionManager  jtaTransactionManager) {
		   	return jtaTransactionManager;
	}
	
}
