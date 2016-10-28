package tp.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import tp.dao.orders.OrderRepository;
import tp.entity.customers.Customer;
import tp.entity.orders.Order;
import tp.entity.orders.ProductRef;
import tp.service.OrderAndPurchaseService;
import tp.spring.config.DomainServiceConfig;

@ActiveProfiles(profiles = {"test" , "jta"})
//@ActiveProfiles(profiles = {"mem-test"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DomainServiceConfig.class})
public class TestOrderAndPurchaseService {
	
	
	@Autowired
    private OrderAndPurchaseService orderAndPurchaseService;
	
	@Autowired
	private OrderRepository orderRepository;
     
    @Test
    public void testPurchaseOrder() {
    	List<ProductRef> listOfProductRef = new ArrayList<ProductRef>();
		listOfProductRef.add(new ProductRef(5L,"stylo bille noir " , 1.5));
		listOfProductRef.add(new ProductRef(6L,"cahier 48 pages " , 2.5));
		
		//a tester avec customerId=1L (existant) ou 999L (inexistant) , avec profile "jta" ou "!jta"
		Long newOrderId = orderAndPurchaseService.purchaseOrder(1L, listOfProductRef);
		//Long newOrderId = orderAndPurchaseService.purchaseOrder(999L, listOfProductRef);
		
		Order newOrder = orderRepository.findOne(newOrderId);
		System.out.println("new order : " + newOrder.toString());
		for(Integer numLine : newOrder.getOrderLines().keySet() ){
			System.out.println("\t" + numLine + ":" + newOrder.getOrderLines().get(numLine));
		}
    }

}
