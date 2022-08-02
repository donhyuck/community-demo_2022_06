package com.ldh.example.webdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ReplyRepository;
import com.ldh.example.webdemo.util.Ut;
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

	public Reply getForPrintReply(int memberId, int id) {

		Reply reply = replyRepository.getForPrintReply(id);
		updateForPrintData(memberId, reply);

		return reply;
	}

	public Reply getReply(int id) {

		return replyRepository.getReply(id);
	}

	private void updateForPrintData(int memberId, Reply reply) {

		if (memberId == 0) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(memberId, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(memberId, reply);
		reply.setExtra__actorCanModify(actorCanDeleteRd.isSuccess());

	}

	public ResultData actorCanDelete(int memberId, Reply reply) {

		if (reply == null) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", reply.getId()));
		}

		if (reply.getMemberId() != memberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "삭제가능합니다.");
	}

	public ResultData actorCanModify(int memberId, Reply reply) {

		if (reply == null) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", reply.getId()));
		}

		if (reply.getMemberId() != memberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "수정가능합니다.");
	}

	public void modifyReply(int id, String body) {

		replyRepository.modifyReply(id, body);
	}

	public void deleteReply(int id) {

		replyRepository.deleteReply(id);
	}

}
