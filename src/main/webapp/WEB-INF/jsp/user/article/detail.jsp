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
					<!-- 좋아요 싫어요 추가, 취소 영역 시작 -->
					<tr>
						<th>추천수</th>
						<td>
							<div class="flex items-center">
								<span class="text-blue-700">${ article.goodRP }</span>
								<span>&nbsp;</span>
								<c:if test="${ actorCanMakeRP }">
									<a href="/user/reaction/doMakeLike?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
										class="btn btn-error btn-xs btn-outline">
										<span>좋아요 👍</span>
									</a>
									<span>&nbsp;</span>
									<a href="/user/reaction/doMakeDislike?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
										class="btn btn-info btn-xs btn-outline">
										<span>싫어요 👎</span>
									</a>
								</c:if>

								<c:if test="${ actorCanCancelLike }">
									<a href="/user/reaction/doCancelLike?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
										class="btn btn-error btn-xs">
										<span>좋아요 👍</span>
									</a>
									<span>&nbsp;</span>
									<a href="#" onclick="alert(this.title); return false;" title="먼저 [좋아요 👍]를 취소해주세요."
										class="btn btn-info btn-xs btn-outline">
										<span>싫어요 👎</span>
									</a>
								</c:if>

								<c:if test="${ actorCanCancelDisLike }">
									<a href="#" onclick="alert(this.title); return false;" title="먼저 [싫어요 👎]를 취소해주세요."
										class="btn btn-error btn-xs btn-outline">
										<span>좋아요 👍</span>
									</a>
									<span>&nbsp;</span>
									<a
										href="/user/reaction/doCancelDislike?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
										class="btn btn-info btn-xs">
										<span>싫어요 👎</span>
									</a>
								</c:if>
							</div>
						</td>
					</tr>
					<!-- 좋아요 싫어요 추가, 취소 영역 끝 -->
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

<!-- 댓글 영역 시작 -->
<section class="mt-5">
	<div class="container mx-auto px-3">
		<h2 class="mb-5 font-bold">댓글</h2>
		<c:if test="${ rq.notLogined }">
			<a href="/user/member/login" class="btn btn-link">로그인</a>
				후 댓글을 남길 수 있습니다.
		</c:if>

		<c:if test="${ rq.logined }">
			<form class="table-box-type-1" method="post" action="../reply/doWrite">
				<input type="hidden" name="relTypeCode" value="article" />
				<input type="hidden" name="relId" value="${ article.id }" />
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
							<th>내용</th>
							<td>
								<textarea name="body" rows="5" required="required" placeholder="댓글을 남겨보세요."></textarea>
							</td>
						</tr>
					</tbody>
				</table>

				<div class="btns mt-3">
					<button type="button" class="btn btn-primary btn-sm btn-outline">등록</button>
				</div>
			</form>
		</c:if>
	</div>
</section>
<!-- 댓글 영역 끝 -->
<%@include file="../common/foot.jspf"%>