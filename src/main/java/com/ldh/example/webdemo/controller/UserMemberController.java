package com.ldh.example.webdemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.MemberService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Member;
import com.ldh.example.webdemo.vo.ResultData;

@Controller
public class UserMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		// 입력데이터 유효성 검사
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}

		if (Ut.empty(name)) {
			return ResultData.from("F-3", "name(을)를 입력해주세요.");
		}

		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "nickname(을)를 입력해주세요.");
		}

		if (Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5", "cellphoneNo(을)를 입력해주세요.");
		}

		if (Ut.empty(email)) {
			return ResultData.from("F-6", "email(을)를 입력해주세요.");
		}

		// 회원가입 가능여부 검사
		ResultData joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return ResultData.newData(joinRd, "member", member);
	}

	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) {

		// 로그인 확인
		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined == true) {
			return ResultData.from("F-5", "이미 로그인 중입니다.");
		}

		// 입력데이터 유효성 검사
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}

		// 미등록 회원 제외, 비밀번호확인
		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return ResultData.from("F-3", "등록되지 않은 회원입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-4", "잘못된 비밀번호입니다.");
		}

		httpSession.setAttribute("loginedMemberId", member.getId());

		return ResultData.from("S-1", Ut.f("%s님 환영합니다.", member.getNickname()));
	}

	@RequestMapping("/user/member/doLogout")
	@ResponseBody
	public ResultData<Member> doLogout(HttpSession httpSession) {

		// 로그아웃 확인
		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined == false) {
			return ResultData.from("S-1", "이미 로그아웃 되었습니다.");
		}

		httpSession.removeAttribute("loginedMemberId");

		return ResultData.from("S-2", "로그아웃 처리되었습니다.");
	}

}