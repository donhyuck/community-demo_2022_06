package com.ldh.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ArticleService;
import com.ldh.example.webdemo.service.ReplyService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.Reply;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserReplyController {

	private ReplyService replyService;
	private ArticleService articleService;
	private Rq rq;

	public UserReplyController(ReplyService replyService, ArticleService articleService, Rq rq) {
		this.replyService = replyService;
		this.articleService = articleService;
		this.rq = rq;
	}

	@RequestMapping("/user/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		// 입력데이터 유효성 검사
		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode(을)를 입력해주세요.");
		}

		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("relId(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body(을)를 입력해주세요.");
		}

		// 댓글 등록
		int replyId = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		if (Ut.empty(replaceUri)) {

			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}

		return rq.jsReplace(Ut.f("%s번 댓글이 등록되었습니다.", replyId), replaceUri);
	}

	@RequestMapping("/user/reply/modify")
	public String showModify(Model model, int id, String replaceUri) {

		// 데이터와 권한 확인
		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("%s번 댓글을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.historyBackOnView(actorCanModifyRd.getMsg());
		}

		// 댓글에 등록된 게시글 제목을 가져오도록
		String relDataTitle = "";
		String relDataBody = "";

		switch (reply.getRelTypeCode()) {
		case "article":
			Article article = articleService.getArticle(reply.getRelId());
			relDataTitle = article.getTitle();
			relDataBody = article.getBody();
		}

		model.addAttribute("reply", reply);
		model.addAttribute("relDataTitle", relDataTitle);
		model.addAttribute("relDataBody", relDataBody);

		return "user/reply/modify";
	}

	@RequestMapping("/user/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {

		// 입력데이터 유효성 검사
		if (Ut.empty(id)) {
			return rq.jsHistoryBack("댓글 번호(을)를 입력해주세요.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용(을)를 입력해주세요.");
		}

		// 데이터와 권한 확인
		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("%s번 댓글을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}

		replyService.modifyReply(id, body);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return rq.jsReplace(Ut.f("%s번 댓글이 수정되었습니다.", id), replaceUri);
	}

	@RequestMapping("/user/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		// 데이터와 권한 확인
		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("%s번 댓글을 찾을 수 없습니다.", id));
		}

		ResultData actorCanDeleteRd = replyService.actorCanDelete(rq.getLoginedMemberId(), reply);

		if (actorCanDeleteRd.isFail()) {
			return rq.jsHistoryBack(actorCanDeleteRd.getMsg());
		}

		replyService.deleteReply(id);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return rq.jsReplace(Ut.f("%s번 댓글이 삭제되었습니다.", id), replaceUri);
	}
}