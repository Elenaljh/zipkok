create database if not exists ssafyhouse;
use ssafyhouse;


drop table if exists comment;
drop table if exists board;
drop table if exists user;




create table user (
                      member_id int not null auto_increment primary key,
                      email varchar(20) not null,
                      password varchar(45) not null,
                      name varchar(15) not null,
                      registered_at timestamp,
                      age int,
                      prefered_place_1 varchar(20),
                      prefered_place_2 varchar(20),
                      prefered_place_3 varchar(20),
                      prefered_type varchar(10)
);

create table board (
                       board_id int not null auto_increment primary key,
                       title varchar(50) not null,
                       content text not null,
                       created_at timestamp,
                       member_id int not null,
                       type int not null default 1,
                       hit int default 0,
                       boom int default 0,
                       writer varchar(30) not null,
                       foreign key (member_id) references user(member_id)
);

create table comment (
                         comment_id int not null auto_increment primary key,
						member_id int not null,
                         content text not null,
                         board_id int not null,
                         foreign key (board_id) references board(board_id)
);

insert into user (email, password, name, registered_at, age)
values ('ssafy@naver.com', '1234', '김싸피', now(), 23);

insert into board (title, content, created_at, member_id, writer)
values ('시험용','시험용 게시글', now(), 1, '김싸피');
insert into board (title, content, created_at, member_id, writer, type)
values ('QNA 테스트','시험용 QNA', now(), 1, '김싸피', 2);

insert into board (title, content, created_at, member_id, writer, type)
values ('공지 테스트','시험용 공지', now(), 1, '관리자', 0);
insert into comment (content, board_id, member_id) values ('재미있다', 1, 1);