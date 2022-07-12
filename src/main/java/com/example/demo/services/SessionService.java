package com.example.demo.services;

import com.example.demo.repositories.session.SessionRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class SessionService {

    @Inject
    private SessionRepository sessionRepository;

    public void visitArticle(Integer articleId) {
        this.sessionRepository.visitArticle(articleId);
    }

    public void likeArticle(Integer articleId, Integer num) {
        this.sessionRepository.likeArticle(articleId, num);
    }

    public void dislikeArticle(Integer articleId, Integer num) {
        this.sessionRepository.dislikeArticle(articleId, num);
    }

    public void likeComment(Integer commentId, Integer num) {
        this.sessionRepository.likeComment(commentId, num);
    }

    public void dislikeComment(Integer commentId, Integer num) {
        this.sessionRepository.dislikeComment(commentId, num);
    }

    public List<String> stringToArray(String stringList){
        String[] str = stringList.split(",");
        for(int i = 0 ; i < str.length; i++){
            //System.out.println(str[i]);
        }
        return Arrays.asList(str);
    }
}