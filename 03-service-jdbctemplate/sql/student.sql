--====================
-- 관리자(system) 계정
--====================
-- student 계정 생성
alter session set "_oracle_script" = true;

create user student
identified by student
default tablespace users;

grant connect, resource to student;

alter user student quota unlimited on users;

--====================
-- student 계정
--====================

create table member (
    id varchar2(20),
    name varchar2(100) not null,
    gender char(1),
    birthday date,
    email varchar2(500) not null,
    point number default 1000,
    reg_date timestamp default systimestamp,
    constraint pk_member_id primary key(id),
    constraint uq_member_email unique(email),
    constraint ck_member_gender check(gender in ('M', 'F'))
);

insert into
    member
values(
    'honggd', '홍길동', 'M', '1999-09-09', 'honggd@naver.com', default, default
);
insert into
    member
values(
    'gogd', '고길동', 'M', '1980-02-15', 'gogd@naver.com', default, default
);
insert into
    member
values(
    'sinsa', '신사임당', 'F', '1995-05-05', 'sinsa@naver.com', default, default
);
insert into
    member
values(
    'leess', '이순신', null, null, 'leess@naver.com', default, default
);
insert into
    member
values(
    'qwerty', '쿼티', 'F', null, 'qwerty@naver.com', default, default
);


select * from member;
select * from member_del;

--------------------------------------------------
-- 탈퇴회원테이블
create table MEMBER_DEL
as
select * from member where 1 = 0;

alter table
    member_del
add
    del_date timestamp default systimestamp;


-- * 회원 탈퇴시 탈퇴회원테이블에 insert하는 트리거 
create or replace trigger trig_member_del
    before
    delete on member
    for each row
begin
    insert into
        member_del
    values (
    :old.id, :old.name, :old.gender, :old.birthday, :old.email, :old.point, :old.reg_date, default
    );
end;
/
