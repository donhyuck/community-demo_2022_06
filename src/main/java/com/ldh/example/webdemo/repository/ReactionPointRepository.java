package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionPointRepository {

	public int actorCanMakeRP(int memberId, String relTypeCode, int id);
}