drop database if exists vvip;

create database vvip default character set utf8;

use vvip;

drop table if exists User;

create table User
(
	id int primary key auto_increment,
	name varchar(50) not null,
	phone varchar(50) not null,
	balance double default 0,
	info varchar(50)
);

drop table if exists Record;

create table Record
(
	id int primary key auto_increment,
	operaterId varchar(50) not null,
	phone varchar(50) not null,
	info varchar(50),
	status int default 0
);