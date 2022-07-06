<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시글 상세" />
<%@include file="../common/head.jspf"%>

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

<%@include file="../common/foot.jspf"%>