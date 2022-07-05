package com.ldh.example.webdemo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ArticleService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.ResultData;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/user/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-A", Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%s번 게시물입니다.", id), article);
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {

		List<Article> articles = articleService.getArticles();

		return ResultData.from("S-1", "게시글 목록입니다.", articles);
	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {

		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getArticle(id);

		return ResultData.newData(writeArticleRd, article);
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(HttpSession httpSession, int id, String title, String body) {

		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-A", Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		articleService.modifyArticle(id, title, body);

		return ResultData.from("S-1", Ut.f("%s번 게시물이 수정되었습니다.", id), id);
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {

		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", id), id);
		}

		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%s번 게시물이 삭제되었습니다.", id), id);
	}
}