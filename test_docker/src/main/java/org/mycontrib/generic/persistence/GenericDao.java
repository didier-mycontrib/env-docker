package org.mycontrib.generic.persistence;

import java.io.Serializable;

public interface GenericDao<T,ID extends Serializable> {
	
	public void deleteEntity(ID pk); // remove entity from pk
	public void removeEntity(T e); // remove entity
	
	public  T updateEntity(T e); // update entity (and return persist ref )
	
	public  T getEntityById(ID pk);//by primary key
	
    public  T persistNewEntity(T e); // persist entity (and return it with pk )

}
