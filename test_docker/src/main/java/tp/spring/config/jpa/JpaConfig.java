package tp.spring.config.jpa;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public abstract class JpaConfig {
	
	@Bean
	@Profile("h2")
	public Database dataBaseH2(){
		return Database.H2;
	}
	
	@Bean
	@Profile("mysql")
	public Database dataBaseMysql(){
		return Database.MYSQL;
	}
	@Bean
	@Profile("postgres")
	public Database dataBasePostgres(){
		return Database.POSTGRESQL;
	}
	@Bean
	@Profile("oracle")
	public Database dataBaseOracle(){
		return Database.ORACLE;
	}
	
	@Bean("mappingResourcesFilePaths")
	@Profile("oracle")
	public String mappingResourcesFilePathsForOracle(){
		return "META-INF/orm/oracle-orm.xml";
	}
	
	@Bean("mappingResourcesFilePaths")
	@Profile({"h2","mysql","postgres"})
	public String noMappingResourcesFilePaths(){
		return null;
	}
	
	@Autowired @Qualifier("mappingResourcesFilePaths")
	private String mappingResourcesFilePaths; //selon profile "oracle" ou autre

	// JpaVendorAdapter (Hibernate ou OpenJPA ou ...)
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(Database databaseOfProfile) {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(databaseOfProfile);//Database.H2 ou .MYSQL ou ...
		//hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect"); ???
		return hibernateJpaVendorAdapter;
	}
	
	@Autowired
	private JpaVendorAdapter selectedJpaVendorAdapter; //selon profile "h2" , "mysql" , "oracle" ou autre
	
	public abstract String jpaEntiyPackagesToScan();
	
		
	@Profile("no-jta")
	@Bean("jpaProperties")
	public Properties noJtaJpaProperties(){
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.transactionType" ,"RESOURCE_LOCAL");
		return jpaProperties;
	}
	
	@Profile("jta")
	@Bean("jpaProperties")
	public Properties jtaJpaProperties(){
		Properties jpaProperties = new Properties();
		jpaProperties.put("javax.persistence.transactionType" ,"JTA");
		jpaProperties.put("hibernate.transaction.jta.platform","org.mycontrib.generic.jta.jpa.atomikos.AtomikosJtaPlatform");
		return jpaProperties;
	}
	
	@Autowired @Qualifier("jpaProperties")
	private Properties selectedJpaProperties; //selon profile "jta" ou "non-jta"
	

	// EntityManagerFactory:
	public EntityManagerFactory entityManagerFactoryToOverride( DataSource dataSource , String persistenceUnitName ) {
			
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(selectedJpaVendorAdapter);
		if(mappingResourcesFilePaths!=null){
		    factory.setMappingResources(mappingResourcesFilePaths);
		}
		factory.setPackagesToScan(this.jpaEntiyPackagesToScan());           /* A AJUSTER DANS SOUS CLASSE !!! */
		factory.setPersistenceUnitName(persistenceUnitName);                /* A AJUSTER DANS SOUS CLASSE !!! */
		
		factory.setDataSource(dataSource);
		//factory.setJtaDataSource(dataSource); // set dataSource and set jpaProperties.put("javax.persistence.transactionType" ,"JTA");
		
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
