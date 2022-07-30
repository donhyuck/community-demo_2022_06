package com.ldh.example.webdemo.service;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ReplyRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.ResultData;

@Service
public class ReplyService {

	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public ResultData<Integer> writeReply(int memberId, String relTypeCode, int relId, String body) {

		replyRepository.writeReply(memberId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%s번 댓글이 등록되었습니다.", id), "id", id);
	}

}
