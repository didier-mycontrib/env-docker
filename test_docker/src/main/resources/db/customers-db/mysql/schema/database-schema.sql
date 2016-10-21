# CREATE DATABASE IF NOT EXISTS customers;
USE customers;

DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Address;

CREATE TABLE Address
	(id integer primary key auto_increment,
     numberAndStreet VARCHAR(64),
     zip VARCHAR(12),
     town VARCHAR(32),
     country VARCHAR(32));

CREATE TABLE Customer
	(id integer primary key auto_increment,
     firstName VARCHAR(32),
     lastName VARCHAR(32),
     addressId integer,
     email VARCHAR(32),
     phoneNumber VARCHAR(16));
     
ALTER TABLE Customer ADD CONSTRAINT validAddressId
FOREIGN KEY(addressId) REFERENCES Address(id); 