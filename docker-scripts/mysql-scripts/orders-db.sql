CREATE DATABASE IF NOT EXISTS orders;
USE orders;

DROP TABLE IF EXISTS OrderLine;
DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS ProductRef;

CREATE TABLE ProductRef
	(productId integer primary key,
     label VARCHAR(64),
     price double);

CREATE TABLE `Order`
	(orderId integer primary key auto_increment,
     orderDate VARCHAR(32),
     cutomerId integer,
     totalPrice double);
     
CREATE TABLE OrderLine
	(orderId integer,
	 lineNumber integer,
     productRef integer,
     quantity integer,
     primary key(orderId,lineNumber)); 
     
ALTER TABLE OrderLine ADD CONSTRAINT validProductRef
FOREIGN KEY(productRef) REFERENCES ProductRef(productId);

ALTER TABLE OrderLine ADD CONSTRAINT validOrderId
FOREIGN KEY(orderId) REFERENCES `Order`(orderId);

INSERT INTO ProductRef(productId,label,price) 
   VALUES (1,'smartPhone xy' , 120.5),
          (2,'micro SD memory card' , 8);

INSERT INTO `Order`(orderDate,cutomerId,totalPrice) 
   VALUES ('2016-10-18' , 1 , 136.5);
   
INSERT INTO OrderLine(orderId,lineNumber,productRef,quantity) 
   VALUES (1 , 1 , 1 , 1),
          (1 , 2 , 2 , 2);   

select * from ProductRef;
select * from `Order`;
select * from OrderLine;
