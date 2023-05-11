drop table if exists member CASCADE;

create table member(
                     id int auto_increment,
                     email varchar(100),
                     password varchar(300),
                     primary key (id)
);


