package com.ldh.example.webdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.MemberService;

@Controller
public class UserMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNo, email);

	}
}