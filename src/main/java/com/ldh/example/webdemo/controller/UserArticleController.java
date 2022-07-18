package com.ldh.example.webdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ArticleService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "user/article/detail";
	}

	@RequestMapping("/user/article/list")
	public String showList(HttpServletRequest req, Model model) {

		Rq rq = (Rq) req.getAttribute("rq");

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());

		model.addAttribute("articles", articles);

		return "user/article/list";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 입력데이터 유효성 검사
		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("body(을)를 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		return Ut.jsReplace(Ut.f("%s번 게시물이 등록되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}

	@RequestMapping("/user/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.historyBackOnView(Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.historyBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		return "user/article/modify";
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 입력데이터 유효성 검사
		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("body(을)를 입력해주세요.");
		}

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return Ut.jsHistoryBack(Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return Ut.jsReplace(Ut.f("%s번 게시물이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return Ut.jsHistoryBack(Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanDeleteRd = articleService.actorCanDelete(rq.getLoginedMemberId(), article);

		if (actorCanDeleteRd.isFail()) {
			return Ut.jsHistoryBack(actorCanDeleteRd.getMsg());
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%s번 게시물이 삭제되었습니다.", id), "../article/list");
	}
}