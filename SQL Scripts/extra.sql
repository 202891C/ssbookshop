use ssbookshop;

-- insert into customer(name, email, phone_no, passkey) values ("Tom", "acbd@hotmail.com", 92039395, 10001);
-- Alter Table customer_order ADD (price float);
-- select custID from customer where (name, email, phone_no) = ("Tom", "acbd@hotmail.com", 92039395);

-- UPDATE book SET qty = qty - 1 WHERE bookID = 1001;

-- insert into customer_order values (2, 1001, 1, 10.00);
-- update book set qty = 100 where bookID=1001;
-- select * from book;

-- select * from customer_order;
-- select * from customer;
-- select custID from customer where (name, email, phone_no) = ('Mark', 'qeqrt@gmail.com', 56789098);

-- select email, pass from customer, pw WHERE pass="xxxx" and email="acbd@hotmail.com" 
-- 	and customer.passkey = pw.passkey;

-- select email, pass from customer, pw WHERE pass='xxxx' and email='acbd@hotmail.com' and customer.passkey = pw.passkey;

-- select customer_order.bookID, email, name, title, customer_order.price, customer_order.qty, image 
-- from customer_order, customer, book WHERE customer.custID = customer_order.custID
-- 		AND book.bookID = customer_order.bookID AND customer_order.custID = 2;

-- Update customer Set phone_no = 28022026 where custID=1;

-- SELECT * FROM book WHERE title Like '%null%' and qty > 0 ORDER BY title ASC;

select * from pw;
select * from book;
select * from author;
select * from customer;
select * from book_author, book, author
   WHERE book.bookID = book_author.bookID
      AND author.authorID = book_author.authorID;
select * from customer_order, customer, book 
	WHERE customer.custID = customer_order.custID
		AND book.bookID = customer_order.bookID;