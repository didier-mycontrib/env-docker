-- CONNECT system/oracle
-- defaut database/instance: sid=XE

DROP SEQUENCE customer_seq;
DROP TABLE Customer;
COMMIT;


CREATE TABLE Customer
	(id NUMBER(12) primary key ,
         name VARCHAR2(64));

CREATE SEQUENCE customer_seq START WITH 1;

CREATE OR REPLACE TRIGGER customer_bir 
BEFORE INSERT ON Customer 
FOR EACH ROW

BEGIN
  SELECT customer_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/
COMMIT;

INSERT INTO Customer(id,name)  VALUES (1 , 'first customer');

COMMIT;

select * from Customer;
