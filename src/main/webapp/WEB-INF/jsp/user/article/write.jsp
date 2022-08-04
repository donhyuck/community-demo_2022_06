<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 작성" />
<%@include file="../common/head.jspf"%>
<%@include file="../../common/toastUiEditorLib.jspf"%>

<!-- 게시글 작성시 유효성 검사 스크립트 시작 -->
<script>
	let ArticleWrite__submitFormDone = false;

	function ArticleWrite__submitForm(form) {

		if (ArticleWrite__submitFormDone) {
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

		// 토스트UI 적용
		const editor = $(form).find('.toast-ui-editor').data(
				'data-toast-editor');
		const markdown = editor.getMarkdown().trim();

		if (markdown.length == 0) {
			alert('내용을 입력해주세요.');
			editor.focus();
			return;
		}
		form.body.value = markdown;

		ArticleWrite__submitFormDone = true;
		form.submit();
	}
</script>
<!-- 게시글 작성시 유효성 검사 스크립트 끝 -->

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../article/doWrite"
			onsubmit="ArticleWrite__submitForm(this); return false;">
			<input type="hidden" name="body" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>작성자</th>
						<td>${ rq.loginedMember.nickname }</td>
					</tr>
					<tr>
						<th>게시판</th>
						<td>
							<select class="select select-bordered" name="boardId">
								<option selected disabled>게시판을 선택해주세요.</option>
								<option value="1">공지</option>
								<option value="2">자유</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" name="title" required="required" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="제목을 입력하세요." />
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<div class="toast-ui-editor">
								<script type="text/x-template"></script>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn btn-outline btn-sm" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn btn-outline btn-primary btn-sm">등록하기</button>
			</div>
		</form>

		<div class="btns mt-3">
			<c:if test="${ article.extra__actorCanModify }">
				<a class="btn btn-link" href="../article/modify?id=${article.id}">게시물 수정</a>
			</c:if>
			<c:if test="${ article.extra__actorCanDelete }">
				<a class="btn btn-link" href="../article/doDelete?id=${ article.id }"
					onclick="if ( confirm('정말 삭제하시겠습니까?') == false) return false;">게시물 삭제</a>
			</c:if>
		</div>
	</div>
</section>

<%@include file="../common/foot.jspf"%>