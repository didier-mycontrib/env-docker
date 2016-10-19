package tp.withoutSpring.simple.app;

import org.mycontrib.admin.util.DataBaseAdminHelper;

public class InitDataBaseMiniApp {

	public static void main(String[] args) {
		DataBaseAdminHelper.createNewDatabase("jdbc:mysql://mysqlHost:3306/mysql", "test", "root", "root");
		//deux limitations autour:
		// a) securité : username="root" , password="root" devraient idéalement provenir d'une saisie confidentielle
		// b) tant que la nouvelle base (ici test) n'est pas construite , la config datasource spring (avec base 'test' ne démarre pas).
	}

}
