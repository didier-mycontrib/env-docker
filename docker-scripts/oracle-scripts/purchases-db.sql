-- CONNECT system/oracle
-- defaut database/instance: sid=XE

DROP SEQUENCE purchase_seq;
DROP TABLE Purchase;
COMMIT;


CREATE TABLE Purchase
	(purchaseId NUMBER(12) primary key ,
     purchaseDateTime VARCHAR2(32),
     cutomerId NUMBER(12),
     amount NUMBER(12,2));
         
        
     

CREATE SEQUENCE purchase_seq START WITH 1;

-- nb: ce trigger increment l'id (via la sequence) que
-- si l'id est null (pas renseigné). ce qui pourra etre le cas si la sequence
-- est explicitement utilisee (par jpa ou autre):
CREATE OR REPLACE TRIGGER purchase_insert_trg
BEFORE INSERT ON Purchase
FOR EACH ROW
WHEN (new.purchaseId is null)
BEGIN
  SELECT purchase_seq.NEXTVAL
  INTO   :new.purchaseId
  FROM   dual;
END;
/


COMMIT;


INSERT INTO Purchase(purchaseDateTime,cutomerId,amount) 
   VALUES ('2016-10-18-10:30:00' , 1 , 136.5);

COMMIT;

select * from Purchase;
