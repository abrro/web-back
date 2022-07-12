package com.example.demo.repositories.comment;

import com.example.demo.entities.Comment;

import java.util.List;

public interface CommentRepository {
    public Comment addComment(Comment comment, Integer articleId);
    public List<Comment> allComments(Integer articleId, Integer pageNum);
    Integer count(Integer id);
}
