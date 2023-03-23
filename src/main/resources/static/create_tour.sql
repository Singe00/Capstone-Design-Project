drop table if exists tour CASCADE;

create table tour(
                     tourId int auto_increment,
                     title varchar(255),
                     roadaddress int,
                     latitude float,
                     longitude float,
                     phoneno varchar(50),
                     tag varchar(255),
                     introduction varchar(255),
                     imagepath varchar(255),
                     primary key (tourId)
);
