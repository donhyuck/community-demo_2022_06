<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
</head>
<body>
	<h1>게시글 상세</h1>

	<header>
		<a href="/">로고</a>

		<ul>
			<li>
				<a href="/">홈</a>
			</li>
			<li>
				<a href="/user/article/list">목록</a>
			</li>
		</ul>
	</header>

	<table border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>등록날짜</th>
				<th>수정날짜</th>
				<th>작성자</th>
				<th>제목</th>
				<th>내용</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${ article.id }</td>
				<td>${ article.regDate }</td>
				<td>${ article.updateDate }</td>
				<td>${ article.memberId }</td>
				<td>${ article.title }</td>
				<td>${ article.body }</td>
			</tr>
		</tbody>
	</table>
</body>
</html>