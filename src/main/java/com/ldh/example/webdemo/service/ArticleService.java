package com.ldh.example.webdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ArticleRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.ResultData;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData<Integer> writeArticle(int memberId, String title, String body) {

		articleRepository.writeArticle(memberId, title, body);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%s번 게시물이 등록되었습니다.", id), id);
	}

	public Article getArticle(int id) {

		return articleRepository.getArticle(id);
	}

	public List<Article> getArticles() {

		return articleRepository.getArticles();
	}

	public void modifyArticle(int id, String title, String body) {

		articleRepository.modifyArticle(id, title, body);
	}

	public void deleteArticle(int id) {

		articleRepository.deleteArticle(id);
	}
}
