<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원정보 수정" />
<%@include file="../common/head.jspf"%>

<!-- 회원정보 수정시 유효성 검사 스크립트 시작 -->
<script>
	let MemberModify__submitFormDone = false;

	function MemberModify__submitForm(form) {

		if (MemberModify__submitFormDone) {
			return;
		}

		// 비밀번호 확인
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length > 0) {

			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호 재확인(을)를 입력해주세요.');
				form.loginPwConfirm.focus();
				return;
			}

			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('비밀번호가 일치하지 않습니다.');
				form.loginPwConfirm.focus();
				return;
			}
		}

		// 좌우 공백 제거
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

		MemberModify__submitFormDone = true;
		form.submit();
	}
</script>
<!-- 회원정보 수정시 유효성 검사 스크립트 끝 -->

<section class="mt-5">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="post" action="../member/doModify"
			onsubmit="MemberModify__submitForm(this); return false;">
			<input type="hidden" name="memberModifyAuthKey" value="${ param.memberModifyAuthKey }">
			<table>
				<c:set var="member" value="${ rq.loginedMember }" />
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>로그인 아이디</th>
						<td>${ member.loginId }</td>
					</tr>
					<tr>
						<th>새로운 비밀번호</th>
						<td>
							<input name="loginPw" type="password" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="비밀번호를 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>비밀번호 재확인</th>
						<td>
							<input name="loginPwConfirm" type="password" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="다시 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input name="name" type="text" value="${ member.name }" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="이름을 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>별명</th>
						<td>
							<input name="nickname" type="text" value="${ member.nickname }" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="별명을 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>
							<input name="cellphoneNo" type="text" value="${ member.cellphoneNo }"
								class="w-96 input input-bordered w-full max-w-xs" placeholder="예) (-) 제외하여 입력해주세요." />
						</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>
							<input name="email" type="email" value="${ member.email }" class="w-96 input input-bordered w-full max-w-xs"
								placeholder="이메일을 입력해주세요." />
						</td>
					</tr>
				</tbody>
			</table>

			<div class="btns mt-3">
				<button type="button" class="btn btn-outline" onclick="history.back();">뒤로가기</button>
				<button type="submit" class="btn btn-outline btn-primary">정보수정</button>
			</div>
		</form>
	</div>
</section>

<%@include file="../common/foot.jspf"%>