package tp.spring.config.jpa;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public abstract class JpaConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer(); //to interpret ${} in @Value()
	}
	

	// JpaVendorAdapter (Hibernate ou OpenJPA ou ...)
	//@Bean
	public JpaVendorAdapter jpaVendorAdapterToOverride(String dbType) {
		Database databaseOfProfile=null;
		switch(dbType){
		case "mysql": databaseOfProfile = Database.MYSQL; 
			break;
		case "postgres": databaseOfProfile = Database.POSTGRESQL; 
		break;
		case "oracle": databaseOfProfile = Database.ORACLE; 
		break;
		case "h2":
		default:
			       databaseOfProfile = Database.MYSQL; //Le comportement de Database.H2 est volontairement très proche de Database.MYSQL; 
		}
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(databaseOfProfile);//Database.H2 ou .MYSQL ou ...
		return hibernateJpaVendorAdapter;
	}
	
	
	public abstract String jpaEntiyPackagesToScan();
	
	@Profile("!jta")
	@Bean("jpaProperties")
	public Properties noJtaJpaProperties(){
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.transactionType" ,"RESOURCE_LOCAL");
		jpaProperties.put("javax.persistence.validation.mode", "none");//no validation (hibernate-validator) on @Entity(ies)
		return jpaProperties;
	}
	
	@Profile("jta")
	@Bean("jpaProperties")
	public Properties jtaJpaProperties(){
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.validation.mode", "none");
		jpaProperties.put("javax.persistence.transactionType" ,"JTA");
		jpaProperties.put("hibernate.transaction.jta.platform","org.mycontrib.generic.jta.jpa.atomikos.AtomikosJtaPlatform");
		return jpaProperties;
	}
	
	@Autowired @Qualifier("jpaProperties")
	private Properties selectedJpaProperties; //selon profile "jta" ou "default/no-jta"
	
	@Profile("!mem-test")
	@Bean("ddlAutoHbjpaProperties")
	public Properties withoutDdlAutoHbjpaProperties(){
		Properties ddlAutoHbjpaProperties = new Properties();
		return ddlAutoHbjpaProperties; //empty property list
	}
	
	@Profile("mem-test")
	@Bean("ddlAutoHbjpaProperties")
	public Properties withDdlAutoHbjpaProperties(){
		Properties ddlAutoHbjpaProperties = new Properties();
		ddlAutoHbjpaProperties.put("hibernate.hbm2ddl.auto","update"); //"update" , "create-drop" , "none"
		return ddlAutoHbjpaProperties;
	}
	
	@Autowired @Qualifier("ddlAutoHbjpaProperties")
	private Properties selectedDdlAutoHbjpaProperties; //selon absence ou presence du profile "mem-test"
	

	// EntityManagerFactory:
	public EntityManagerFactory entityManagerFactoryToOverride(String dbType,JpaVendorAdapter jpaVendorAdapter, DataSource dataSource , String persistenceUnitName ) {
			
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		String mappingResourcesFilePaths=null;
		switch(dbType){
		case "oracle":
			mappingResourcesFilePaths="META-INF/orm/"+persistenceUnitName+"/oracle-orm.xml";
			break;
		case "mysql":	
		case "h2":
			mappingResourcesFilePaths="META-INF/orm/"+persistenceUnitName+"/mysql-orm.xml";
			break;
		default:
			mappingResourcesFilePaths=null;
		}
		if(mappingResourcesFilePaths!=null){
		    factory.setMappingResources(mappingResourcesFilePaths);
		}
		factory.setPackagesToScan(this.jpaEntiyPackagesToScan());           /* A AJUSTER DANS SOUS CLASSE !!! */
		factory.setPersistenceUnitName(persistenceUnitName);                /* A AJUSTER DANS SOUS CLASSE !!! */
		
		factory.setDataSource(dataSource);
		//factory.setJtaDataSource(dataSource); // set dataSource and set jpaProperties.put("javax.persistence.transactionType" ,"JTA");
		
		if(!selectedDdlAutoHbjpaProperties.isEmpty()){
			selectedJpaProperties.putAll(selectedDdlAutoHbjpaProperties);
		}
		
		factory.setJpaProperties(selectedJpaProperties);
		
			
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	
	// pour activer la prise en charge de @PersistentContext dans le code 
	// (pas indispensable si @ComponentScan également configuré)
	/*
	@Bean
	public PersistenceAnnotationBeanPostProcessor enablePersistentContextAnnotation(){
		return new PersistenceAnnotationBeanPostProcessor();
	}
	*/
	
	// Transaction Manager for JPA or ...
	public PlatformTransactionManager jpaTransactionManagerToOverride(EntityManagerFactory entityManagerFactory) {
				JpaTransactionManager txManager = new JpaTransactionManager();
				txManager.setEntityManagerFactory(entityManagerFactory);
				return txManager;
	}

	
}
