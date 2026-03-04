create database if not exists ssbookshop;
show databases;

use ssbookshop;

drop table if exists book; 
create table book (bookID int primary key, title varchar(50), price float, qty int);

drop table if exists author, book_author, customer, customer_order; 
create table author (authorID int primary key, name varchar(50), email varchar(50));
create table book_author(authorID int NOT NULL, bookID int NOT NULL, 
	foreign key(authorID) references author(authorID), 
    foreign key(bookID) references book(bookID));

create table customer (custID int primary key, name varchar(50), email varchar(50), phone_no int);
create table customer_order (custID int NOT NULL, bookID int NOT NULL, qty int NOT NULL, 
	foreign key(custID) references customer(custID), 
    foreign key(bookID) references book(bookID)); 
    

show tables;
describe book;
describe book_author;
describe author;
describe customer_order;
describe customer;

insert into book values (1001, 'Java for dummies', 11.11, 11);
insert into book values (1002, 'More Java for dummies', 22.22, 22);
insert into book values (1003, 'More Java for more dummies', 33.33, 33);
insert into book values (1004, 'Expensive Book', 100.99, 99);
insert into book values (1005, 'Null and Void', 30.00, 0);

insert into author values(1, 'Tan Ah Teck', 'teck@nowhere.com');
insert into author values(2, 'Mohamed Ali', 'ali@somewhere.com');
insert into author values(3, 'Kumar', 'kumar@abc.com');
insert into author values(4, 'Kelvin Jones', 'kelvin@xyz.com');

insert into book_author values (1, 1001);
insert into book_author values (2, 1001);
insert into book_author values (3, 1001);
insert into book_author values (1, 1002);
insert into book_author values (3, 1002);
insert into book_author values (2, 1003);
insert into book_author values (2, 1004);
insert into book_author values (3, 1005);

insert into customer values (1, 'Mr Person', 'popipa@pipopa.com', 2802026);
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

