# CREATE DATABASE IF NOT EXISTS orders;
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
