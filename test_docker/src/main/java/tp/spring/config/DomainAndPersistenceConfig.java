package tp.spring.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*  different uses:
 
   in main() :
   JavaConfigApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class, DataConfig.class);
    Service service = context.getBean(Service.class);
    
   in springContext.xml :
   <bean class="tp.myapp.minibank.impl.config.DomainAndPersistenceConfig"/>
   
   in spring test:
   @RunWith(SpringJUnit4ClassRunner.class)
	//@ContextConfiguration(locations="/springContextOfModule.xml") // xml config
	@ContextConfiguration(classes={tp.myapp.minibank.impl.config.DataSourceConfig.class,
		                       tp.myapp.minibank.impl.config.DomainAndPersistenceConfig.class}) //java config
   
   ...
 */



@Configuration
@Import(DataSourceConfig.class)
//@ImportResource("classpath:/xy.xml")
@EnableTransactionManagement() //"transactionManager" (not "txManager") is expected !!!
@ComponentScan(basePackages={"tp.dao.jpa","org.mycontrib.generic"}) //to find and interpret @Autowired, @Inject , @Component , ...
public class DomainAndPersistenceConfig {

	// JpaVendorAdapter (Hibernate ou OpenJPA ou ...)
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		//hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}

	// EntityManagerFactory
	@Bean
	public EntityManagerFactory entityManagerFactory(
			JpaVendorAdapter jpaVendorAdapter, DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan("tp.entity");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	// pour activer la prise en charge de @PersistentContext dans le code 
	@Bean
	public PersistenceAnnotationBeanPostProcessor enablePersistentContextAnnotation(){
		return new PersistenceAnnotationBeanPostProcessor();
	}

	// Transaction Manager for JPA or ...
	@Bean(name="transactionManager")//("transactionManager" but not "txManager")
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

}
