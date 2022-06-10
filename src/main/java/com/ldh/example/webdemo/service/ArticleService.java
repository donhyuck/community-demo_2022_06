package com.ldh.example.webdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.vo.Article;

@Service
public class ArticleService {

	public int articleLastId;
	public List<Article> articles;

	public ArticleService() {
		articleLastId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}

	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {

		int id = articleLastId + 1;
		Article article = new Article(id, title, body);

		articles.add(article);
		articleLastId = id;

		return article;
	}

	public Article getArticle(int id) {

		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}

	public void modifyArticle(int id, String title, String body) {

		Article article = getArticle(id);

		article.setTitle(title);
		article.setBody(body);
	}

	public void deleteArticle(int id) {

		Article article = getArticle(id);

		articles.remove(article);
	}
}
