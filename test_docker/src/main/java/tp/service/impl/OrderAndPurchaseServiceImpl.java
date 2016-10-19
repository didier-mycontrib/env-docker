package tp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.dao.customers.CustomerRepository;
import tp.dao.orders.OrderRepository;
import tp.dao.orders.ProductRefRepository;
import tp.dao.purchases.PurchaseRepository;
import tp.entity.customers.Customer;
import tp.entity.orders.Order;
import tp.entity.orders.OrderLine;
import tp.entity.orders.ProductRef;
import tp.entity.purchases.Purchase;
import tp.service.OrderAndPurchaseService;

@Transactional("transactionManager")
//@Transactional()
@Service
public class OrderAndPurchaseServiceImpl implements OrderAndPurchaseService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRefRepository productRefRepository;
	
	//@Autowired
	//private PurchaseRepository purchaseRepository;

	@Override
	public Long purchaseOrder(Long customerId, List<ProductRef> listOfProducts) {
		Long orderId=null;
		Order newOrder = orderRepository.save(new Order(null,new Date(), customerId , 0.0,  null));
		orderId= newOrder.getOrderId();
		
		Map<Integer,OrderLine> mapOrderLines = new HashMap<Integer,OrderLine>();
		int i=0;
		double prixTotal = 0;
		for(ProductRef prod : listOfProducts){
			i++;
			productRefRepository.save(prod);
			OrderLine orderLine =new OrderLine(null,prod,1 /*quantity*/);
			orderLine.setOrderId(orderId);
			orderLine.setLineNumber(i);
			mapOrderLines.put(i, orderLine);
			prixTotal+=prod.getPrice();
		}
		
		newOrder.setOrderLines(mapOrderLines);
		newOrder.setTotalPrice(prixTotal);
		
		purchaseRepository.save(new Purchase(null,new Date(),customerId , prixTotal));
		
		//verification de l'existence du client:
		Customer customer = customerRepository.findOne(customerId);
		if(customer==null){
			//System.out.println("customer not exists with id="+customerId); 
			throw new RuntimeException("customer not exists with id="+customerId); //transaction will be rollback (all in jta mode)
		}
		 
		orderRepository.save(newOrder);
		System.out.println("savedOrder: "+newOrder.toString());
		return orderId;
	}

}
