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

	@RequestMapping("/user/member/join")
	public String showJoin(HttpSession httpSession) {

		return "user/member/join";
	}

	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		// 입력데이터 유효성 검사
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("아이디(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("비밀번호(을)를 입력해주세요.");
		}

		if (Ut.empty(name)) {
			return Ut.jsHistoryBack("이름(을)를 입력해주세요.");
		}

		if (Ut.empty(nickname)) {
			return Ut.jsHistoryBack("별명(을)를 입력해주세요.");
		}

		if (Ut.empty(cellphoneNo)) {
			return Ut.jsHistoryBack("연락처(을)를 입력해주세요.");
		}

		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("이메일(을)를 입력해주세요.");
		}

		// 회원가입 가능여부 검사
		ResultData joinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack(joinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return Ut.jsReplace(Ut.f("%s님 회원가입이 완료되었습니다.", member.getName()), "/");
	}

	@RequestMapping("/user/member/login")
	public String showLogin(HttpSession httpSession) {

		return "user/member/login";
	}

	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public String doLogin(HttpSession httpSession, String loginId, String loginPw) {

		// 로그인 확인
		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined == true) {
			return Ut.jsHistoryBack("이미 로그인 중입니다.");
		}

		// 입력데이터 유효성 검사
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("아이디(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("비밀번호(을)를 입력해주세요.");
		}

		// 미등록 회원 제외, 비밀번호확인
		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("등록되지 않은 회원입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("잘못된 비밀번호입니다.");
		}

		httpSession.setAttribute("loginedMemberId", member.getId());

		return Ut.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/");
	}

	@RequestMapping("/user/member/doLogout")
	@ResponseBody
	public String doLogout(HttpSession httpSession) {

		// 로그아웃 확인
		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}

		if (isLogined == false) {
			return Ut.jsHistoryBack("이미 로그아웃 되었습니다.");
		}

		httpSession.removeAttribute("loginedMemberId");

		return Ut.jsReplace("로그아웃 처리되었습니다.", "/");
	}

}