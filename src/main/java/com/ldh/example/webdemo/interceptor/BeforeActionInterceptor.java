package com.ldh.example.webdemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ldh.example.webdemo.service.MemberService;
import com.ldh.example.webdemo.vo.Rq;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {

	@Autowired
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

		Rq rq = new Rq(req, resp, memberService);
		req.setAttribute("rq", rq);

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}
