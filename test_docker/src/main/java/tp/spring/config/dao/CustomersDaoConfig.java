package tp.spring.config.dao;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tp.spring.config.ds.DataSourceConfigAllProfiles;
import tp.spring.config.jpa.JpaConfig;
import tp.spring.config.jta.JtaConfig;

@Configuration
@Import( { DataSourceConfigAllProfiles.class , 
           JpaConfig.class , JtaConfig.class }) 
//@ImportResource("classpath:/xy.xml")
@EnableTransactionManagement() //"transactionManager" (not "txManager") is expected !!!
//@ComponentScan(basePackages={"tp.dao.jpa","org.mycontrib.generic"}) //to find and interpret @Autowired, @Inject , @Component , ...
//@EnableJpaRepositories(basePackages = "tp.dao") //entityManagerFactory , transactionManager
@EnableJpaRepositories(
	    basePackages = "tp.dao.customers", 
	    entityManagerFactoryRef = "customersEntityManagerFactory", 
	    transactionManagerRef = "customersTransactionManager"
	)
public  class CustomersDaoConfig extends JpaConfig{
	
	
	
	@Override
	public String jpaEntiyPackagesToScan() {
		return "tp.entity.customers";
	}
	
	
	
	
	@Profile({"h2" , "mysql" , "postgres" })
	@Bean("customersEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory( @Qualifier("customersDataSource")DataSource dataSource) {
		//dataSource eventuellement en mode "xa" si profile "xa"
		return super.entityManagerFactoryToOverride(dataSource,"customers");
	}
	
	// Transaction Manager for JPA or ...
	@Profile("no-jta")
	@Bean(name= { "customersTransactionManager" , "transactionManager"} )
	//default name is "transactionManager" for GenericDao (IL NE PEUT N'Y EN AVOIR QU'UN (dans ce profile) avec le nom "transactionManager")
	// other names are ALIAS for  SPRING-DATA : WITH OR WITHOUT JTA
	public PlatformTransactionManager transactionManager(@Qualifier("customersEntityManagerFactory") 
	                                                     EntityManagerFactory entityManagerFactory) {
			return super.jpaTransactionManagerToOverride(entityManagerFactory);
	}
	
	@Profile("jta")
	@Bean(name= { "transactionManager" , "customersTransactionManager" })
	//default name "transactionManager" for unique and global one  (JTA version) 
	// other names are ALIAS for DAO , SPRING-DATA : WITH OR WITHOUT JTA
	public PlatformTransactionManager jtaPransactionManager(@Qualifier("springAtomikosJtaTransactionManager") 
	                                     PlatformTransactionManager  jtaTransactionManager) {
			return jtaTransactionManager;
	}
	
}
