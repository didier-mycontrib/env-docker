CREATE DATABASE purchases;
\c purchases; 
-- \c in postgres like USE in mysql

DROP TABLE IF EXISTS Purchase;


CREATE TABLE Purchase
	(purchaseId SERIAL primary key,
     purchaseDateTime VARCHAR(32),
     cutomerId integer,
     amount real);
     

INSERT INTO Purchase(purchaseDateTime,cutomerId,amount) 
   VALUES ('2016-10-18-10:30:00' , 1 , 136.5);
   

select * from Purchase;