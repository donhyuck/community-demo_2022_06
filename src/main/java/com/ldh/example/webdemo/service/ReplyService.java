package com.ldh.example.webdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ReplyRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.Reply;
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

	public List<Reply> getForPrintReplies(int memberId, String relTypeCode, int relId) {

		List<Reply> replies = replyRepository.getForPrintReplies(relTypeCode, relId);

		for (Reply reply : replies) {
			updateForPrintData(memberId, reply);
		}

		return replies;
	}

	private void updateForPrintData(int memberId, Reply reply) {
		// 구현 예정
	}

}
