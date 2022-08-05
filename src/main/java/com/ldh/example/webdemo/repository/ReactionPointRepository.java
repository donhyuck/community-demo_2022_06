package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionPointRepository {

	public int getSumReactionPointByMemberId(int memberId, String relTypeCode, int id);

	public void doReaction(int memberId, String relTypeCode, int relId, int point);

	public void doCancelReaction(int memberId, String relTypeCode, int relId);
}