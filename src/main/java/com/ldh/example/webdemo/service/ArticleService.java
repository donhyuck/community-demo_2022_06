package com.ldh.example.webdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ArticleRepository;
import com.ldh.example.webdemo.vo.Article;

@Service
public class ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
		articleRepository.makeTestData();
	}

	public Article writeArticle(String title, String body) {

		return articleRepository.writeArticle(title, body);
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
