CREATE DATABASE customers;
\c customers; 
-- \c in postgres like USE in mysql

DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Address;

-- SERIAL or BIGSERIAL postgres TYPE are auto_increment Integer

CREATE TABLE Address
	(id SERIAL primary key ,
     numberAndStreet VARCHAR(64),
     zip VARCHAR(12),
     town VARCHAR(32),
     country VARCHAR(32));

CREATE TABLE Customer
	(id SERIAL primary key ,
     firstName VARCHAR(32),
     lastName VARCHAR(32),
     addressId integer,
     email VARCHAR(32),
     phoneNumber VARCHAR(16));

ALTER TABLE Customer ADD CONSTRAINT validAddressId
FOREIGN KEY(addressId) REFERENCES Address(id); 

INSERT INTO Address(numberAndStreet , zip , town , country) 
   VALUES ('12 rue elle ' , '75000' , 'Paris' , 'France');

INSERT INTO Customer(firstName , lastName , addressId ,email , phoneNumber) 
   VALUES ('alex' , 'Therieur' , 1 , 'alex-therieur@iciOulaBas.fr' , '0102030405');


select * from Address;
select * from Customer;
