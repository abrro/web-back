package com.example.demo;

import com.example.demo.repositories.article.ArticleRepository;
import com.example.demo.repositories.article.MySqlArticleRepository;
import com.example.demo.repositories.category.CategoryRepository;
import com.example.demo.repositories.category.MySqlCategoryRepository;
import com.example.demo.repositories.comment.CommentRepository;
import com.example.demo.repositories.comment.MySqlCommentRepository;
import com.example.demo.repositories.session.MySqlSessionRepository;
import com.example.demo.repositories.session.SessionRepository;
import com.example.demo.repositories.tag.MySqlTagRepository;
import com.example.demo.repositories.tag.TagRepository;
import com.example.demo.repositories.user.MySqlUserRepository;
import com.example.demo.repositories.user.UserRepository;
import com.example.demo.services.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlArticleRepository.class).to(ArticleRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlSessionRepository.class).to(SessionRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);


                this.bindAsContract(ArticleService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(AuthService.class);
                this.bindAsContract(SessionService.class);
            }
        };
        register(binder);

        packages("com.example.demo");
    }
}
