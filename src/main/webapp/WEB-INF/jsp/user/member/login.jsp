<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="로그인" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../member/doLogin">
			<input type="hidden" name="afterLoginUri" value="${param.afterLoginUri}" />
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>로그인 아이디</th>
						<td>
							<input name="loginId" type="text" required="required" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="아이디를 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>로그인 비밀번호</th>
						<td>
							<input name="loginPw" type="password" required="required" class=" w-96 input input-bordered w-full max-w-xs"
								placeholder="비밀번호를 입력해주세요." />
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn btn-outline" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn btn-outline btn-primary">로그인</button>
			</div>
		</form>
	</div>
</section>

<%@include file="../common/foot.jspf"%>