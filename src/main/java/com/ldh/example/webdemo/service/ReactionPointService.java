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

	public boolean actorCanMakeRP(int memberId, String relTypeCode, int relId) {

		if (memberId == 0) {
			return false;
		}

		return reactionPointRepository.getSumReactionPointByMemberId(memberId, relTypeCode, relId) == 0;
	}

	public ResultData doMakeLike(int memberId, String relTypeCode, int relId) {

		reactionPointRepository.doMakeLike(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodRP(relId);
		}

		return ResultData.from("S-1", Ut.f("%s - %d 좋아요 처리했습니다.", relTypeCode, relId));
	}

	public ResultData doMakeDislike(int memberId, String relTypeCode, int relId) {

		reactionPointRepository.doMakeDislike(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadRP(relId);
		}

		return ResultData.from("S-1", Ut.f("%s - %d 싫어요 처리했습니다.", relTypeCode, relId));
	}
}
