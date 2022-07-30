package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyRepository {

	public void writeReply(int memberId, String relTypeCode, int relId, String body);

	public int getLastInsertId();

}