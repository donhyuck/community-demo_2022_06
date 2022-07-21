<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name} 게시판" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="mb-3">게시물 개수 : ${ articlesCount }개</div>
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="60" />
					<col width="150" />
					<col width="150" />
					<col width="150" />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>등록날짜</th>
						<th>수정날짜</th>
						<th>작성자</th>
						<th>제목</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="article" items="${ articles }">
						<tr>
							<td>${ article.id }</td>
							<td>${ article.regDateForPrint }</td>
							<td>${ article.updateDateForPrint }</td>
							<td>${ article.extra__writerName }</td>
							<td>
								<a href="../article/detail?id=${ article.id }" class="btn-text-link">${ article.title }</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="page-menu mt-3">
			<div class="btn-group justify-center">
				<c:forEach begin="1" end="20" var="i">
					<a href="?page=${ i }" class="btn btn-sm ${ param.page == i ? 'btn-active' : '' }">${ i }</a>
				</c:forEach>
			</div>
		</div>
	</div>
</section>

<%@include file="../common/foot.jspf"%>