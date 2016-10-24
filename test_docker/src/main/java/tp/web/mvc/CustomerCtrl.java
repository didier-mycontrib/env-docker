package tp.web.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import tp.entity.customers.Customer;


@Controller //but not "@Component" for spring web controller
//@Scope(value="singleton")//by default
@RequestMapping("/customer")
@SessionAttributes( value={"customer"} ) //noms des "modelAttributes" qui sont EN PLUS récupérés/stockés en SESSION HTTP
                                         //au niveau de la page de rendu --> visibles en requestScope ET en sessionScope
public class CustomerCtrl {
	
	private Map<Long,Customer> clientMap = new HashMap<Long,Customer>(); //simulation sans service
	
	public CustomerCtrl(){
		clientMap.put(0L, new Customer(0L,null,null,"nouvel_email" , "0101010101" , null));//empty default customer
		clientMap.put(1L, new Customer(1L,"Therieur","alex","nouvel_email" , "0101010101" , null));
		clientMap.put(2L, new Customer(2L,"Therieur","alain","nouvel_email" , "0101010101" , null));
		clientMap.put(3L, new Customer(3L,"Fer","lucie","nouvel_email" , "0101010101" , null));
		clientMap.put(4L, new Customer(4L,"Air","axelle","nouvel_email" , "0101010101" , null));
	}
	
    //NB: @SessionAttributes et @ModelAttribute sont gérés avant @RequestMapping
	
	/*
	@ModelAttribute("customer") //NB: cette méthode n'est pas appelée/déclenchée si "customer" 
								//est déjà présent en session (et par copie) dans le modèle
	public Client addCustomerAttributeInModel(@RequestParam(name="numCli",required=false)Long numCli) {
		   //System.out.println("addCustomerAttributeInModel called");
		   if(numCli!=null)
	        return clientMap.get(numCli);
		   else 
			   return clientMap.get(0L); //cannot return null (for attribute value) !!!
	}
	*/
	
	@ModelAttribute 
		public void addAttributesInModel(Model model,@RequestParam(value="numCli",required=false)Long numCli) {
		//System.out.println("addAttributesInModel called");
		if(numCli!=null){
			/*
			if(numCli < 0 )
				throw new RuntimeException("le numero de client ne doit pas être négatif"); //pour test temporaire @ExceptionHandler
			else */
		      model.addAttribute("customer", clientMap.get(numCli));
		}
		else  model.addAttribute("customer", clientMap.get(0L)); //pour modelAttribute jamais null pour form:form
		}
	
	@RequestMapping(value="/updateClient" , method = RequestMethod.POST)
    public String updateClient(Model model , @Valid  @ModelAttribute("customer") Customer client , BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
		    // form validation error
			System.out.println("form validation error: " + bindingResult.toString());
		} else {
		    // form input is ok
			clientMap.put(client.getId() , client);
			System.out.println("updated client:" + client);
		}
		return "infosClient";
	}
	
	
	@RequestMapping("/info")
    public String toInfosClient(Model model) {
        //mise à jour du telephone du client 0L (pour le fun / la syntaxe):
		Customer cli = (Customer) model.asMap().get("customer");
		if(cli!=null && cli.getId()==0L) 
			  cli.setPhoneNumber("0102030405");
        return "infosClient"; //WEB-INF/view/infosClient.jsp selon viewResolver de WEB-INF/mvc-config
    }
	
	@RequestMapping("/endSession")
    public String endSession(Model model,HttpSession session) {
        if(model.containsAttribute("customer")){
	            model.asMap().remove("customer");
	            model.addAttribute("customer", clientMap.get(0L));//reinit default ("unknowned") customer
        }
		session.invalidate();
        return "infosClient"; //WEB-INF/view/infosClient.jsp selon viewResolver de WEB-INF/mvc-config
    }
}
