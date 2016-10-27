package tp.spring.config.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@Import( { WebAppConfig.class  })
public class StandaloneWebAppConfigWithEmbbededTomcat {
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	TomcatEmbeddedServletContainerFactory factory = new
	                                                TomcatEmbeddedServletContainerFactory();
	factory.setPort(8080);
	factory.setSessionTimeout(5, TimeUnit.MINUTES);
	//equivalent of server.context-path=/testDockerSpringBootWeb in application.properties :
	factory.setContextPath("/testDockerSpringBootWeb");
	 
	//factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html");
	TomcatContextCustomizer contextCustomizer = new TomcatContextCustomizer() {
		        @Override
		        public void customize(org.apache.catalina.Context context) {
		            context.addWelcomeFile("/index.html");
		        }
		    };
	factory.addContextCustomizers(contextCustomizer);
	return factory;
	}
	
	
	
	@Bean
    public ServletRegistrationBean servletRegistrationBean(WebApplicationContext context) {
		      
		ServletRegistrationBean dispatcherServletRegistrationBean 
        = new ServletRegistrationBean(new DispatcherServlet(), "/mvc/*");
		
		Map<String,String> mapInitParameters = new HashMap<String,String>();
		mapInitParameters.put("contextConfigLocation", "");
		dispatcherServletRegistrationBean.setInitParameters(mapInitParameters);
		
		dispatcherServletRegistrationBean.setLoadOnStartup(1);
		
		return dispatcherServletRegistrationBean;
	}
   
}
