package com.ldh.example.webdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ldh.example.webdemo.interceptor.BeforeActionInterceptor;
import com.ldh.example.webdemo.interceptor.NeedLoginInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	// needLoginInterceptor 인터셉터 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// addInterceptors 함수 -> 인터셉터를 적용하는 역할
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/resource/**")
		.excludePathPatterns("/error");

		registry.addInterceptor(needLoginInterceptor)
		.addPathPatterns("/user/article/write")
		.addPathPatterns("/user/article/doWrite")
		.addPathPatterns("/user/article/modify")
		.addPathPatterns("/user/article/doModify")
		.addPathPatterns("/user/article/doDelete");
	}
}
