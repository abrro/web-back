package com.example.demo.services;

import com.example.demo.entities.Article;
import com.example.demo.repositories.article.ArticleRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ArticleService {
    public ArticleService(){
        System.out.println(this);
    }
    @Inject
    private ArticleRepository articleRepository;

    public Article addArticle(Article article) {
        return this.articleRepository.addArticle(article);
    }

    public Article findArticle(Integer id) {
        return this.articleRepository.findArticle(id);
    }

    public void deleteArticle(Integer id) {
        this.articleRepository.deleteArticle(id);
    }

    public Article modifyArticle(Article article, Integer articleId) {
        return this.articleRepository.modifyArticle(article, articleId);
    }

    public List<Article> allArticles() {
        return this.articleRepository.allArticles();
    }

    public List<Article> allArticlesOnPage(Integer pageNum) {
        return this.articleRepository.allArticlesOnPage(pageNum);
    }
    public Integer articleCount(){
        return this.articleRepository.articlesCount();
    }

    public List<Article> searchArticle(String search, Integer pageNum) {
        return this.articleRepository.searchArticles(search, pageNum);
    }

    public Integer articlesCountSearch(String search) {
        return this.articleRepository.articlesCountSearch(search);
    }

    public List<Article> newestArticles() {
        return this.articleRepository.newestArticles();
    }

    public List<Article> mostVisitedArticles() {
        return this.articleRepository.mostVisitedArticles();
    }

    public List<Article> mostReactionsArticles() {
        return this.articleRepository.mostReactionsArticles();
    }


}
