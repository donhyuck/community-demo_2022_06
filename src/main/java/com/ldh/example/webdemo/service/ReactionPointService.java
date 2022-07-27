package com.ldh.example.webdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ReactionPointRepository;

@Service
public class ReactionPointService {

	private ReactionPointRepository reactionPointRepository;

	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}

	public boolean actorCanMakeRP(int memberId, String relTypeCode, int id) {

		if (memberId == 0) {
			return false;
		}

		return reactionPointRepository.actorCanMakeRP(memberId, relTypeCode, id) == 0;
	}
}
