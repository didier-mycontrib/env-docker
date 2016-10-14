CREATE DATABASE IF NOT EXISTS test;
USE test;

DROP TABLE IF EXISTS Customer;

CREATE TABLE Customer
	(id integer primary key auto_increment,
         name VARCHAR(64));

INSERT INTO Customer(id,name) 
   VALUES (1 , "first customer");

select * from Customer;
