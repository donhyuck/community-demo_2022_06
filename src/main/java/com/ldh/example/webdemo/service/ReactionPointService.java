package com.ldh.example.webdemo.service;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ReactionPointRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.ResultData;

@Service
public class ReactionPointService {

	private ReactionPointRepository reactionPointRepository;
	private ArticleService articleService;

	public ReactionPointService(ReactionPointRepository reactionPointRepository, ArticleService articleService) {
		this.reactionPointRepository = reactionPointRepository;
		this.articleService = articleService;
	}

	public ResultData actorCanMakeRP(int memberId, String relTypeCode, int relId) {

		if (memberId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}

		int sumReactionPoint = reactionPointRepository.getSumReactionPointByMemberId(memberId, relTypeCode, relId);

		if (sumReactionPoint != 0) {
			return ResultData.from("F-2", "리액션이 불가능합니다.", "sumReactionPoint", sumReactionPoint);
		}

		return ResultData.from("S-1", "리액션이 가능합니다.", "sumReactionPoint", sumReactionPoint);
	}

	public ResultData doReaction(int memberId, String relTypeCode, int relId, int point) {

		String forPrintCodeName = "";
		String forPrintFeedbackName = "";

		switch (relTypeCode) {
		case "article":
			forPrintCodeName = "게시글";

			if (point > 0) {
				forPrintFeedbackName = "좋아요";
				articleService.increaseGoodRP(relId);
				reactionPointRepository.doMakeLike(memberId, relTypeCode, relId);

			} else if (point < 0) {
				forPrintFeedbackName = "싫어요";
				articleService.increaseBadRP(relId);
				reactionPointRepository.doMakeDislike(memberId, relTypeCode, relId);
			}
		}

		return ResultData.from("S-1", Ut.f("%d 번 %s [%s] 처리", relId, forPrintCodeName, forPrintFeedbackName));
	}

	public ResultData doCancelLike(int memberId, String relTypeCode, int relId) {

		String forPrintCodeName = "";
		reactionPointRepository.doCancelLike(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodRP(relId);
			forPrintCodeName = "게시글";
		}

		return ResultData.from("S-1", Ut.f("%d 번 %s [좋아요] 취소", relId, forPrintCodeName));
	}

	public ResultData doCancelDislike(int memberId, String relTypeCode, int relId) {

		String forPrintCodeName = "";
		reactionPointRepository.doCancelDislike(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadRP(relId);
			forPrintCodeName = "게시글";
		}

		return ResultData.from("S-1", Ut.f("%d 번 %s [싫어요] 취소", relId, forPrintCodeName));
	}
}
