--====================================
-- @실습문제 - 상품재고관리자
--====================================

select * from product;
select * from product_io order by no desc;

create table product (
    id varchar2(30),
    brand varchar2(50) not null,
    name varchar2(100) not null,
    price number not null,
    monitor_size number,
    os varchar2(100),
    storage number, -- 저장공간
    stock number default 0,
    reg_date date default sysdate,
    constraint pk_product_id primary key(id),
    constraint ck_product_stock check(stock >= 0)
);

create table product_io (
    no number,
    product_id varchar2(30),
    count number,
    status char(1), -- 입출고여부 - 입고 : I 출고 : O
    io_datetime timestamp default systimestamp,
    constraint pk_product_io_no primary key(no),
    constraint fk_prodcut_io_product_id foreign key(product_id) references product(id) on delete cascade,
    constraint ck_product_io_ststus check(status in ('I', 'O'))
);

-- 시퀀스 생성
create sequence seq_product_io_no
start with 1
increment by 1
nocycle;

drop table product;
drop table product_io;
drop sequence seq_product_io_no;

--상품정보 insert
insert into product 
values ('14TD90P-GX30K', 'LG전자', '그램360', 1046990, 15, 'Windows10', 256, default, default);

insert into product 
values ('X413EA-EB086', 'ASUS', '비보북', 609000, 14, 'Windows10', 256, default, default);

insert into product 
values ('16ACH-R7-STORM', 'Lenovo', 'LEGION 5 Pro',     1699000, 16, 'Windows10', 512, default, default);

insert into product 
values ('MVVK2KH/A', 'Apple', '맥북프로', 3705690, 16, 'macOS', 512, default, default);


insert into
    product_io
values (
    seq_product_io_no.nextval, '14TD90P-GX30K', 10, 'I', default
);


-- 상품입출고 데이터 insert시 상품테이블 재고를 수정하는 트리거 생성
create or replace trigger trig_product
    before
    insert on product_io
    for each row
begin
    if :new.status = 'I' then
        update product
        set stock = stock + :new.count
        where id = :new.product_id;
    else
        update product
        set stock = stock - :new.count
        where id = :new.product_id;
    end if;
end;
/

insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'14TD90P-GX30K' , 30, 'I');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'14TD90P-GX30K' , 20, 'O');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'X413EA-EB086' , 100, 'I');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'X413EA-EB086' , 10, 'O');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'16ACH-R7-STORM' , 50, 'I');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'16ACH-R7-STORM' , 5, 'O');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'MVVK2KH/A' , 30, 'I');
insert into product_io(no, product_id, count, status) 
values(seq_product_io_no.nextval,'MVVK2KH/A' , 8, 'O');
