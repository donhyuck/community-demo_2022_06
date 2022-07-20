package com.ldh.example.webdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ArticleService;
import com.ldh.example.webdemo.service.BoardService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.Board;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserArticleController {

	private ArticleService articleService;
	private BoardService boardService;

	public UserArticleController(ArticleService articleService, BoardService boardService) {
		this.articleService = articleService;
		this.boardService = boardService;
	}

	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "user/article/detail";
	}

	@RequestMapping("/user/article/list")
	public String showList(HttpServletRequest req, Model model, int boardId) {

		Rq rq = (Rq) req.getAttribute("rq");

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.historyBackOnView(Ut.f("%d번 게시판은 등록되지 않았습니다.", boardId));
		}

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId);

		model.addAttribute("board", board);
		model.addAttribute("articles", articles);

		return "user/article/list";
	}

	@RequestMapping("/user/article/write")
	public String showWrite(HttpServletRequest req, Model model) {

		return "user/article/write";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 입력데이터 유효성 검사
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body(을)를 입력해주세요.");
		}

		int id = articleService.writeArticle(rq.getLoginedMemberId(), title, body);

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		return rq.jsReplace(Ut.f("%s번 게시물이 등록되었습니다.", id), replaceUri);
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
			return rq.jsHistoryBack("title(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body(을)를 입력해주세요.");
		}

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.f("%s번 게시물이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 데이터와 권한 확인
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%s번 게시물을 찾을 수 없습니다.", id));
		}

		ResultData actorCanDeleteRd = articleService.actorCanDelete(rq.getLoginedMemberId(), article);

		if (actorCanDeleteRd.isFail()) {
			return rq.jsHistoryBack(actorCanDeleteRd.getMsg());
		}

		articleService.deleteArticle(id);

		return rq.jsReplace(Ut.f("%s번 게시물이 삭제되었습니다.", id), "../article/list");
	}
}