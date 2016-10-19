USE customers;

INSERT INTO Address(numberAndStreet , zip , town , country) 
   VALUES ('12 rue elle ' , '75000' , 'Paris' , 'France');

INSERT INTO Customer(firstName , lastName , addressId ,email , phoneNumber) 
   VALUES ('alex' , 'Therieur' , 1 , 'alex-therieur@iciOulaBas.fr' , '0102030405');


select * from Address;
select * from Customer;