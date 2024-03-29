package com.ldh.example.webdemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ldh.example.webdemo.vo.Rq;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {

	private Rq rq;

	public NeedLogoutInterceptor(Rq rq) {
		this.rq = rq;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

		// 로그이웃 확인후 요청처리
		if (rq.isLogined() == true) {
			rq.printHistoryBackJs("로그아웃 후 이용해주세요.");
			return false;
		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}
