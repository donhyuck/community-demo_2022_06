package com.ldh.example.webdemo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldh.example.webdemo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getArticle(int id);

	public List<Article> getArticles();

	public Article writeArticle(String title, String body);

	public void modifyArticle(int id, String title, String body);

	public void deleteArticle(int id);
}