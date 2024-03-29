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

DROP TABLE IF EXISTS `member`;

# 회원 데이터 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '(3=일반, 7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNo CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    `delStatus` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전, 1=탈퇴)',
    `delDate` DATETIME COMMENT '탈퇴날짜'
);

SELECT * FROM `member`;

# 회원 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = 'Admin',
nickname = '관리자',
cellphoneNo = '01011111110',
email = 'admin@test.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = 'test1',
nickname = '테스터1',
cellphoneNo = '01011111111',
email = 'test1@test.com';


INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = 'test2',
nickname = '테스터2',
cellphoneNo = '01011111112',
email = 'test2@test.com';

SELECT LAST_INSERT_ID();
SELECT * FROM `member`;

SELECT * FROM `member` AS m
WHERE m.id = 1;

# 게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

# 기존 게시물의 작성자를 2번으로 지정
UPDATE article
SET memberId = 2
WHERE memberId = 0;

UPDATE article
SET memberId = 3
WHERE id = 3;

SELECT * FROM article;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2),...',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=탈퇴전, 1=탈퇴)',
    delDate DATETIME COMMENT '삭제날짜'
);

# 기본 게시판 생성
INSERT INTO board
SET regdate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regdate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유';

# 게시판 테이블에 boardId 컬럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

SELECT * FROM article;

# 1, 2번 게시물을 공지사항 게시물로 지정
UPDATE article
SET boardId = 1
WHERE id IN(1, 2);

# 3번 게시물을 자유게시판 게시물로 지정
UPDATE article
SET boardId = 2
WHERE id IN(3);

SELECT * FROM board;

# 게시물 개수 늘리기
/*
INSERT INTO article
(
	regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 1, FLOOR(RAND() * 2) + 1, CONCAT('제목_', RAND()), CONCAT('제목_', RAND())
FROM article;
*/

SELECT COUNT(*) FROM article;

# 게시물 테이블 hitCount 컬럼을 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;

SELECT hitCount FROM article
WHERE id = 2045;

SELECT * FROM article;

# 리액션포인트 테이블 생성
CREATE TABLE reactionPoint (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `point` SMALLINT(2) NOT NULL
);

# 리액션포인트 테스트 데이터
## 1번 회원이 1번 article에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

## 1번 회원이 2번 article에 대해서 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

## 2번 회원이 1번 article에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

## 2번 회원이 2번 article에 대해서 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = 1;

## 3번 회원이 1번 article에 대해서 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;

SELECT * FROM reactionPoint;

## 게시물 리스트 가져오는 쿼리에 관련 리액션 포인트도 같이 가져오게 하기
SELECT A.*,
IFNULL(SUM(RP.point), 0) AS extra__sumRP,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)), 0) AS extra__goodRP,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)), 0) AS extra__badRP
FROM (
    SELECT A.*,
    M.nickname AS extra__writerName
    FROM article AS A
    LEFT JOIN
    `member` AS M
    ON A.memberId = M.id
    WHERE 1
) AS A
LEFT JOIN reactionPoint AS RP
ON RP.relTypeCode = 'article'
AND A.id = RP.relId
GROUP BY A.id
ORDER BY A.id DESC;

## 게시물 상세정보 가져오는 쿼리에 관련 리액션 포인트도 같이 가져오게 하기
SELECT A.*,
M.nickname AS extra__writerName,
IFNULL(SUM(RP.point), 0) AS extra__sumRP,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)), 0) AS extra__goodRP,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)), 0) AS extra__badRP
FROM article AS A
LEFT JOIN `member` AS M
ON A.memberId = M.id
LEFT JOIN reactionPoint AS RP
ON RP.relTypeCode = 'article'
AND A.id = RP.relId
WHERE 1
AND A.id = 3;

# 게시물 테이블 goodReactionPoint 컬럼을 추가
ALTER TABLE article
ADD COLUMN goodRP INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 게시물 테이블 badReactionPoint 컬럼을 추가
ALTER TABLE article
ADD COLUMN badRP INT(10) UNSIGNED NOT NULL DEFAULT 0;

/*
#각 게시물 별, 좋아요, 싫어요 총합
SELECT RP.relId,
SUM(IF(RP.point > 0, RP.point, 0)) AS goodRP,
SUM(IF(RP.point < 0, RP.point * 1, 0)) AS badRP
FROM reactionPoint AS RP
WHERE relTypeCode = 'article'
GROUP BY RP.relTypeCode, RP.relId
*/

UPDATE article AS A
INNER JOIN (
	SELECT RP.relId,
	SUM(IF(RP.point > 0, RP.point, 0)) AS goodRP,
	SUM(IF(RP.point < 0, RP.point * -1, 0)) AS badRP
	FROM reactionPoint AS RP
	WHERE relTypeCode = 'article'
	GROUP BY RP.relTypeCode, RP.relId
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodRP = RP_SUM.goodRP,
A.badRP = RP_SUM.badRP;

SELECT * FROM article;
SELECT * FROM reactionPoint;

# 댓글 테이블 생성
CREATE TABLE reply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `body` TEXT NOT NULL
);

# 댓글 테스트 데이터
# 1번 회원, 1번 게시글 댓글 등록
INSERT INTO reply
SET regdate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 aaaaaaaaaaaa';

# 1번 회원, 1번 게시글 댓글 등록
INSERT INTO reply
SET regdate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 bbbbbbbbbbbb';

# 2번 회원, 1번 게시글 댓글 등록
INSERT INTO reply
SET regdate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`body` = '댓글 ccccccccccc';

# 3번 회원, 2번 게시글 댓글 등록
INSERT INTO reply
SET regdate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 2,
`body` = '댓글 ddddddddddd';

SELECT * FROM reply;

SELECT R.*,
M.nickname AS extra__writerName
FROM reply AS R
LEFT JOIN `member` AS M
ON R.memberId = M.id
WHERE R.relTypeCode = 'article'
AND R.relId = 1
ORDER BY R.id DESC;

# 댓글 테이블 goodReactionPoint 컬럼을 추가
ALTER TABLE reply
ADD COLUMN goodRP INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 댓글 테이블 badReactionPoint 컬럼을 추가
ALTER TABLE reply
ADD COLUMN badRP INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 댓글 테이블에 인덱스 걸기
ALTER TABLE `reply` ADD INDEX (`relTypeCode`, `relId`);

# 부가정보테이블
# 부가정보테이블 테이블 추가
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
    `relId` INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `typeCode` CHAR(30) NOT NULL,
    `type2Code` CHAR(70) NOT NULL,
    `value` TEXT NOT NULL
);

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`);

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`;

DESC attr;
SELECT * FROM attr;