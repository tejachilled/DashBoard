Scripts:

create table user_table(
user_id varchar(20) NOT null PRIMARY KEY,
uname varchar(60),
passwrd varchar(12),
role varchar(50) default 'student',
email varchar(30),
group_id VARCHAR(65) default 'spring15'
);

create table user_assignment(
user_id varchar(20) NOT null,
assignment_id int,
group_id varchar(10),
assignment_name varchar(30),
imagefile varchar(300),
NoofImages int,
content TEXT,
link varchar(100),
SUBMISSIONCOUNT int,
submissionDate TIMESTAMP,
wordcount int,
charcount int
);


create table peer_table(
user_id varchar(20) NOT null, peer_id varchar(20) NOT NULL, count int DEFAULT 0, teacher_evaluation boolean DEFAULT false,
analysis int,design int,vc int,consistency int,aesthetic int, orginality int, tot int,
analysis_text varchar(200), design_text varchar(200),clarity_text varchar(200),aesthetic_text varchar(200),orginality_text varchar(200),tot_text varchar(200)
);

insert into user_table values('admin','admin','password','admin','rthutari@asu.edu','fall15');

select * from user_table;

/*retreving peer info*/

select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link, 
submissioncount,submissionDate,wordcount,charcount
from user_assignment where  (user_id,submissionDate) in
(select user_id,max(submissionDate) from user_assignment  group by user_id) and  submissionDate > '2015-02-09 13:37:26'
order by submissionDate asc limit 2;

