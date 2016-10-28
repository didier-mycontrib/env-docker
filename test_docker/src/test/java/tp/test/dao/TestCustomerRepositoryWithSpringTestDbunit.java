package tp.test.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import tp.dao.customers.CustomerRepository;
import tp.entity.customers.Customer;
import tp.spring.config.dao.CustomersDaoConfig;

//@ActiveProfiles(profiles = {"test"})
@ActiveProfiles(profiles = {"mem-test"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomersDaoConfig.class})
@DbUnitConfiguration(databaseConnection={ "customersDataSource"})//default datasource used by dbUnit (with spring): dbUnitDatabaseConnection, dataSource
@TestExecutionListeners({  DependencyInjectionTestExecutionListener.class,
	/* TransactionalTestExecutionListener.class,*/
	/*TransactionDbUnitTestExecutionListener.class ,*/
        DbUnitTestExecutionListener.class})
//si chemin relatif , relatif vis à vis de la classe de test , si absolu : vis à vis classpath
@DatabaseSetup(value={"/dataset/initialDataSet.xml"},type=DatabaseOperation.CLEAN_INSERT)//déclenché avant chaque @Test (et l'odre est par défaut incontrolé)
public class TestCustomerRepositoryWithSpringTestDbunit {
	
	
	@Autowired
    private CustomerRepository customerDao;
     
    @Test
    public void testFindByEmail() {
    	Customer c = customerDao.findByEmail("didier@ici_ou_la");
    	System.out.println(c);
        Assert.assertTrue(c!=null);
    }
    
    @Test
    @ExpectedDatabase(value="/dataset/withNewCustomer.xml",assertionMode=DatabaseAssertionMode.NON_STRICT) 
    //NON_STRICT effectue une comparaison que sur les tables et colonnes indiquées dans le dataset 
    //(pratique si on ne connait pas la valeur qui sera auto_incr , limitation: chaque record xml doit idéalement avoir le même nombre de colonnes)
    public void testAddCustomer() {
    	Customer savedCustomer = customerDao.save(new Customer(null,"alain" , "therieur" , "alain.therieur@ici_ou_la" , "0102030405" , null));
		System.out.println("savedCustomer (via spring-data): "+savedCustomer.toString());
        Assert.assertTrue(savedCustomer!=null);
    }

}
