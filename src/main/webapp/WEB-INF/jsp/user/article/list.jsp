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
				<c:set var="pageMenuArmLen" value="5" />
				<c:set var="startPage" value="${page - pageMenuArmLen >= 1 ? page - pageMenuArmLen : 1}" />
				<c:set var="endPage" value="${page + pageMenuArmLen <= pagesCount ? page + pageMenuArmLen : pagesCount}" />

				<!-- 앞으로 이동 -->
				<c:if test="${ startPage > 1 }">
					<a href="?page=1&boardId=${ boardId }" class="btn btn-sm">《</a>
				</c:if>
				<c:if test="${ page > 1 }">
					<a href="?page=${ page-1 }&boardId=${ boardId }" class="btn btn-sm">〈</a>
				</c:if>

				<!-- 전체이동 -->
				<c:forEach begin="${ startPage }" end="${ endPage }" var="i">
					<a href="?page=${ i }&boardId=${ boardId }" class="btn btn-sm ${ param.page == i ? 'btn-active' : '' }">${ i }</a>
				</c:forEach>

				<!-- 끝으로 이동 -->
				<c:if test="${ page < pagesCount }">
					<a href="?page=${ page+1 }&boardId=${ boardId }" class="btn btn-sm">〉</a>
				</c:if>
				<c:if test="${ endPage < pagesCount }">
					<a href="?page=${ pagesCount }&boardId=${ boardId }" class="btn btn-sm">${ pagesCount }</a>
				</c:if>
			</div>
		</div>
	</div>
</section>

<%@include file="../common/foot.jspf"%>