<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="댓글 수정" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../reply/doModify">
			<input type="hidden" name="id" value="${ reply.id }" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>게시물 번호</th>
						<td>
							<div>${ reply.relId }</div>
						</td>
					</tr>
					<tr>
						<th>게시물 제목</th>
						<td>
							<div>제목</div>
						</td>
					</tr>
					<tr>
						<th>댓글 번호</th>
						<td>
							<div class="badge badge-primary">${ reply.id }</div>
						</td>
					</tr>
					<tr>
						<th>등록날짜</th>
						<td>
							<div>${ reply.forPrintRegDate_Type2 }</div>
						</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>
							<div>${ reply.forPrintUpdateDate_Type2 }</div>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${ reply.extra__writerName }</td>
					</tr>
					<tr>
						<th>추천수</th>
						<td>
							<span class="text-blue-700 article-detail__hitCount">${ reply.goodRP }</span>
						</td>
					</tr>
					<tr>
						<th>댓글내용</th>
						<td>
							<textarea name="body" class="w-full textarea textarea-bordered" rows="10" placeholder="수정할 댓글을 입력해주세요.">${ reply.body }</textarea>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn btn-outline btn-sm" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn btn-outline btn-primary btn-sm">수정하기</button>
			</div>
		</form>
	</div>
</section>

<%@include file="../common/foot.jspf"%>