package com.ldh.example.webdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.MemberService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Member;

@Controller
public class UserMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		if (Ut.empty(loginId)) {
			return "loginId(을)를 입력해주세요.";
		}

		if (Ut.empty(loginPw)) {
			return "loginPw(을)를 입력해주세요.";
		}

		if (Ut.empty(name)) {
			return "name(을)를 입력해주세요.";
		}

		if (Ut.empty(nickname)) {
			return "nickname(을)를 입력해주세요.";
		}

		if (Ut.empty(cellphoneNo)) {
			return "cellphoneNo(을)를 입력해주세요.";
		}

		if (Ut.empty(email)) {
			return "email(을)를 입력해주세요.";
		}

		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (id == -1) {
			return Ut.f("입력하신 아이디는 [ %s ] 이미 사용중입니다.", loginId);
		}

		if (id == -2) {
			return Ut.f("입력하신 이름 [ %s ], 이메일 [ %s ] 은 이미 등록되었습니다.", name, email);
		}

		Member member = memberService.getMemberById(id);

		return member;
	}
}