drop table if exists road CASCADE;

CREATE TABLE road (
                      roadId INT,
                      tId INT,
                      traffic INT,
                      FOREIGN KEY (tId) REFERENCES tour (tourid)
);

