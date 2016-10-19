# CREATE DATABASE IF NOT EXISTS purchases;
USE purchases;


DROP TABLE IF EXISTS Purchase;

# ... + Payment , + ....

CREATE TABLE Purchase
	(purchaseId integer primary key auto_increment,
     purchaseDateTime VARCHAR(32),
     cutomerId integer,
     amount double);