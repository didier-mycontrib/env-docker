package org.mycontrib.generic.test;

import java.io.Serializable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/*
 * Version complexe (avec beaucoup de callback à coder) - pour tests poussés avec relations 1-1 , 1-n , n-n , ...
 * nécessitant des paramétrages assez spécifiques , cette version est moins automatisable et donc assez peu utilisée.
 */


public abstract class GenericSpringDataJpaComplexTest<T,ID extends Serializable> extends GenericSpringDataJpaTest<T, ID> {
	
	private static Logger logger = LoggerFactory.getLogger(GenericSpringDataJpaComplexTest.class);
	
	@Test
	@Transactional
	public void testGenericDao_CRUD_InOneTx() {
		//************* version avec grande transaction globale (de bout en bout à l'état persistant)
    	//              affichages sophistiqués possibles (pas de problème avec lazy=true)
    	//******************************************************************
		logger.debug("****** test CRUD sur T en une seule transaction (via DAO JpaRepository<T>) *****");
		// séquence habituelle : new & save , get(All) , maj , update , get , delete , ...
		boolean withDeep=true;
		sequence_crudT(withDeep);
		logger.debug("****** end of CRUD test en une seule transaction *****\n");
	}

}
