package com.ldh.example.webdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ldh.example.webdemo.interceptor.BeforeActionInterceptor;
import com.ldh.example.webdemo.interceptor.NeedLoginInterceptor;
import com.ldh.example.webdemo.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// needLoginInterceptor 인터셉터 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// needLogoutInterceptor 인터셉터 불러오기
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;

	// addInterceptors 함수 -> 인터셉터를 적용하는 역할
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		InterceptorRegistration ir;

		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");

		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/user/article/write");
		ir.addPathPatterns("/user/article/doWrite");
		ir.addPathPatterns("/user/article/modify");
		ir.addPathPatterns("/user/article/doModify");
		ir.addPathPatterns("/user/article/doDelete");
		ir.addPathPatterns("/user/reply/doWrite");
		ir.addPathPatterns("/user/reply/modify");
		ir.addPathPatterns("/user/reply/doModify");
		ir.addPathPatterns("/user/reply/doDelete");
		ir.addPathPatterns("/user/reaction/doMakeLike");
		ir.addPathPatterns("/user/reaction/doMakeDislike");
		ir.addPathPatterns("/user/reaction/doCancelLike");
		ir.addPathPatterns("/user/reaction/doCancelDislike");
		ir.addPathPatterns("/user/member/myPage");
		ir.addPathPatterns("/user/member/checkPassword");
		ir.addPathPatterns("/user/member/doCheckPassword");
		ir.addPathPatterns("/user/member/modify");
		ir.addPathPatterns("/user/member/doModify");

		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/user/member/join");
		ir.addPathPatterns("/user/member/doJoin");
		ir.addPathPatterns("/user/member/login");
		ir.addPathPatterns("/user/member/doLogin");
		ir.addPathPatterns("/user/member/findLoginId");
		ir.addPathPatterns("/user/member/doFindLoginId");
		ir.addPathPatterns("/user/member/findLoginPw");
		ir.addPathPatterns("/user/member/doFindLoginPw");
	}
}
