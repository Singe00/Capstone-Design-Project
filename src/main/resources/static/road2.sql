drop table if exists road2 CASCADE;

CREATE TABLE road2 (
                      roadId INT,
                      cId INT,
                      traffic INT,
                      FOREIGN KEY (cId) REFERENCES charge (chargeid)
);

