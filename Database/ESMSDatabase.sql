CREATE DATABASE ESMSProjectdb;

use ESMSProjectdb;

show tables;

select * from Users;
select * from Product;
select * from Products;

insert into products (id ,description, image_url,name, price)
values
(101,'High performance laptop','notebook1.jpg','Laptop',150000);
insert into products (id ,description, image_url,name, price)
values
(102,'High performance mobile','Samsung Galaxy S25 Ultra.jpg','Samsung Galaxy S25 Ultra',11100),
(103, 'Good performance headphone', 'Sonyheadphone.jpg', 'Headphone', 5000);

select description from Products;

select image_url from Products;

set sql_safe_updates = 0;

update Products set image_url = '/images/notebook1.jpg' where name ='Laptop';
update Products set image_url = '/images/Samsung Galaxy S25 Ultra.jpg' where name ='Samsung Galaxy S25 Ultra';
update Products set image_url = '/images/Sonyheadphone.jpg' where name ='Headphone';

update Products set image_url = '/images/notebook1.jpg' where id = 101;
update Products set image_url = '/images/Samsung Galaxy S25 Ultra.jpg' where id = 102;

set sql_safe_updates = 1;

select id, name, image_url from products;