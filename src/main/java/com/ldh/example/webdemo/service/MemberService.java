package com.ldh.example.webdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.MemberRepository;
import com.ldh.example.webdemo.vo.Member;

@Service
public class MemberService {

	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

		// 회원가입시 로그인아이디 중복 검사
		Member oldMember = getMemberByLoginId(loginId);

		if (oldMember != null) {
			return -1;
		}

		// 회원가입시 이름과 이메일 중복 검사
		oldMember = getMemberByNameAndEmail(name, email);

		if (oldMember != null) {
			return -2;
		}

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNo, email);

		return memberRepository.getLastInsertId();
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
