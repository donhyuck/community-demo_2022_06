<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="비밀번호 확인" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../member/doCheckPassword">
			<input type="hidden" name="replaceUri" value="${ param.replaceUri }">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>아이디</th>
						<td>${ rq.loginedMember.loginId }</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input name="loginPw" type="password" required="required" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="비밀번호를 입력해주세요." />
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn btn-outline" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn btn-outline btn-primary">확인</button>
			</div>
		</form>
	</div>
</section>

<%@include file="../common/foot.jspf"%>