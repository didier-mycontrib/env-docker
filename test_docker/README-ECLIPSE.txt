Réglages "DataSource-explorer":
==============================

pour Oracle , corriger le nom du driver jdbc:  oracle.jdbc.driver.OracleDriver

pour H2: selectionner Generic JDBC , org.h2.Driver , sa , ...
penser à regulièrement se connecter et se déconnecter de la base H2 pour éviter un blocage (verrou)

Réglages JPA (facet JPA)
=========================
disable librairie configuration
pour H2 : redéfinir catalog = nom de la base et SCHEMA = PUBLIC