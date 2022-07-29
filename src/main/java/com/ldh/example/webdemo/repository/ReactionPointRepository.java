package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionPointRepository {

	public int getSumReactionPointByMemberId(int memberId, String relTypeCode, int id);

	public void doMakeLike(int memberId, String relTypeCode, int relId);

	public void doMakeDislike(int memberId, String relTypeCode, int relId);

	public void doCancelLike(int memberId, String relTypeCode, int relId);

	public void doCancelDislike(int memberId, String relTypeCode, int relId);
}