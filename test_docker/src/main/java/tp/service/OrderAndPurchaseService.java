package tp.service;

import java.util.List;

import tp.entity.orders.ProductRef;
	


public interface OrderAndPurchaseService {
	
		
	public Long purchaseOrder(Long customerId , List<ProductRef> listOfProducts);//return new OrderId

}
