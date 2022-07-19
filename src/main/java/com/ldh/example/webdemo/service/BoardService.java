package com.ldh.example.webdemo.service;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.BoardRepository;
import com.ldh.example.webdemo.vo.Board;

@Service
public class BoardService {

	private BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public Board getBoardById(int id) {

		return boardRepository.getBoardById(id);
	}

}
