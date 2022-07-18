<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 수정" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../article/doModify">
			<input type="hidden" name="id" value="${ article.id }" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>번호</th>
						<td>${ article.id }</td>
					</tr>
					<tr>
						<th>등록날짜</th>
						<td>${ article.regDate.substring(2, 16) }</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>${ article.updateDate.substring(2, 16) }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${ article.extra__writerName }</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="title" value="${ article.title }" class="w-96" placeholder="수정할 제목을 입력하세요." />
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<textarea name="body" class="w-full" rows="10">${ article.body }</textarea>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn-text-link" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn-text-link">수정하기</button>
				<c:if test="${ article.extra__actorCanDelete }">
					<a class="btn-text-link" href="../article/doDelete?id=${ article.id }"
						onclick="if ( confirm('정말 삭제하시겠습니까?') == false) return false;">게시물 삭제</a>
				</c:if>
			</div>
		</form>
	</div>
</section>

<%@include file="../common/foot.jspf"%>