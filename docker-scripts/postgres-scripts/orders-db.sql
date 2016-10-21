CREATE DATABASE orders;
\c orders; 
-- \c in postgres like USE in mysql

DROP TABLE IF EXISTS OrderLine;
DROP TABLE IF EXISTS "Order";
DROP TABLE IF EXISTS ProductRef;

-- SERIAL or BIGSERIAL postgres TYPE are auto_increment Integer

CREATE TABLE ProductRef
	(productId integer primary key,
     label VARCHAR(64),
     price real);

CREATE TABLE "Order"
	(orderId SERIAL  primary key,
     orderDate VARCHAR(32),
     cutomerId integer,
     totalPrice real);
     
CREATE TABLE OrderLine
	(orderId integer,
	 lineNumber integer,
     productRef integer,
     quantity integer,
     primary key(orderId,lineNumber)); 
     
ALTER TABLE OrderLine ADD CONSTRAINT validProductRef
FOREIGN KEY(productRef) REFERENCES ProductRef(productId);

ALTER TABLE OrderLine ADD CONSTRAINT validOrderId
FOREIGN KEY(orderId) REFERENCES "Order"(orderId);

INSERT INTO ProductRef(productId,label,price) 
   VALUES (1,'smartPhone xy' , 120.5),
          (2,'micro SD memory card' , 8);

INSERT INTO "Order"(orderDate,cutomerId,totalPrice) 
   VALUES ('2016-10-18' , 1 , 136.5);
   
INSERT INTO OrderLine(orderId,lineNumber,productRef,quantity) 
   VALUES (1 , 1 , 1 , 1),
          (1 , 2 , 2 , 2);   

select * from ProductRef;
select * from "Order";
select * from OrderLine;
