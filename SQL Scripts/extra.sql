use ssbookshop;

-- insert into customer(name, email, phone_no, passkey) values ("Tom", "acbd@hotmail.com", 92039395, 10001);
-- Alter Table customer_order ADD (price float);
-- select custID from customer where (name, email, phone_no) = ("Tom", "acbd@hotmail.com", 92039395);

-- UPDATE book SET qty = qty - 1 WHERE bookID = 1001;

-- insert into customer_order values (2, 1001, 1, 10.00);
-- update book set qty = 100 where bookID=1001;
select * from book;

select * from customer_order;
select * from customer;
select custID from customer where (name, email, phone_no) = ('Mark', 'qeqrt@gmail.com', 56789098);
