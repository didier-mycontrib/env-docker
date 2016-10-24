package tp.web.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //but not "@Component" for spring web controller
@RequestMapping("/app")
public class WelcomeCtrl {
	
	//complete path/url is "http://localhost:8080" 
	//        + "/...." + "/app" + "/hello"
	 @RequestMapping("/hello")
	    @ResponseBody //si @ResponseBody , génération directe de la réponse , sinon viewResolver (mvc-config.xml)
	    String say_hello() {
	        return "Hello World!";
	    }
	 
	 @RequestMapping("/to_welcome")
	    public String toWelcome(Model model) {
	        model.addAttribute("message", "bienvenu(e)");
	        return "welcome"; //WEB-INF/view/welcome.jsp selon viewResolver de WEB-INF/mvc-config
	    }
	 
	 @RequestMapping("/memberOnly")
	    public String toMember(Model model) {
	        model.addAttribute("message", "message top secret 007");
	        return "member"; //WEB-INF/view/member.jsp selon viewResolver de WEB-INF/mvc-config
	    }
	 
	 @RequestMapping("/finSession")
	    public String finSession(Model model,HttpSession session) {
		    //SecurityContextHolder.clearContext(); //RAZ context Spring security
		    session.invalidate();
	        model.addAttribute("message", "session terminée");
	        return "welcome"; //WEB-INF/view/welcome.jsp selon viewResolver de WEB-INF/mvc-config
	    }

}

