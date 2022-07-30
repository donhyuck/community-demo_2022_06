package com.ldh.example.webdemo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ldh.example.webdemo.vo.Reply;

@Mapper
public interface ReplyRepository {

	public void writeReply(int memberId, String relTypeCode, int relId, String body);

	public int getLastInsertId();

	public List<Reply> getForPrintReplies(String relTypeCode, int relId);

}