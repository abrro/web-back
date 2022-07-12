package com.example.demo.services;

import com.example.demo.entities.Comment;
import com.example.demo.repositories.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    public CommentService() {
        System.out.println(this);
    }

    @Inject
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment, Integer articleId) {
        return this.commentRepository.addComment(comment, articleId);
    }

    public List<Comment> allComments(Integer articleId, Integer pageNum) {
        return this.commentRepository.allComments(articleId, pageNum);
    }

    public Integer count(Integer id) {
        return this.commentRepository.count(id);
    }
}
