connect 'jdbc:derby:memory:sample;create=true';

-- in case these are left over from previous tests...
drop table Pet;
drop table Person;

create table Person(
	id bigint not null generated always as identity (start with 1, increment by 1) constraint person_pk primary key,
	firstname varchar(50),
	middlename varchar(50),
	lastname varchar(50)
);

create table Pet(
	id bigint not null generated always as identity (start with 1, increment by 1),
	personId bigint not null constraint pet_fk references Person on delete cascade on update restrict,
	name varchar(50)
);

insert into person (firstname, middlename, lastname) values ('George', null, 'Washington');
insert into person (firstname, middlename, lastname) values ('John', null, 'Adams');
insert into person (firstname, middlename, lastname) values ('Thomas',null,'Jefferson');
insert into person (firstname, middlename, lastname) values ('James',null,'Madison');
insert into person (firstname, middlename, lastname) values ('James' ,null, 'Monroe');
insert into person (firstname, middlename, lastname) values ('John','Quincy','Adams');
insert into person (firstname, middlename, lastname) values ('Andrew',null,'Jackson');
insert into person (firstname, middlename, lastname) values ('Martin',null,'Van Buren');
insert into person (firstname, middlename, lastname) values ('William','Henry','Harrison');
insert into person (firstname, middlename, lastname) values ('John',null,'Tyler');
