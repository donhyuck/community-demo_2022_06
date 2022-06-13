package com.ldh.example.webdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ArticleService;
import com.ldh.example.webdemo.vo.Article;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/user/article/getArticle")
	@ResponseBody
	public Object getArticleForPrint(int id) {

		return articleService.getArticle(id);
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articleService.getArticles();
	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		return articleService.writeArticle(title, body);
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물을 찾을 수 없습니다.";
		}

		articleService.modifyArticle(id, title, body);

		return id + "번 게시물을 수정했습니다.";
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물을 찾을 수 없습니다.";
		}

		articleService.deleteArticle(id);

		return id + "번 게시물을 삭제했습니다.";
	}
}