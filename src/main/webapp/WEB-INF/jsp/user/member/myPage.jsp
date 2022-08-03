<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원정보" />
<%@include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table>
				<c:set var="member" value="${ rq.loginedMember }" />
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>등록번호</th>
						<td>
							<div class="badge badge-primary">${ member.id }</div>
						</td>
					</tr>
					<tr>
						<th>최초 가입일</th>
						<td>
							<div>${ member.regDate }</div>
						</td>
					</tr>
					<tr>
						<th>최근 수정일</th>
						<td>
							<div>${ member.updateDate }</div>
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${ member.name }</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>${ member.nickname }</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${ member.email }</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>${ member.cellphoneNo }</td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- 회원정보 조작 영역 시작 -->
		<div class="btns mt-3">
			<button type="button" class="btn btn-outline" onclick="history.back();">뒤로가기</button>
			<a class="btn btn-primary" href="../member/checkPassword">회원정보 수정</a>
			<a class="btn btn-secondary" href="../member/doLeave?id=${ member.id }"
				onclick="if ( confirm('정말 탈퇴하시겠습니까?') == false) return false;">회원탈퇴</a>
		</div>
		<!-- 회원정보 조작 영역 끝 -->
	</div>
</section>
<%@include file="../common/foot.jspf"%>