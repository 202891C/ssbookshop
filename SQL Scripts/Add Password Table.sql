use ssbookshop;

create table pw (passkey int not null primary key auto_increment, pass varchar(255) not null);
ALTER TABLE pw AUTO_INCREMENT=10001;
Alter Table customer ADD (cust_img varchar(255), passkey int not null);
insert into pw (pass) values ("xxxx");
Update customer set passkey = 10001 where custID = 1;

Alter Table customer add constraint fk_pass foreign key(passkey) references pw(passkey);

select * from pw;
select * from customer;

select * from customer, pw WHERE customer.passkey = pw.passkey;

