package com.example.demo.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class Article {
    private Integer id;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String title;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String content;

    private String date;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String creator;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String category;

    private List<@NotNull(message = "Field required")
                 @NotEmpty(message = "Field required")
                 @NotBlank(message = "Field cannot be whitespace") String> tags;

    private Integer visits;
    private Integer likes;
    private Integer dislikes;

    public Article() {
    }

    public Article(String title, String content, String creator, String category, List<String> tags ,Integer visits, Integer likes, Integer dislikes) {
        this();
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.category = category;
        this.tags = tags;
        this.visits = visits;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Article(Integer id, String title, String content, String date, String creator, String category, List<String> tags ,Integer visits, Integer likes, Integer dislikes) {
        this(title, content, creator, category, tags, visits, likes, dislikes);
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
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

    public static String listToString(List<String> tagList) {
        return String.join(",", tagList);
    }

    public static List<String> stringToList(String tagsString) {
        String[] parts = tagsString.split(",");
        return Arrays.asList(parts);
    }
}
