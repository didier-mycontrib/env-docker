CREATE DATABASE IF NOT EXISTS purchases;
USE purchases;


DROP TABLE IF EXISTS Purchase;

# ... + Payment , + ....

CREATE TABLE Purchase
	(purchaseId integer primary key auto_increment,
     purchaseDateTime VARCHAR(32),
     cutomerId integer,
     amount double);
     

INSERT INTO Purchase(purchaseDateTime,cutomerId,amount) 
   VALUES ('2016-10-18-10:30:00' , 1 , 136.5);
   

select * from Purchase;

