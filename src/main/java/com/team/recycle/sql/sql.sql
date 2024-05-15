drop sequence id_seq;

CREATE SEQUENCE id_seq
    START WITH 1
    INCREMENT BY 1;

drop table member;

CREATE TABLE MEMBER (
                        id NUMBER PRIMARY KEY,
                        email VARCHAR2(100) NOT NULL UNIQUE,
                        password VARCHAR2(100) NOT NULL,
                        join_date VARCHAR2(100) NOT NULL
);

select * from member;