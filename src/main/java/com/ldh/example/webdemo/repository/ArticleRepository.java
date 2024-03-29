package com.ldh.example.webdemo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ldh.example.webdemo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getForPrintArticle(int id);

	public List<Article> getForPrintArticles(int boardId, int limitStart, int limitTake, String searchKeyword,
			String keywordType);

	public Article getArticle(int id);

	public void writeArticle(int memberId, int boardId, String title, String body);

	public void modifyArticle(int id, String title, String body);

	public void deleteArticle(int id);

	public int getLastInsertId();

	public int getArticlesCount(int boardId, String searchKeyword, String keywordType);

	public int increaseHitCount(int id);

	public int getArticleHitCount(int id);

	public void increaseGoodRP(int id);

	public void increaseBadRP(int id);

	public void decreaseGoodRP(int id);

	public void decreaseBadRP(int id);
}