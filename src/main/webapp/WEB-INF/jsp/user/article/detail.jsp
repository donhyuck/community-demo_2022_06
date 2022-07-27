<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시글 상세" />
<%@include file="../common/head.jspf"%>

<!-- 게시글 조회수 스크립트 시작 -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {

		const localStorageKey = 'article__' + params.id + '__viewDone';

		if (localStorage.getItem(localStorageKey)) {
			return;
		}

		localStorage.setItem(localStorageKey, true);

		$.get('../article/doIncreaseHitCount', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hitCount').empty().html(data.data1);
		}, 'json');
	}

	$(function() {
		// 실전코드
		// ArticleDetail__increaseHitCount();

		// 임시코드
		setTimeout(ArticleDetail__increaseHitCount, 300);
	})
</script>
<!-- 게시글 조회수 스크립트 끝 -->

<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
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
							<div class="flex items-center">
								<span class="text-blue-700">${ article.goodRP }</span>
								<span>&nbsp;</span>
								<c:if test="${ actorCanMakeRP }">
									<button class="btn btn-secondary btn-xs">좋아요 👍</button>
									<span>&nbsp;</span>
									<button class="btn btn-accent btn-xs">싫어요 👎</button>
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${ article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${ article.body }</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- 게시글 조작 영역 시작 -->
		<div class="btns mt-3">
			<button type="button" class="btn btn-outline" onclick="history.back();">뒤로가기</button>
			<c:if test="${ article.extra__actorCanModify }">
				<a class="btn btn-primary" href="../article/modify?id=${article.id}">게시물 수정</a>
			</c:if>
			<c:if test="${ article.extra__actorCanDelete }">
				<a class="btn btn-secondary" href="../article/doDelete?id=${ article.id }"
					onclick="if ( confirm('정말 삭제하시겠습니까?') == false) return false;">게시물 삭제</a>
			</c:if>
		</div>
		<!-- 게시글 조작 영역 끝 -->
	</div>
</section>

<%@include file="../common/foot.jspf"%>