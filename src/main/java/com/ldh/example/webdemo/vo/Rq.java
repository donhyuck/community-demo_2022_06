package com.ldh.example.webdemo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.ldh.example.webdemo.service.MemberService;
import com.ldh.example.webdemo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {

	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {

		this.req = req;
		this.resp = resp;
		this.session = req.getSession();

		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;

		this.req.setAttribute("rq", this);
	}

	public void printHistoryBackJs(String msg) {

		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));

	}

	public void print(String str) {

		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {

		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {

		session.removeAttribute("loginedMemberId");
	}

	public String historyBackOnView(String msg) {

		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);

		return "common/js";
	}

	public String jsHistoryBack(String msg) {

		resp.setContentType("text/html; charset=UTF-8");
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {

		resp.setContentType("text/html; charset=UTF-8");
		return Ut.jsReplace(msg, uri);
	}
}
