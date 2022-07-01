package com.ldh.example.webdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.MemberRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Member;
import com.ldh.example.webdemo.vo.ResultData;

@Service
public class MemberService {

	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
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

		return ResultData.from("S-1", "회원가입이 완료되었습니다.", id);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
}
