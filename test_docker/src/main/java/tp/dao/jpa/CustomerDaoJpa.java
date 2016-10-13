package tp.dao.jpa;

import org.mycontrib.generic.persistence.GenericDaoJpaImpl;
import org.springframework.stereotype.Component;

import tp.dao.CustomerDAO;
import tp.entity.Customer;

@Component
public class CustomerDaoJpa  extends GenericDaoJpaImpl<Customer,Long> 
                             implements CustomerDAO{

}
