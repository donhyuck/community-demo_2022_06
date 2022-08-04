package com.ldh.example.webdemo.service;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.MemberRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Member;
import com.ldh.example.webdemo.vo.ResultData;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	private AttrService attrService;

	public MemberService(MemberRepository memberRepository, AttrService attrService) {
		this.memberRepository = memberRepository;
		this.attrService = attrService;
	}

	public ResultData<Integer> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		// 회원가입시 로그인아이디 중복 검사
		Member oldMember = getMemberByLoginId(loginId);

		if (oldMember != null) {
			return ResultData.from("F-A", Ut.f("입력하신 아이디 [ %s ] 는 이미 사용중입니다.", loginId));
		}

		// 회원가입시 이름과 이메일 중복 검사
		oldMember = getMemberByNameAndEmail(name, email);

		if (oldMember != null) {
			return ResultData.from("F-B", Ut.f("입력하신 이름 [ %s ], 이메일 [ %s ] 은 이미 등록되었습니다.", name, email));
		}

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNo, email);

		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public void doModify(int id, String loginPw, String name, String nickname, String cellphoneNo, String email) {

		memberRepository.doModify(id, loginPw, name, nickname, cellphoneNo, email);
	}

	public String genMemberModifyAuthKey(int memberId) {

		String memberModifyAuthKey = Ut.getTempPassword(10);

		attrService.setValue("member", memberId, "extra", "memberModifyAuthKey", memberModifyAuthKey,
				Ut.getDateStrLater(60 * 5));

		return memberModifyAuthKey;
	}

	public ResultData checkMemberModifyAuthKey(int memberId, String memberModifyAuthKey) {

		String saved = attrService.getValue("member", memberId, "extra", "memberModifyAuthKey");

		if (saved.equals(memberModifyAuthKey) == false) {
			return ResultData.from("F-1", "일치하지 않거나 만료되었습니다.");
		}

		return ResultData.from("S-1", "정상적인 코드입니다.");
	}
}
