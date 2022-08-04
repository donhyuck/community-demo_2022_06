<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 수정" />
<%@include file="../common/head.jspf"%>
<%@include file="../../common/toastUiEditorLib.jspf"%>

<!-- 게시글 수정시 유효성 검사 스크립트 시작 -->
<script>
	let ArticleModify__submitFormDone = false;

	function ArticleModify__submitForm(form) {

		if (ArticleModify__submitFormDone) {
			alert('처리중입니다.');
			return;
		}

		// 좌우 공백 제거
		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();
			return;
		}

		const editor = $(form).find('.toast-ui-editor').data(
				'data-toast-editor');
		const markdown = editor.getMarkdown().trim();

		if (markdown.length == 0) {
			alert('내용을 입력해주세요.');
			editor.focus();
			return;
		}

		ArticleModify__submitFormDone = true;
		form.submit();
	}
</script>
<!-- 게시글 수정시 유효성 검사 스크립트 끝 -->

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../article/doModify"
			onsubmit="ArticleModify__submitForm(this); return false;">
			<input type="hidden" name="id" value="${ article.id }" />
			<input type="hidden" name="body" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>번호</th>
						<td>
							<div class="badge badge-primary">${ article.id }</div>
						</td>
					</tr>
					<tr>
						<th>등록날짜</th>
						<td>
							<div>${ article.forPrintRegDate_Type2 }</div>
						</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>
							<div>${ article.forPrintUpdateDate_Type2 }</div>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${ article.extra__writerName }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>
							<span class="text-blue-700 article-detail__hitCount">${ article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<th>추천수</th>
						<td>
							<span class="text-blue-700 article-detail__hitCount">${ article.goodRP }</span>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="title" value="${ article.title }" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="수정할 제목을 입력하세요." />
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<div class="toast-ui-editor">
								<script type="text/x-template">${ article.body }</script>
							</div>
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