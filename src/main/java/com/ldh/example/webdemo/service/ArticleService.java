package com.ldh.example.webdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ldh.example.webdemo.repository.ArticleRepository;
import com.ldh.example.webdemo.util.Ut;
import com.ldh.example.webdemo.vo.Article;
import com.ldh.example.webdemo.vo.ResultData;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public int writeArticle(int memberId, int boardId, String title, String body) {

		articleRepository.writeArticle(memberId, boardId, title, body);

		int id = articleRepository.getLastInsertId();

		return id;
	}

	public Article getForPrintArticle(int actorId, int id) {

		Article article = articleRepository.getForPrintArticle(id);

		updateForPrintData(actorId, article);

		return article;
	}

	public List<Article> getForPrintArticles(int actorId, int boardId, int itemsCountInAPage, int page,
			String searchKeyword, String keywordType) {

		int limitStart = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		List<Article> articles = articleRepository.getForPrintArticles(boardId, limitStart, limitTake, searchKeyword,
				keywordType);

		for (Article article : articles) {
			updateForPrintData(actorId, article);
		}

		return articles;
	}

	public Article getArticle(int id) {

		return articleRepository.getArticle(id);
	}

	private void updateForPrintData(int actorId, Article article) {

		if (article == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setExtra__actorCanModify(actorCanDeleteRd.isSuccess());

	}

	public void modifyArticle(int id, String title, String body) {

		articleRepository.modifyArticle(id, title, body);
	}

	public void deleteArticle(int id) {

		articleRepository.deleteArticle(id);
	}

	public ResultData actorCanModify(int actorId, Article article) {

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", article.getId()));
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "수정가능합니다.");
	}

	public ResultData actorCanDelete(int actorId, Article article) {

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", article.getId()));
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "삭제가능합니다.");
	}

	public int getArticlesCount(int boardId, String searchKeyword, String keywordType) {

		return articleRepository.getArticlesCount(boardId, searchKeyword, keywordType);
	}

	public ResultData<Integer> increaseHitCount(int id) {

		int affectedRowsCount = articleRepository.increaseHitCount(id);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", Ut.f("%s번 게시물을 찾을 수 없습니다.", id), "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "조회수가 1만큼 증가합니다.", "affectedRowsCount", affectedRowsCount);
	}

	public int getArticleHitCount(int id) {

		return articleRepository.getArticleHitCount(id);
	}

	public void increaseGoodRP(int id) {

		articleRepository.increaseGoodRP(id);
	}

	public void increaseBadRP(int id) {

		articleRepository.increaseBadRP(id);
	}

	public void decreaseGoodRP(int id) {

		articleRepository.decreaseGoodRP(id);
	}

	public void decreaseBadRP(int id) {

		articleRepository.decreaseBadRP(id);
	}
}
