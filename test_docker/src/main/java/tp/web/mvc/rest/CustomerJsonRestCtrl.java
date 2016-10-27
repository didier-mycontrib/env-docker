package tp.web.mvc.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


import tp.dao.customers.CustomerRepository;
import tp.entity.customers.Customer;

@RestController 
@RequestMapping(value="/rest/customers" , headers="Accept=application/json")
public class CustomerJsonRestCtrl {
	
	
	@Autowired //ou @Inject
	private CustomerRepository customerDao;
	
	
	 @RequestMapping(value="/" , method=RequestMethod.GET) 
	 @ResponseBody  
	 List<Customer> getCustomersByCriteria(@RequestParam(value="email",required=false)String email) {
		 if(email==null)
			 return customerDao.findAll();
		 else{
			 List<Customer> listeC= new ArrayList<Customer>();
			 Customer c = customerDao.findByEmail(email);
			 if(c!=null)
				 listeC.add(c);
			 return listeC;
		     }
		 } 
	 
	/*
	 @RequestMapping(value="/" , method=RequestMethod.PUT ) 
	 @ResponseBody  
	 Customer updateCustomer(@RequestBody String customerAsJsonString) {
		 Customer c=null;
		 try {
			ObjectMapper jacksonMapper = new ObjectMapper();
			//jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			c = jacksonMapper.readValue(customerAsJsonString,Customer.class);
			System.out.println("devise to update:" + c);			
			customerDao.save(c);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} */
	 
	 //méthode non autorisée ?
	 @RequestMapping(value="/" , method=RequestMethod.PUT )   
	 ResponseEntity<Customer> updateDevise(@RequestBody Customer c) {
		 try {
			System.out.println("customer to update:" + c);			
			customerDao.save(c);
			return new ResponseEntity<Customer>(c, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<Customer>(HttpStatus.NOT_MODIFIED);
		}
	}
	 
	 
	
	 
	 @RequestMapping(value="/{id}" , method=RequestMethod.GET) 
	 ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) { 
		 Customer c = customerDao.findOne(id);
		 return new ResponseEntity<Customer>(c, HttpStatus.OK);
		 }
	 
	
	 
}

