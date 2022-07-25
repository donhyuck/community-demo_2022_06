package com.ldh.example.webdemo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ldh.example.webdemo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getForPrintArticle(int id);

	public List<Article> getForPrintArticles(int boardId, int limitStart, int limitTake, String searchKeyword, String keywordType);

	public void writeArticle(int memberId, int boardId, String title,String body);

	public void modifyArticle(int id, String title, String body);

	public void deleteArticle(int id);

	public int getLastInsertId();

	public int getArticlesCount(int boardId, String searchKeyword, String keywordType);

	public int increaseHitCount(int id);
}