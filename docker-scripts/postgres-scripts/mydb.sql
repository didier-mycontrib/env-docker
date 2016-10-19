CREATE DATABASE test;
\c test; 
-- \c in postgres like USE in mysql

DROP TABLE IF EXISTS Customer;

-- SERIAL or BIGSERIAL postgres TYPE are auto_increment Integer

CREATE TABLE Customer
	(id SERIAL primary key ,
         name VARCHAR(64));

INSERT INTO Customer(name)  VALUES ( 'first postgresql customer');


select * from Customer;
