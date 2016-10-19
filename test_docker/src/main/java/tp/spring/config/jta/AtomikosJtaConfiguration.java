package tp.spring.config.jta;

import java.util.Properties;

//import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

//import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.atomikos.datasource.pool.ConnectionFactory;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
//import com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup; //deprecated and removed in hibernate 4 , 5

@Configuration
public class AtomikosJtaConfiguration {

	@Autowired private Environment environment ;

	public void tailorProperties(Properties properties) {
		/*properties.setProperty( "hibernate.transaction.manager_lookup_class", 
				 TransactionManagerLookup.class.getName());*/
	}

	@Bean
	public javax.transaction.UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(1000);
		return userTransactionImp;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public javax.transaction.TransactionManager transactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

	/*
	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(this.environment.getProperty("dataSource.url"));
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(this.environment.getProperty("dataSource.password"));
		mysqlXaDataSource.setUser(this.environment.getProperty ("dataSource.password"));
		//dataSource.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads");
		return xaDataSource;
	}*/

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