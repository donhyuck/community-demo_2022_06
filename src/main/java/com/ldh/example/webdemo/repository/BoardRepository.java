package com.ldh.example.webdemo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ldh.example.webdemo.vo.Board;

@Mapper
public interface BoardRepository {

	public Board getBoardById(@Param("id") int id);

}