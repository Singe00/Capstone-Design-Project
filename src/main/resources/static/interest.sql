drop table if exists interest CASCADE;

create table interest(
                     interId int auto_increment,
                     userId int,
                     tourkey int,
                     primary key (interId),
                     FOREIGN KEY (userId) REFERENCES MEMBER(id),
                     FOREIGN KEY (tourkey) REFERENCES tour(tourId)
);

