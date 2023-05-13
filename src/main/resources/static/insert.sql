
drop table if exists tour CASCADE;
CREATE TABLE IF NOT EXISTS tour AS SELECT * FROM CSVREAD('C:\Users\admin\IdeaProjects\CrtDgn\src\main\resources\static\tour_with_image.csv',null, 'charset=UTF-8');
select * from tour

DELETE FROM tour
WHERE latitude IS NULL AND longitude IS NULL;