package com.example.demo.repositories.tag;

import com.example.demo.entities.Article;

import java.util.List;

public interface TagRepository {
    Integer articlesByTagCount(String tag);
    List<Article> articlesByTag(String tag, Integer pageNum);
}
