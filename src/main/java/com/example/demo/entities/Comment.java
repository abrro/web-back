package com.example.demo.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {

    private Integer id;

    private Integer articleId;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String author;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String text;

    private String timestamp;
    private Integer likes;
    private Integer dislikes;

    public Comment() {
    }

    public Comment(String author, String text, Integer likes, Integer dislikes) {
        this();
        this.author = author;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Comment(Integer id, Integer articleId, String author, String text, String timestamp, Integer likes, Integer dislikes) {
        this(author, text, likes, dislikes);
        this.id = id;
        this.articleId = articleId;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }
}
