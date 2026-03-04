Drop database ssbookshop;
create database if not exists ssbookshop;
show databases;

use ssbookshop;

drop table if exists book; 
create table book (bookID int not null primary key auto_increment, title varchar(50), price float, qty int, image varchar(255));
ALTER TABLE book AUTO_INCREMENT=1001;

drop table if exists author, book_author, customer, customer_order; 
create table author (authorID int not null primary key auto_increment, name varchar(50), email varchar(50));
ALTER TABLE author AUTO_INCREMENT=101;
create table book_author(authorID int NOT NULL, bookID int NOT NULL, 
	foreign key(authorID) references author(authorID), 
    foreign key(bookID) references book(bookID));

create table customer (custID int not null primary key auto_increment, name varchar(50), email varchar(50), phone_no int);
create table customer_order (custID int NOT NULL, bookID int NOT NULL, qty int NOT NULL, 
	foreign key(custID) references customer(custID), 
    foreign key(bookID) references book(bookID)); 
    
show tables;
describe book;
describe book_author;
describe author;
describe customer_order;
describe customer;

insert into book (title, price, qty, image) values ('Java for dummies', 11.11, 11, "images/book-blue.jpg");
insert into book (title, price, qty) values ('More Java for dummies', 22.22, 22);
insert into book (title, price, qty) values ('More Java for more dummies', 33.33, 33);
insert into book (title, price, qty) values ('Expensive Book', 100.99, 99);
insert into book (title, price, qty) values ('Null and Void', 30.00, 0);

insert into author (name, email) values('Tan Ah Teck', 'teck@nowhere.com');
insert into author (name, email) values('Mohamed Ali', 'ali@somewhere.com');
insert into author (name, email) values('Kumar', 'kumar@abc.com');
insert into author (name, email) values('Kelvin Jones', 'kelvin@xyz.com');

insert into book_author values (101, 1001);
insert into book_author values (102, 1001);
insert into book_author values (103, 1001);
insert into book_author values (101, 1002);
insert into book_author values (103, 1002);
insert into book_author values (102, 1003);
insert into book_author values (102, 1004);
insert into book_author values (103, 1005);

insert into customer (name, email, phone_no) values ('Mr Person', 'popipa@pipopa.com', 2802026);
insert into customer_order values (1, 1001, 10);

select * from book;
select * from author;
select * from customer;
select * from book_author, book, author
   WHERE book.bookID = book_author.bookID
      AND author.authorID = book_author.authorID;
select * from customer_order, customer, book 
	WHERE customer.custID = customer_order.custID
		AND book.bookID = customer_order.bookID;

