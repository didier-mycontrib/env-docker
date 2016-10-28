package tp.standalone.boot;

import javax.sql.DataSource;

import org.mycontrib.admin.util.DataBaseAdminHelper;
import org.mycontrib.database.util.DataBaseUtilHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import tp.spring.config.ds.DataSourceConfigCustomersDB;
import tp.spring.config.ds.DataSourceConfigOrdersDB;
import tp.spring.config.ds.DataSourceConfigPurchasesDB;

public class PreBoot {
	
	private static Logger logger = LoggerFactory.getLogger(PreBoot.class) ;
	// le boot en mode console/text .
	
	/*
		DataBaseAdminHelper.createNewDatabase("jdbc:mysql://mysqlHost:3306/mysql", "test", "root", "root");
		//deux limitations autour:
		// a) securité : username="root" , password="root" devraient idéalement provenir d'une saisie confidentielle
		// b) tant que la nouvelle base (ici test) n'est pas construite , la config datasource spring (avec base 'test' ne démarre pas).
		
		// ==>à absolument placer dans une mini application paralèlle  (au minimum main() d'une autre classe java)
		// DANS tp.withoutSpring.simple.app.InitDataBaseMiniApp (pour l'instant)
	*/
	
	
	
	public static void main(String[] args) {
		System.out.println("testDatabasesBeforeLaunchingApp : " + testDatabasesBeforeLaunchingApp(true,true) );
	}
	
	public static boolean testDatabasesBeforeLaunchingApp(boolean initSchemaIfNecessary , boolean initDefaultDataIfNecessary) {
		boolean db1Ready = true;
		boolean db2Ready = true;
		boolean db3Ready = true;
		// on prépare la configuration de l'application en mode spring-boot
		SpringApplication app = new SpringApplication(DataSourceConfigCustomersDB.class , DataSourceConfigOrdersDB.class , DataSourceConfigPurchasesDB.class);
		
		app.setWebEnvironment(false);
		//app.setAdditionalProfiles("..." , "..." ); 
		
		// on lance l'application spring
		ConfigurableApplicationContext context =  app.run();
		
		try {
			   try {
				DataSource ds1 = (DataSource) context.getBean("customersDataSource");
				   if(DataBaseUtilHelper.test_database_schema_initialization(ds1,"Customer")){
					   logger.info("database customers is ready [ accessible and with table(s) ]");
				   }
				   else{
					   db1Ready=false;
					   if(initSchemaIfNecessary){
					      DataBaseAdminHelper.initDatabaseSchema(ds1, "mysql" , "customers");
					      db1Ready=true;
					      if(initDefaultDataIfNecessary)
					         DataBaseAdminHelper.insertInitialDefaultDataInDatabase(ds1, "customers");
					   }
				   }
				} catch (Exception e) {
					    db1Ready = false;
						logger.error("Exception : " + e.getMessage());
				}
			   
			   
			  try{
			   DataSource ds2 =  (DataSource) context.getBean("ordersDataSource");
			   if(DataBaseUtilHelper.test_database_schema_initialization(ds2,"OrderLine")){
				   logger.info("database orders is ready [ accessible and with table(s) ]");
			   }
			   else{
				   db2Ready=false;
				   if(initSchemaIfNecessary){
					   DataBaseAdminHelper.initDatabaseSchema(ds2, "mysql" , "orders");
					   db2Ready=true;
					   if(initDefaultDataIfNecessary)
					      DataBaseAdminHelper.insertInitialDefaultDataInDatabase(ds2, "orders");
				   }
			   }
			  } catch (Exception e) {
				    db2Ready = false;
					logger.error("Exception : " + e.getMessage());
			   }
			  
			  try{
			   DataSource ds3 =  (DataSource) context.getBean("purchasesDataSource");
			   if(DataBaseUtilHelper.test_database_schema_initialization(ds3,"Purchase")){
				   logger.info("database purchases is ready [ accessible and with table(s) ]");
			   }
			   else{
				   db3Ready=false;
				   if(initSchemaIfNecessary){
					   DataBaseAdminHelper.initDatabaseSchema(ds3, "mysql" , "purchases");
					   db3Ready=true;
					   if(initDefaultDataIfNecessary)
						   DataBaseAdminHelper.insertInitialDefaultDataInDatabase(ds3, "purchases");
				   }
			   }
			  } catch (Exception e) {
				    db3Ready = false;
					logger.error("Exception : " + e.getMessage());
			   }
			   
					 
		} catch (Exception ex) {
			logger.error("Exception : " + ex.getMessage());
		}finally{
		// fermeture du contexte Spring
		context.close();
		}
		return (db1Ready && db2Ready && db3Ready);
	}


}
