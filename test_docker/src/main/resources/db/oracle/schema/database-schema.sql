-- CONNECT system/oracle
-- defaut database/instance: sid=XE

DROP SEQUENCE customer_seq;
DROP TABLE Customer;
COMMIT;


CREATE TABLE Customer
	(id NUMBER(12) primary key ,
         name VARCHAR2(64));

CREATE SEQUENCE customer_seq START WITH 1;

-- nb: ce trigger increment l'id (via la sequence) que
-- si l'id est null (pas renseigné). ce qui pourra etre le cas si la sequence
-- est explicitement utilisee (par jpa ou autre):
CREATE OR REPLACE TRIGGER customer_insert_trg
BEFORE INSERT ON Customer
FOR EACH ROW
WHEN (new.id is null)
BEGIN
  SELECT customer_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/



COMMIT;

