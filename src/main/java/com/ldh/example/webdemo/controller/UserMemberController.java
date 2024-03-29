package com.ldh.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.MemberService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Member;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserMemberController {

	private MemberService memberService;
	private Rq rq;

	public UserMemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}

	@RequestMapping("/user/member/join")
	public String showJoin() {

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
	public String showLogin() {

		return "user/member/login";
	}

	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		// 로그인 확인
		if (rq.isLogined() == true) {
			return rq.jsHistoryBack("이미 로그인 중입니다.");
		}

		// 입력데이터 유효성 검사
		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("아이디(을)를 입력해주세요.");
		}

		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호(을)를 입력해주세요.");
		}

		// 미등록 회원 제외, 비밀번호확인
		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return rq.jsHistoryBack("등록되지 않은 회원입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return rq.jsHistoryBack("잘못된 비밀번호입니다.");
		}

		rq.login(member);

		return rq.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), afterLoginUri);
	}

	@RequestMapping("/user/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		// 로그아웃 확인
		if (rq.isLogined() == false) {
			return rq.jsHistoryBack("이미 로그아웃 되었습니다.");
		}

		rq.logout();

		return rq.jsReplace("로그아웃 처리되었습니다.", afterLogoutUri);
	}

	@RequestMapping("/user/member/myPage")
	public String showMyPage() {

		return "user/member/myPage";
	}

	@RequestMapping("/user/member/checkPassword")
	public String showCheckPW() {

		return "user/member/checkPassword";
	}

	@RequestMapping("/user/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw, String replaceUri) {

		// 입력데이터 유효성 검사
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호(을)를 입력해주세요.");
		}

		// 비밀번호 확인
		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return rq.jsHistoryBack("잘못된 비밀번호 입니다.");
		}

		// 인증코드 발급
		if (replaceUri.equals("../member/modify")) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId());

			replaceUri += "?memberModifyAuthKey=" + memberModifyAuthKey;
		}

		return rq.jsReplace("", replaceUri);
	}

	@RequestMapping("/user/member/modify")
	public String showModify(String memberModifyAuthKey) {

		// 회원수정으로 바로 이동하는 경우
		if (Ut.empty(memberModifyAuthKey)) {
			return rq.historyBackOnView("비밀번호 확인이 필요합니다. 잘못된 접근입니다.");
		}

		// 인증코드 확인
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.historyBackOnView(checkMemberModifyAuthKeyRd.getMsg());
		}

		return "user/member/modify";
	}

	@RequestMapping("/user/member/doModify")
	@ResponseBody
	public String doModify(String memberModifyAuthKey, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		// 회원수정으로 바로 이동하는 경우
		if (Ut.empty(memberModifyAuthKey)) {
			return rq.jsHistoryBack("비밀번호 확인이 필요합니다. 잘못된 접근입니다.");
		}

		// 인증코드 확인
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBack(checkMemberModifyAuthKeyRd.getMsg());
		}

		// 입력데이터 유효성 검사
		if (Ut.empty(loginPw)) {
			loginPw = null;
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

		// 회원정보 수정
		memberService.doModify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNo, email);

		// 수정 후 로그아웃
		rq.logout();

		return Ut.jsReplace(Ut.f("%s님 회원정보가 수정되었습니다. 로그인 후 이용해주세요.", nickname), "/user/member/login");
	}

}