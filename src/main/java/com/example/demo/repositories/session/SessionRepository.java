package com.example.demo.repositories.session;

public interface SessionRepository {
    void visitArticle(Integer articleId);
    void likeArticle(Integer articleId, Integer num);
    void dislikeArticle(Integer articleId, Integer num);
    void likeComment(Integer commentId, Integer num);
    void dislikeComment(Integer commentId, Integer num);
}
