drop table if exists member CASCADE;

create table member(
                     id int auto_increment,
                     username varchar(32),
                     email varchar(32),
                     password varchar(32),
                     primary key (id)
);
