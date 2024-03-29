package com.ldh.example.webdemo.controller;

import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ArticleService;
import com.ldh.example.webdemo.service.BoardService;
import com.ldh.example.webdemo.service.ReactionPointService;
import com.ldh.example.webdemo.service.ReplyService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.Board;
import com.ldh.example.webdemo.vo.Reply;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserArticleController {

	private ArticleService articleService;
	private BoardService boardService;
	private ReplyService replyService;
	private ReactionPointService reactionPointService;
	private Rq rq;

	public UserArticleController(ArticleService articleService, BoardService boardService,
			ReactionPointService reactionPointService, ReplyService replyService, Rq rq) {
		this.articleService = articleService;
		this.boardService = boardService;
		this.replyService = replyService;
		this.reactionPointService = reactionPointService;
		this.rq = rq;
	}

	@RequestMapping("/user/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		// 게시글에 해당하는 댓글 목록 가져오기
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);

		// 리액션 or 리액션 취소 가능여부
		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(), "article",
				id);

		if (actorCanMakeReactionPointRd.getResultCode().equals("F-2")) {
			int sumReactionPoint = (int) actorCanMakeReactionPointRd.getData1();

			if (sumReactionPoint > 0) {
				model.addAttribute("actorCanCancelLike", true);
			}

			else if (sumReactionPoint < 0) {
				model.addAttribute("actorCanCancelDisLike", true);
			}
		}

		model.addAttribute("article", article);
		model.addAttribute("replies", replies);
		model.addAttribute("actorCanMakeRP", actorCanMakeReactionPointRd.isSuccess());

		return "user/article/detail";
	}

	@RequestMapping("/user/article/doIncreaseHitCount")
	@ResponseBody
	public ResultData<Integer> doIncreaseHitCount(int id) {

		ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount",
				articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

	@RequestMapping("/user/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String searchKeyword,
			@RequestParam(defaultValue = "title,body") String keywordType) {

		// 게시판 정보 가져오기
		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.historyBackOnView(Ut.f("%d번 게시판은 등록되지 않았습니다.", boardId));
		}

		// 전체 게시글 갯수(검색사항이 있을 경우, 해당 게시글을 대상으로)
		int articlesCount = articleService.getArticlesCount(boardId, searchKeyword, keywordType);
		// 한 페이지 당 게시글 갯수
		int itemsCountInAPage = 8;
		// 페이지 갯수
		int pagesCount = (int) Math.ceil((double) articlesCount / itemsCountInAPage);

		// 게시글 정보 묶음 가져오기(게시판, 페이지, 검색사항)
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId, itemsCountInAPage,
				page, searchKeyword, keywordType);

		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articles", articles);
		model.addAttribute("articlesCount", articlesCount);

		return "user/article/list";
	}

	@RequestMapping("/user/article/write")
	public String showWrite() {

		return "user/article/write";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public String doWrite(@RequestParam(defaultValue = "0") int boardId, String title, String body, String replaceUri) {

		// 입력데이터 유효성 검사
		if (boardId == 0) {
			return rq.jsHistoryBack("게시판을 선택해주세요.");
		}

		if (Ut.empty(title)) {
			return rq.jsHistoryBack("제목(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용(을)를 입력해주세요.");
		}

		int id = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		return rq.jsReplace(Ut.f("%s번 게시물이 등록되었습니다.", id), replaceUri);
	}

	@RequestMapping("/user/article/modify")
	public String showModify(Model model, int id) {

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
	public String doModify(int id, String title, String body) {

		// 입력데이터 유효성 검사
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("제목(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용(을)를 입력해주세요.");
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
	public String doDelete(int id) {

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