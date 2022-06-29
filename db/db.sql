# DB 생성
DROP DATABASE IF EXISTS kor_sb_2022;
CREATE DATABASE kor_sb_2022;
USE kor_sb_2022;

# 게시물 데이터 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100),
    `body` TEXT NOT NULL
);

# 게시물 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

SELECT * FROM article;

SELECT LAST_INSERT_ID();

# 회원 데이터 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(50) NOT NULL,
    loginPw CHAR(50) NOT NULL,
    `name` CHAR(50) NOT NULL,
    nickname CHAR(50) NOT NULL,
    cellphoneNo CHAR(50) NOT NULL,
    email CHAR(50) NOT NULL
);

SELECT * FROM `member`;

# 회원 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`name` = 'admin',
nickname = 'admin',
cellphoneNo = '01011111110',
email = 'admin@test.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = 'test1',
nickname = 'test1',
cellphoneNo = '01011111111',
email = 'test1@test.com';


INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = 'test2',
nickname = 'test2',
cellphoneNo = '01011111112',
email = 'test2@test.com';

SELECT * FROM `member`;