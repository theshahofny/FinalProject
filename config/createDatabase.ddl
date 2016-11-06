drop database fitness;
create database fitness;

USE fitness;


create table goals(
	id int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	goal varchar(200) NOT NULL)
	engine = innodb;

create table exercises(
	id int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	exercise_name varchar(1000) NOT NULL,
	category varchar(1000) NOT NULL, 
	muscles varchar(1000) NOT NULL,
	image varchar(1000) NOT NULL,
	instructions varchar(2000) NOT NULL)
engine = innodb;	


create table accounts(
	id int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username varchar(50) NOT NULL,
	salt varchar(60) NOT NULL,
	hash varchar(60) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	height decimal(12,2) NOT NULL,
	weight decimal(12,2) NOT NULL,
	gender char(1) NOT NULL,
	age int(3) NULL,
	goal_id int(10) unsigned NOT NULL,
	UNIQUE(username),
	FOREIGN KEY(goal_id) references goals(id))
engine=innodb;

create table history(
	id int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	workout_date date,
	exercise_id int(10) unsigned NOT NULL,
	w_sets int(10),
	reps int(10),
	weight decimal(12,2),
	distance decimal(12,2),
	duration decimal(12,2),
	account_id int(10) unsigned NOT NULL,
	FOREIGN KEY(exercise_id) references exercises(id),
	FOREIGN KEY(account_id) references accounts(id) on delete cascade)
engine = innodb;

create table regimen(
	id int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	exercise_id int(10) unsigned NOT NULL,
	w_sets int(10),
	reps int(10),
	weight decimal(12,2),
	distance decimal(12,2),
	duration decimal(12,2),
	account_id int(10) unsigned NOT NULL,
	day varchar(10) NOT NULL,
	FOREIGN KEY(exercise_id) references exercises(id),
	FOREIGN KEY(account_id) references accounts(id) on delete cascade)
engine = innodb;

create table weekly_score(
	id int (10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	week int(10) unsigned NOT NULL,
	account_id int(10) unsigned NOT NULL,
	score decimal(10,2) NOT NULL,
	FOREIGN KEY(account_id) references accounts(id))
engine = innodb;
