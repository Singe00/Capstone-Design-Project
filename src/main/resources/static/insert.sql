
drop table if exists tour CASCADE;
CREATE TABLE IF NOT EXISTS tour AS SELECT * FROM CSVREAD('C:\Users\admin\IdeaProjects\CrtDgn\src\main\resources\static\tour.csv',null, 'charset=UTF-8');
select * from tour;


DELETE FROM tour WHERE ROADADDRESS IS NULL;
DELETE FROM tour WHERE latitude = 0.0 AND longitude = 0.0;

DELETE FROM tour WHERE tag LIKE '%축제%';

ALTER TABLE tour ADD tourid INT AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE tour DROP COLUMN tourid;

ALTER TABLE tour ALTER COLUMN latitude DOUBLE;
ALTER TABLE tour ALTER COLUMN longitude DOUBLE;