
DROP TABLE IF EXISTS Purchase;

CREATE TABLE Purchase
	(purchaseId integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
     purchaseDateTime VARCHAR(32),
     cutomerId integer,
     amount double);
     

INSERT INTO Purchase(purchaseDateTime,cutomerId,amount) 
   VALUES ('2016-10-18-10:30:00' , 1 , 136.5);
   

select * from Purchase;

