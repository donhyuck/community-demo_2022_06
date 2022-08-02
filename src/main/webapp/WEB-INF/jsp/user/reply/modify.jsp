<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="댓글 수정" />
<%@include file="../common/head.jspf"%>

<!-- 댓글 수정시 유효성 검사 스크립트 시작 -->
<script>
	let ReplyModify__submitFormDone = false;

	function ReplyModify__submitForm(form) {

		if (ReplyModify__submitFormDone) {
			return;
		}

		// 좌우 공백 제거
		form.body.value = form.body.value.trim();
		if (form.body.value.length == 0) {
			alert('댓글을 입력해주세요.');
			form.body.focus();
			return;
		}

		if (form.body.value.length < 2) {
			alert('댓글을 2자 이상 입력해주세요.');
			form.body.focus();
			return;
		}

		ReplyModify__submitFormDone = true;
		form.submit();
	}
</script>
<!-- 댓글 수정시 유효성 검사 스크립트 끝 -->

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../reply/doModify"
			onsubmit="ReplyModify__submitForm(this); return false;">
			<input type="hidden" name="id" value="${ reply.id }" />

			<!-- 게시물 정보 -->
			<div>게시물 정보</div>
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>번호</th>
						<td>
							<div>${ reply.relId }</div>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>
							<div>${ relDataTitle }</div>
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<div>${ relDataBody }</div>
						</td>
					</tr>
				</tbody>
			</table>

			<!-- 댓글 영역 -->
			<table class="mt-5">
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
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