package com.example.demo.resources;

import com.example.demo.services.TagService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @GET
    @Path("/{tag}/articles/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count(@PathParam("tag") String tag) {
        return Response.ok(this.tagService.articlesByTagCount(tag)).build();
    }

    @GET
    @Path("/{tag}/articles/page/{pageNum}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@PathParam("tag") String tag, @PathParam("pageNum") Integer pageNum) {
        return Response.ok(this.tagService.articlesByTag(tag, pageNum)).build();
    }

}
