package org.mycontrib.generic.test;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




/**
 * @author Didier Defrance
 * 
 * Classe de Test générique pour tester un dao dans le cadre suivant:
 *     - via JUnit4 et SpringTest
 *     - test d'un dao dont le code est basé sur héritage de JpaRepository<T,ID >
 *     
 * Utilisation:
 * 
 *     créer une sous classe héritant de GenericSpringDataJpaTest<T> 
 *     où T est le type d'entité persistante
 */

public abstract class  GenericSpringDataJpaTest<T,ID extends Serializable> {
	
	private boolean withDetails = true; //si true , logger.debug() des valeurs detaillées
	
	private static Logger logger = LoggerFactory.getLogger(GenericSpringDataJpaTest.class);
	
	protected JpaRepository<T,ID> genericDao;
	protected ID genericPk;
	
	public void setGenericDao(JpaRepository<T,ID> genericDao) {
		this.genericDao = genericDao;
	}
	
	//callback à coder:
	public abstract ID getPkOfEntity(T entity);
	public abstract T newEntity();
	public abstract void assertValuesOfNewEntity(T entity);
	public abstract void changeValuesOfEntity(T entity);
	public abstract void assertChangedValuesOfNewEntity(T entity);
	
	//callback utiles si relations (1-1 , 1-n , n-n , ... ) , redéfinition non obligatoire si héritage de GenericDaoSimpleTest
	public abstract void displayAttachedOtherEntities(T mainEntity); // afficher choses en dehors de toString()
	
	public abstract void attachNewOtherEntities(T mainEntity);
	public abstract void assertNewAttachedOtherEntities(T mainEntity);
	
	public abstract void updateAttachedOtherEntities(T mainEntity);
	public abstract void assertApdatedAttachedOtherEntities(T mainEntity);
		
	//code du TestGenerique
	public JpaRepository<T,ID> getGenericDao() {
		return genericDao;
	}

	
	public ID getGenericPk() {
		return genericPk;
	}

	public void setGenericPk(ID genericPk) {
		this.genericPk = genericPk;
	}

	@Test
	//@Transactional(propagation=Propagation.NEVER)
	public void testGenericDao_CRUD() {
		//************* version sans grande transaction globale (plein de petites transactions)
    	//              persistant, détaché , persistant, détaché , ....
    	//              pas d'affichage ou autre operation sophistiqué (sinon problème sur lazy=true)
    	//******************************************************************
		logger.debug("****** test CRUD sur T avec plusieurs petites transactions (via DAO JpaRepository<T>) *****");
		// séquence habituelle : new & save , get(All) , maj , update , get , delete , ...
		boolean withDeep=false;
		sequence_crudT(withDeep);
		logger.debug("****** end of CRUD test avec plusieurs petites transactions *****\n");
	}
    
	/*
	// EN PLUS, dans sous-classe "GenericSpringDataJpaComplexTest"
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
	*/
    
              
    protected void sequence_crudT(boolean withDeep){
		createEntity(withDeep); verifNewEntity(withDeep);
		updateEntity(withDeep); verifUpdateEntity(withDeep); 
		deleteEntity(withDeep); verifDeleteEntity(withDeep);
	}
	private void createEntity(boolean withDeep) {
		try {
		    T newEntity = this.newEntity(); //callback (in subclass)
	        if(withDeep){
		        // eventuelles liaisions avec d'autres entités
		        	this.attachNewOtherEntities(newEntity);
		        }
			genericDao.save(newEntity); // appel d'une méthode transactionnelle
			this.genericPk = this.getPkOfEntity(newEntity);
			if(withDetails){				
				     logger.debug("\t id(pk) de la nouvelle entite creee: " + genericPk);
				  }
			} catch(RuntimeException ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
		
		private void verifNewEntity(boolean withDeep) {
		T e=null;
			try {
			e = genericDao.findOne(this.genericPk);
			this.assertValuesOfNewEntity(e);
			if(withDetails){
				logger.debug("\t valeurs initiales de l'entite (cree): " + e.toString());
			}
			if(withDeep){
			    // éventuel test ou affichage d'un élément (ou collection) lié(e) - lazy=true ? 
			    assertNewAttachedOtherEntities(e);
			    displayAttachedOtherEntities(e);			
			  }
			} catch(RuntimeException  ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
					
		private void updateEntity(boolean withDeep) {
		T e=null;
		try {
			e = genericDao.findOne(this.genericPk);
			this.changeValuesOfEntity(e);
			if(withDeep){
			    // éventuelle modification d'une liaison avec une autre entité
				updateAttachedOtherEntities(e);
			  }
			genericDao.save(e);  // appel d'une méthode transactionnelle
		    } catch(Exception ex){
		    	logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
		
		
		private void verifUpdateEntity(boolean withDeep) {
		T e=null;
			try {
			e = genericDao.findOne(this.genericPk);
			this.assertChangedValuesOfNewEntity(e);
			if(withDetails){
				logger.debug("\t nouvelle valeur de l'entite (modifiee): " + e.toString());
			}
			if(withDeep){
			    // + éventuel test ou affichage d'un élément (ou collection) lié(e) - lazy=true ? 
				assertApdatedAttachedOtherEntities(e);
				displayAttachedOtherEntities(e);
			  }
			} catch(RuntimeException  ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
		}
		    	    
		private void deleteEntity(boolean withDeep) {
		try {
			genericDao.delete(this.genericPk);
			} catch(RuntimeException  ex){
				logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}	
		}   
		
	
		private void verifDeleteEntity(boolean withDeep) {
		try {
			T e = genericDao.findOne(this.genericPk);
			if(e==null && withDetails)
				logger.debug("\t entite bien supprimee");
			Assert.assertTrue(e == null);
		} catch(RuntimeException ex){
			    logger.error(ex.getMessage());
      	    	Assert.fail(ex.getMessage());
      		}
	   }

		public boolean isWithDetails() {
			return withDetails;
		}

		public void setWithDetails(boolean withDetails) {
			this.withDetails = withDetails;
		}
		
		

	
}
