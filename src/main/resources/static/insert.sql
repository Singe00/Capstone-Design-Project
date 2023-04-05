drop table if exists charge CASCADE;
CREATE TABLE IF NOT EXISTS charge AS SELECT * FROM CSVREAD('C:\Users\admin\IdeaProjects\CrtDgn\src\main\resources\static\tourList.csv',null, 'charset=UTF-8');
select * from charge

drop table if exists tour CASCADE;
CREATE TABLE IF NOT EXISTS tour AS SELECT * FROM CSVREAD('C:\Users\admin\IdeaProjects\CrtDgn\src\main\resources\static\tourList.csv',null, 'charset=UTF-8');
select * from tour