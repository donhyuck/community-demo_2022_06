package com.ldh.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ReactionPointService;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserReactionController {

	private ReactionPointService reactionPointService;
	private Rq rq;

	public UserReactionController(ReactionPointService reactionPointService, Rq rq) {
		this.reactionPointService = reactionPointService;
		this.rq = rq;
	}

	@RequestMapping("/user/reaction/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId) {

		boolean actorCanMakeReactionPoint = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(), relTypeCode,
				relId);

		if (actorCanMakeReactionPoint == false) {
			return rq.jsHistoryBack("이미 처리되었습니다.");
		}

		return rq.jsReplace("좋아요를 하셨습니다.", "/");
	}

	@RequestMapping("/user/reaction/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId) {

		boolean actorCanMakeReactionPoint = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(), relTypeCode,
				relId);

		if (actorCanMakeReactionPoint == false) {
			return rq.jsHistoryBack("이미 처리되었습니다.");
		}

		return rq.jsReplace("싫어요를 하셨습니다.", "/");
	}

}