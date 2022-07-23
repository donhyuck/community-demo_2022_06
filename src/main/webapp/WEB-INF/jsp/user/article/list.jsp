<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name} 게시판" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<!-- 목록 상단구역 시작 -->
		<div class="flex">
			<div>
				게시물 개수 :
				<span class="text-purple-700 font-bold">${articlesCount}</span>
				개
			</div>
			
			<div class="flex-grow"></div>
			
			<!-- 검색박스 영역 시작 -->
			<form class="flex">
				<input type="hidden" name="boardId" value="${ param.boardId }">

				<select class="select select-bordered" name="keywordType">
					<option disabled="disabled">검색타입</option>
					<option value="title">제목</option>
					<option value="body">내용</option>
					<option value="title,body">제목과 내용</option>
				</select>

				<input name="searchKeyword" value="${ param.searchKeyword }" class="ml-2 w-72 input input-bordered" type="text"
					maxlength="20" placeholder="검색어" />

				<button type="submit" class="btn btn-primary ml-2">검색</button>
			</form>
			<!-- 검색박스 영역 끝 -->
		</div>
		<!-- 목록 상단구역 끝 -->

		<!-- 게시물 목록 영역 시작 -->
		<div class="mt-3">
			<table class="table table-fixed w-full">
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
					<!-- 게시물 데이터 -->
					<c:forEach var="article" items="${ articles }">
						<tr class="hover">
							<td>${ article.id }</td>
							<td>${ article.regDateForPrint }</td>
							<td>${ article.updateDateForPrint }</td>
							<td>${ article.extra__writerName }</td>
							<td>
								<a href="../article/detail?id=${ article.id }" class="block w-full truncate">${ article.title }</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 게시물 목록 영역 끝 -->

		<!-- 페이지 영역 시작 -->
		<div class="page-menu mt-3">
			<div class="btn-group justify-center">
				<c:set var="pageMenuArmLen" value="5" />
				<c:set var="startPage" value="${page - pageMenuArmLen >= 1 ? page - pageMenuArmLen : 1}" />
				<c:set var="endPage" value="${page + pageMenuArmLen <= pagesCount ? page + pageMenuArmLen : pagesCount}" />

				<c:set var="pageBaseUri" value="?boardId=${ boardId }" />
				<c:set var="pageBaseUri" value="${ pageBaseUri }&searchKeyword=${ param.searchKeyword }" />
				<c:set var="pageBaseUri" value="${ pageBaseUri }&keywordType=${ param.keywordType }" />

				<!-- 앞으로 이동 -->
				<c:if test="${ startPage > 1 }">
					<a href="${ pageBaseUri }&page=1" class="btn btn-sm">《</a>
				</c:if>
				<c:if test="${ page > 1 }">
					<a href="${ pageBaseUri }&page=${ page-1 }" class="btn btn-sm">〈</a>
				</c:if>

				<!-- 전체이동 -->
				<c:forEach begin="${ startPage }" end="${ endPage }" var="i">
					<a href="${ pageBaseUri }&page=${ i }" class="btn btn-sm ${ param.page == i ? 'btn-active' : '' }">${ i }</a>
				</c:forEach>

				<!-- 끝으로 이동 -->
				<c:if test="${ page < pagesCount }">
					<a href="${ pageBaseUri }&page=${ page+1 }" class="btn btn-sm">〉</a>
				</c:if>
				<c:if test="${ endPage < pagesCount }">
					<a href="${ pageBaseUri }&page=${ pagesCount }" class="btn btn-sm">${ pagesCount }</a>
				</c:if>
			</div>
		</div>
		<!-- 페이지 영역 끝 -->
	</div>
</section>

<%@include file="../common/foot.jspf"%>