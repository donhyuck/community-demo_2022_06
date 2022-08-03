package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldh.example.webdemo.vo.Member;

@Mapper
public interface MemberRepository {

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	public Member getMemberById(int id);

	public Member getMemberByLoginId(String loginId);

	public Member getMemberByNameAndEmail(String name, String email);

	public void doModify(int id, String loginPw, String name, String nickname, String cellphoneNo, String email);
}