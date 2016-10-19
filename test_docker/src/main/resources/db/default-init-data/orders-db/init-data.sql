USE orders;

INSERT INTO ProductRef(productId,label,price) 
   VALUES (1,'smartPhone xy' , 120.5),
          (2,'micro SD memory card' , 8);

INSERT INTO `Order`(orderDate,cutomerId,totalPrice) 
   VALUES ('2016-10-18' , 1 , 136.5);
   
INSERT INTO OrderLine(orderId,lineNumber,productRef,quantity) 
   VALUES (1 , 1 , 1 , 1),
          (1 , 2 , 2 , 2);   

select * from ProductRef;
select * from `Order`;
select * from OrderLine;
