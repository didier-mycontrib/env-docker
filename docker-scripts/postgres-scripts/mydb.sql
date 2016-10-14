CREATE DATABASE test;
\c test; 
-- \c in postgres like USE in mysql

DROP TABLE IF EXISTS Customer;

-- SERIAL or BIGSERIAL postgres TYPE are auto_increment Integer

CREATE TABLE Customer
	(id SERIAL primary key ,
         name VARCHAR(64));

INSERT INTO Customer(id,name)  VALUES (1 , 'first customer');


select * from Customer;
