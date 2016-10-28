package tp.test.dao;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.mycontrib.generic.test.GenericSpringDataJpaTestWithDbUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tp.dao.customers.CustomerRepository;
import tp.entity.customers.Customer;
import tp.spring.config.dao.CustomersDaoConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles(profiles = {"mem-test"})
@ActiveProfiles(profiles = {"test" , "jta"})
@ContextConfiguration(classes={ CustomersDaoConfig.class})
public class TestCustomerDaoWithGenericAndDbUnit extends GenericSpringDataJpaTestWithDbUnit<Customer,Long>{
	
	private CustomerRepository dao ;

	public CustomerRepository getDao() {
		return dao;
	}
	
	@Autowired
	@Qualifier("customersDataSource")
	 public void setDataSource(DataSource dataSource){
		 super.setDataSource(dataSource);
	 }

	@Autowired
	public void setDao(CustomerRepository dao) {
		this.dao = dao;
		this.setGenericDao(dao);
	}
	
	@Override
	public Long getPkOfEntity(Customer c){
		return c.getId();
	}
			
	
}
