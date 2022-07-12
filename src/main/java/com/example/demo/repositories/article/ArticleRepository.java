package com.example.demo.repositories.article;

import com.example.demo.entities.Article;

import java.util.List;

public interface ArticleRepository {
    public Article addArticle(Article article);
    public Article findArticle(Integer id);
    public void deleteArticle(Integer id);
    public Article modifyArticle(Article article, Integer articleId);
    public List<Article> allArticles();
    public List<Article> allArticlesOnPage(Integer pageNum);
    public Integer articlesCount();
    public List<Article> searchArticles(String search, Integer pageNum);
    Integer articlesCountSearch(String search);
    List<Article> newestArticles();
    List<Article> mostVisitedArticles();
    List<Article> mostReactionsArticles();
}
