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
imagefile varchar(999),
NoofImages int,
content TEXT,
link varchar(100),
SUBMISSIONCOUNT int,
submissionDate TIMESTAMP,
wordcount int,
charcount int,
primary key(user_id,assignment_id,group_id)
);

create table peer_table(
user_id varchar(20) NOT null, reviewer_id varchar(20) NOT NULL, count int DEFAULT 0, teacher_evaluation boolean DEFAULT false,
marks varchar(999), assignment_id int,
group_id varchar(10)
);

drop table group_assign_review;

create table group_assign_review(
group_id VARCHAR(65) not null,
assignment_id int,
random_number int,
submissioncount int,
review_pannel varchar(50)
);


insert into user_table values('admin','admin','password','admin','rthutari@asu.edu','fall15');

select * from user_table;

/*retreving peer info*/

select user_id,assignment_id,group_id,assignment_name,imagefile,NoofImages,content,link, 
submissioncount,submissionDate,wordcount,charcount
from user_assignment where  user_id != 'manu' and assignment_id =3 and group_id = 'summer2015' and submissionDate > '2015-03-12 16:41:05'
order by submissionDate asc limit 2;

select distinct a.assignment_id  from group_assign_review a join user_assignment b where a.group_id = b.group_id and b.user_id ='1';

set sql_safe_updates=0


	