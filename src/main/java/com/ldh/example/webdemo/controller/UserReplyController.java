package com.ldh.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.example.webdemo.service.ReplyService;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.ResultData;
import com.ldh.example.webdemo.vo.Rq;

@Controller
public class UserReplyController {

	private ReplyService replyService;
	private Rq rq;

	public UserReplyController(ReplyService replyService, Rq rq) {
		this.replyService = replyService;
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

		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int replyId = writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {

			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}

		return rq.jsReplace(Ut.f("%s번 댓글이 등록되었습니다.", replyId), replaceUri);
	}
}