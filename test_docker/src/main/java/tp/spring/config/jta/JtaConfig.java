package tp.spring.config.jta;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Profile("jta")
@Configuration
public class JtaConfig {
	
	
	@Bean(name="atomikosJtaUserTransaction")
	public javax.transaction.UserTransaction jtaUserTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		//J2eeUserTransaction userTransactionImp = new J2eeUserTransaction(); sur serveur JEE complet seulement
		userTransactionImp.setTransactionTimeout(1000); //300 , 1000
		return userTransactionImp;
	}

	@Bean(name="atomikosJtaTransactionManager" ,initMethod = "init", destroyMethod = "close")
	public javax.transaction.TransactionManager jtaTransactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		//userTransactionManager.setForceShutdown(true);
		return userTransactionManager;
	}
	
	
	@Bean(name= { "springAtomikosJtaTransactionManager" })	
	public PlatformTransactionManager springJtaTransactionManager() throws Throwable {
		JtaTransactionManager jtaTransactionManager =  new JtaTransactionManager(  jtaUserTransaction(), jtaTransactionManager());
		//jtaTransactionManager.setAllowCustomIsolationLevels(true);
		return jtaTransactionManager;
	           
	}
	
	/*
	@Bean(initMethod = "init", destroyMethod = "close")
	public ConnectionFactory connectionFactory() {
		ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
		activeMQXAConnectionFactory.setBrokerURL(this.environment.getProperty( "jms.broker.url")  );
		AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
		atomikosConnectionFactoryBean.setUniqueResourceName("xamq");
		atomikosConnectionFactoryBean.setLocalTransactionMode(false);
		atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
		return atomikosConnectionFactoryBean;
	}*/

}
