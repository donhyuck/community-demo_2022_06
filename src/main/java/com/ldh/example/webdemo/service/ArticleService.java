package com.ldh.example.webdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ArticleRepository;
import com.ldh.example.webdemo.vo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public int writeArticle(String title, String body) {

		articleRepository.writeArticle(title, body);
		return articleRepository.getLastInsertId();
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
