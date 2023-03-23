drop table if exists charge CASCADE;

create table charge(
                     chargeId int auto_increment,
                     chargingPlace varchar(255),
                     chargingPlaceDetail varchar(255),
                     addressJibun varchar(255),
                     latitude float,
                     longitude float,
                     chargingFlag varchar(1),
                     quickChargingFlag varchar(1),
                     chargerCount int,
                     quickChargerCount int,
                     parkingFeeFlag varchar(1),
                     primary key (chargeId)
);
