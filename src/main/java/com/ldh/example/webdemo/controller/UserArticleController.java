package com.ldh.example.webdemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.vo.Article;

@Controller
public class UserArticleController {

	// 인스턴스 변수 시작
	private int articleLastId;
	private List<Article> articles;
	// 인스턴스 변수 끝

	// 생성자
	public UserArticleController() {
		articleLastId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}

	// 서비스 메서드 시작
	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			int id = articleLastId + 1;
			String title = "제목" + i;
			String body = "내용" + i;
			Article article = new Article(id, title, body);

			articles.add(article);
			articleLastId = id;
		}
	}
	// 서비스 메서드 끝

	// 액션 메서드 시작
	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		int id = articleLastId + 1;
		Article article = new Article(id, title, body);

		articles.add(article);
		articleLastId = id;

		return article;
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;
	}
	// 액션 메서드 끝
}