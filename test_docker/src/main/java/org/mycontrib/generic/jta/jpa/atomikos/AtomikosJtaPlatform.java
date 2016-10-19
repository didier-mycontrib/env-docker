package org.mycontrib.generic.jta.jpa.atomikos;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import com.atomikos.icatch.jta.UserTransactionManager;


/**
 * 
 * classe technique pour associer JPA/Hibernate >= 4 à Atomikos/Jta
 * Ceci remplace l'ancien TransactionManagerLookup de Hibernate 3
 * 
 * <property name="hibernate.transaction.jta.platform">org.mycontrib.generic.jta.jpa.atomikos.AtomikosJtaPlatform</property>
 *
 */

public class AtomikosJtaPlatform extends AbstractJtaPlatform {

  private static final long serialVersionUID = 1L;
  private UserTransactionManager utm;

  public AtomikosJtaPlatform() {
    utm = new UserTransactionManager();
  }

  @Override
  protected TransactionManager locateTransactionManager() {
    return utm;
  }

  @Override
  protected UserTransaction locateUserTransaction() {
    return utm;
  }
}