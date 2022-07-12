package com.example.demo.services;

import com.example.demo.entities.Article;
import com.example.demo.repositories.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    @Inject
    private TagRepository tagRepository;

    public Integer articlesByTagCount(String tag) {
        return this.tagRepository.articlesByTagCount(tag);
    }

    public List<Article> articlesByTag(String tag, Integer pageNum) {
        return this.tagRepository.articlesByTag(tag, pageNum);
    }
}
