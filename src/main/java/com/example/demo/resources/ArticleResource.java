package com.example.demo.resources;

import com.example.demo.entities.Article;
import com.example.demo.entities.UserType;
import com.example.demo.filters.Secured;
import com.example.demo.services.ArticleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/articles")
public class ArticleResource {
    @Inject
    private ArticleService articleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.articleService.allArticles()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({UserType.ADMIN,UserType.CONTENT_CREATOR})
    public Article create(@Valid Article article) {
        return this.articleService.addArticle(article);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Article find(@PathParam("id") Integer id) {
        Article article = this.articleService.findArticle(id);
        if(article == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return article;
    }

    @DELETE
    @Path("/{id}")
    @Secured({UserType.ADMIN,UserType.CONTENT_CREATOR})
    public void delete(@PathParam("id") Integer id) {
        this.articleService.deleteArticle(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({UserType.ADMIN,UserType.CONTENT_CREATOR})
    public Article modify(@Valid Article article, @PathParam("id") Integer id) {
        return this.articleService.modifyArticle(article, id);
    }

    @GET
    @Path("/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allOnPage(@PathParam("pageNum") Integer pageNum) {
        return Response.ok(this.articleService.allArticlesOnPage(pageNum)).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count() {
        return Response.ok(this.articleService.articleCount()).build();
    }

    @GET
    @Path("/search/{string}/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchArticles(@PathParam("pageNum") Integer pageNum, @PathParam("string") String string) {
        return Response.ok(this.articleService.searchArticle(string, pageNum)).build();
    }

    @GET
    @Path("/search/{string}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countSearch(@PathParam("string") String string) {
        return Response.ok(this.articleService.articlesCountSearch(string)).build();
    }

    @GET
    @Path("/newest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newest() {
        return Response.ok(this.articleService.newestArticles()).build();
    }

    @GET
    @Path("/top")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostVisited() {
        return Response.ok(this.articleService.mostVisitedArticles()).build();
    }

    @GET
    @Path("/hot")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostReactions() {
        return Response.ok(this.articleService.mostReactionsArticles()).build();
    }

}
