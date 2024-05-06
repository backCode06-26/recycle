DROP SEQUENCE MEMBER_SEQ;
DROP TABLE GAME;
DROP TABLE MEMBER;

CREATE SEQUENCE MEMBER_SEQ
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1
    CACHE 10;

CREATE TABLE MEMBER (
                        id NUMBER PRIMARY KEY,
                        email VARCHAR2(255) UNIQUE NOT NULL,
                        password VARCHAR2(255) NOT NULL,
                        join_date TIMESTAMP
);

-- 게임 테이블 생성
CREATE TABLE GAME (
                      id NUMBER PRIMARY KEY,
                      email VARCHAR2(255) NOT NULL,
                      game_score NUMBER,
                      FOREIGN KEY (email) REFERENCES MEMBER(email)
);

SELECT *
FROM MEMBER M
         JOIN GAME G ON M.id = G.id