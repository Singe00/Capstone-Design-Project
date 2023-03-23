drop table if exists charge CASCADE;

create table charge(
                     chargeId int auto_increment,
                     chargingPlace varchar(255),
                     chargingPlaceDetail varchar(255),
                     addressJibun varchar(255),
                     latitude float,
                     longitude float,
                     chargingFlag int,
                     quickChargingFlag int,
                     chargerCount int,
                     quickChargerCount int,
                     parkingFeeFlag int,
                     primary key (chargeId)
);
