package com.example.demo.resources;

import com.example.demo.entities.Category;
import com.example.demo.entities.UserType;
import com.example.demo.filters.Secured;
import com.example.demo.services.CategoryService;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/category")
public class CategoryResource {
    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.categoryService.allCategories()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({UserType.ADMIN,UserType.CONTENT_CREATOR})
    public Category create(@Valid Category category) {
        if(this.categoryService.findCategory(category.getLabel()) != null){
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).
                    entity("Category with this name already exists.").build());
        }
        return this.categoryService.addCategory(category);
    }

    @GET
    @Path("/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category find(@PathParam("category") String category) {
        return this.categoryService.findCategory(category);
    }

    @DELETE
    @Path("/{category}")
    @Secured({UserType.ADMIN,UserType.CONTENT_CREATOR})
    public void delete(@PathParam("category") String category) {
        if(this.categoryService.articlesCountByCategory(category) > 0) {
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).
                    entity("Category with active articles cannot be deleted.").build());
        } else {
            this.categoryService.deleteCategory(category);
        }
    }

    @PUT
    @Path("/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({UserType.ADMIN,UserType.CONTENT_CREATOR})
    public Category modify(@Valid Category category, @PathParam("category") String categoryName) {
        if(!category.getLabel().equals(categoryName) && this.categoryService.findCategory(category.getLabel()) != null){
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).
                    entity("Category with this label already exists.").build());
        }
        return this.categoryService.modifyCategory(category, categoryName);
    }

    @GET
    @Path("/{category}/articles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticlesByCategory(@PathParam("category") String category) {
        return Response.ok(this.categoryService.findArticlesByCategory(category)).build();
    }

    @GET
    @Path("/{category}/articles/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticlesByCategoryOnPage(@PathParam("category") String category, @PathParam("pageNum") Integer pageNum) {
        return Response.ok(this.categoryService.findArticlesByCategoryOnPage(category, pageNum)).build();
    }


    @GET
    @Path("page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allOnPage(@PathParam("pageNum") Integer pageNum) {
        return Response.ok(this.categoryService.allCategoriesByPage(pageNum)).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count() {
        return Response.ok(this.categoryService.categoryCount()).build();
    }

    @GET
    @Path("/{category}/articles/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countArticles(@PathParam("category") String category) {
        return Response.ok(this.categoryService.articlesCountByCategory(category)).build();
    }

    @GET
    @Path("/{category}/articles/search/{string}/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("pageNum") Integer pageNum, @PathParam("category") String category, @PathParam("string") String string) {
        return Response.ok(this.categoryService.searchArticlesByCategory(pageNum, category, string)).build();
    }

    @GET
    @Path("/{category}/articles/search/{string}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countSearch(@PathParam("category") String category, @PathParam("string") String string) {
        return Response.ok(this.categoryService.articlesCountByCategorySearch(category, string)).build();
    }

}
