package org.mycontrib.generic.test;

import java.io.Serializable;

public abstract class GenericSpringDataJpaSimpleTest<T,ID extends Serializable> extends GenericSpringDataJpaTest<T, ID> {
	
	//callback éventuellement inutiles  à ne pas obligatoirement redéfinir (si pas de 1-1 ni de  1-n ni de n-n , ... ):
	public void displayAttachedOtherEntities(T mainEntity){} 
	
	public void attachNewOtherEntities(T mainEntity){}
	public void assertNewAttachedOtherEntities(T mainEntity){}
	
	public void updateAttachedOtherEntities(T mainEntity){}
	public void assertApdatedAttachedOtherEntities(T mainEntity){}

}
