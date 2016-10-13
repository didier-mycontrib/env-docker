package org.mycontrib.generic.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Didier
 *
 * NB: penser à placer 
 * <context:component-scan base-package="org.mycontrib"/>
 * dans la configuration Spring de l'application utilisatrice
 */

//@Component ou @Named également dans sous classe prise en charge par Spring
@Component  // ou @Named
@Transactional
public class GenericDaoJpaImpl<T,ID extends Serializable> extends AbstractGenericDaoJpaImpl<T,ID> {
	
		
	public GenericDaoJpaImpl() {
		super();		
	}

	@PersistenceContext() //with default (unique) persitent Unit.
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
