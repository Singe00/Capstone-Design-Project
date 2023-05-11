drop table if exists jwt_token CASCADE;

CREATE TABLE jwt_token (
                           id int auto_increment,
                           user_id INT NOT NULL,
                           token VARCHAR(500) NOT NULL,
                           expiration TIMESTAMP NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (user_id) REFERENCES member (id) ON DELETE CASCADE,
                           primary key (id)
);