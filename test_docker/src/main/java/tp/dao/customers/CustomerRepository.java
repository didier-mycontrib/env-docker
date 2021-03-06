package tp.dao.customers;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.entity.customers.Customer;

/*
 avec "Spring-data , l'implementation de JpaRepository<T,ID> est entierement générée 
 via <jpa:repositories base-package="org.xyz.dao.spring" />
 ou bien via @EnableJpaRepositories(basePackages = "org.xyz.dao.spring")
 */

/*NB : org.springframework.data.jpa.repository.JpaRepository<E,ID> hérite de 
 *     org.springframework.data.repository.CrudRepository<E,ID>
 * et comporte les méthodes delete() , save() , findOne(pk)  , findAll()
 */

public interface CustomerRepository  extends JpaRepository<Customer,Long>{
	
	Customer findByEmail(String email);

}
