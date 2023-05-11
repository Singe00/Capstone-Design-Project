drop table if exists jwttoken CASCADE;

CREATE TABLE jwttoken (
                           id int auto_increment,
                           userid INT NOT NULL,
                           token VARCHAR(500) NOT NULL,
                           FOREIGN KEY (userid) REFERENCES member (id) ON DELETE CASCADE,
                           primary key (id)
);