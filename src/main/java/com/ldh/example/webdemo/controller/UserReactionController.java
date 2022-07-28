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

	@RequestMapping("/user/reaction/doMakeLike")
	@ResponseBody
	public String doMakeLike(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionPointRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionPointRd.getMsg());
		}

		ResultData doMakeLikeRd = reactionPointService.doMakeLike(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace(doMakeLikeRd.getMsg(), replaceUri);
	}

	@RequestMapping("/user/reaction/doMakeDislike")
	@ResponseBody
	public String doMakeDislike(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeRP(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionPointRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionPointRd.getMsg());
		}

		ResultData doMakeDislikeRd = reactionPointService.doMakeDislike(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace(doMakeDislikeRd.getMsg(), replaceUri);
	}

}