<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원가입" />
<%@include file="../common/head.jspf"%>

<!-- 회원 가입시 유효성 검사 스크립트 시작 -->
<script>
	let MemberJoin__submitFormDone = false;

	function MemberJoin__submitForm(form) {

		if (MemberJoin__submitFormDone) {
			return;
		}

		// 좌우 공백 제거
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디(을)를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호(을)를 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름(을)를 입력해주세요.');
			form.name.focus();
			return;
		}

		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('별명(을)를 입력해주세요.');
			form.nickname.focus();
			return;
		}

		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일(을)를 입력해주세요.');
			form.email.focus();
			return;
		}

		form.cellphoneNo.value = form.cellphoneNo.value.trim();
		if (form.cellphoneNo.value.length == 0) {
			alert('연락처(을)를 입력해주세요.');
			form.cellphoneNo.focus();
			return;
		}

		MemberJoin__submitFormDone = true;
		form.submit();
	}
</script>
<!-- 회원 가입시 유효성 검사 스크립트 끝 -->

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../member/doJoin"
			onsubmit="MemberJoin__submitForm(this); return false;">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>로그인 아이디</th>
						<td>
							<input name="loginId" type="text" class="w-96 input input-bordered w-full max-w-xs" placeholder="아이디를 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>로그인 비밀번호</th>
						<td>
							<input name="loginPw" type="password" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="비밀번호를 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input name="name" type="text" class="w-96 input input-bordered w-full max-w-xs" placeholder="이름을 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>별명</th>
						<td>
							<input name="nickname" type="text" class="w-96 input input-bordered w-full max-w-xs" placeholder="별명을 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>
							<input name="cellphoneNo" type="text" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="예) (-) 제외하여 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>
							<input name="email" type="email" class="w-96 input input-bordered w-full max-w-xs" placeholder="이메일을 입력해주세요." />
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn btn-outline" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn btn-outline btn-primary">회원가입</button>
			</div>
		</form>
	</div>
</section>

<%@include file="../common/foot.jspf"%>