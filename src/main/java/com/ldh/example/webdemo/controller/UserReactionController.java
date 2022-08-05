package com.ldh.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ReactionPointService;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserReactionController {

	private ReactionPointService reactionPointService;
	private Rq rq;

	public UserReactionController(ReactionPointService reactionPointService, Rq rq) {
		this.reactionPointService = reactionPointService;
		this.rq = rq;
	}

	@RequestMapping("/user/reaction/doReaction")
	@ResponseBody
	public String doReaction(String relTypeCode, int relId, String replaceUri, int point) {

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(), relTypeCode,
				relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionRd.getMsg());
		}

		ResultData doReactionRd = reactionPointService.doReaction(rq.getLoginedMemberId(), relTypeCode, relId, point);

		return rq.jsReplace(doReactionRd.getMsg(), replaceUri);
	}

	@RequestMapping("/user/reaction/doCancelReaction")
	@ResponseBody
	public String doCancelReaction(String relTypeCode, int relId, String replaceUri, int point) {

		ResultData actorCanCancelReactionRd = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(), relTypeCode,
				relId);

		if (actorCanCancelReactionRd.isSuccess()) {
			return rq.jsHistoryBack("이미 취소되었습니다.");
		}

		ResultData doCancelReactionRd = reactionPointService.doCancelReaction(rq.getLoginedMemberId(), relTypeCode,
				relId, point);

		return rq.jsReplace(doCancelReactionRd.getMsg(), replaceUri);
	}
}