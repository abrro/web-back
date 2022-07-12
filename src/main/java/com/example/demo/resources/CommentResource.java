package com.example.demo.resources;

import com.example.demo.entities.Comment;
import com.example.demo.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/articles/{id}/comments")
public class CommentResource {
    @Inject
    private CommentService commentService;
    @PathParam("id")
    private Integer id;

    @GET
    @Path("/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@PathParam("pageNum") Integer pageNum) {
        return Response.ok(this.commentService.allComments(id, pageNum)).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count() {
        return Response.ok(this.commentService.count(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment) {
        return this.commentService.addComment(comment, id);
    }

}
