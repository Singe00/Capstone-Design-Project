drop table if exists charge CASCADE;

create table charge(
                     chargeId int auto_increment,
                     address varchar(255),
                     latitude int,
                     longitude int,
                     chargerType varchar(255),
                     slowCharge varchar(1),
                     fastChrage varchar(1),
                     fee varchar(1),
                     password varchar(32),
                     primary key (chargeId)
);
