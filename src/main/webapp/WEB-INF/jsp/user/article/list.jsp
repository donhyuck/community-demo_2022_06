<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시글 목록" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="50" />
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
							<td>${ article.regDate.substring(2,16) }</td>
							<td>${ article.updateDate.substring(2,16) }</td>
							<td>${ article.memberId }</td>
							<td>
								<a href="../article/detail?id=${ article.id }" class="hover:underline">${ article.title }</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</section>

<%@include file="../common/foot.jspf"%>