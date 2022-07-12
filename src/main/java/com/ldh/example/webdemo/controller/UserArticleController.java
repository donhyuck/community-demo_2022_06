package com.ldh.example.webdemo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping("/user/article/detail")
	public String showDetail(HttpSession httpSession, Model model, int id) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		model.addAttribute("article", article);

		return "user/article/detail";
	}

	@RequestMapping("/user/article/list")
	public String showList(HttpSession httpSession, Model model) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		List<Article> articles = articleService.getForPrintArticles(loginedMemberId);

		model.addAttribute("articles", articles);

		return "user/article/list";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {

		// 로그인 확인후 요청처리
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		// 입력데이터 유효성 검사
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {

		// 로그인 확인후 요청처리
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		if (article == null) {
			return ResultData.from("F-A", Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {

		// 로그인 확인후 요청처리
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanDeleteRd = articleService.actorCanDelete(loginedMemberId, article);

		if (actorCanDeleteRd.isFail()) {
			return actorCanDeleteRd;
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%s번 게시물이 삭제되었습니다.", id), "id", id);
	}
}