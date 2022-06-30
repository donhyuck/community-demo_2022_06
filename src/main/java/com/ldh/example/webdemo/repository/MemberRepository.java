package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldh.example.webdemo.vo.Member;

@Mapper
public interface MemberRepository {

	public void doJoin(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name,
			@Param("nickname") String nickname, @Param("cellphoneNo") String cellphoneNo, @Param("email") String email);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	public Member getMemberById(@Param("id") int id);

	public Member getMemberByLoginId(@Param("loginId") String loginId);
}