Scripts:

create table user_table(
user_id varchar(20) NOT null PRIMARY KEY,
uname varchar(60),
passwrd varchar(12),
role varchar(20),
email varchar(30)
);
hinppus.kondepogu@randstadusa.com
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
user_id varchar(20) NOT null, peer_id varchar(20) NOT NULL, count int,
analysis int,design int,vc int,clarity int,aesthetic int, orginality int, tot int,
analysis_text varchar(200), design_text varchar(200),clarity_text varchar(200),aesthetic_text varchar(200),orginality_text varchar(200),tot_text varchar(200)
);

insert into user_table values('admin','password','admin','rthutari@asu.edu');

select * from user_table;