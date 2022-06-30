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

		Member oldMember = getMemberByLoginId(loginId);

		if (oldMember != null) {
			return -1;
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
}
