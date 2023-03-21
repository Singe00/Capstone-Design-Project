drop table if exists tour CASCADE;

create table tour(
                     tourId int auto_increment,
                     address varchar(255),
                     latitude int,
                     longitude int,
                     keyword varchar(255),
                     image varchar(255),
                     phone varchar(255),
                     introduce varchar(255),
                     tag varchar(255),
                     primary key (tourId)
);
