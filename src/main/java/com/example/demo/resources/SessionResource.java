package com.example.demo.resources;

import com.example.demo.services.SessionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/session")
public class SessionResource {

    @Inject
    private SessionService sessionService;

    @POST
    @Path("/visit/article/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response visitArticle(Map<String,String> artViews, @PathParam("id") Integer id){
        Map<String, String> response = new HashMap<>();

        System.out.println(artViews.get("artViews"));

        if(!this.sessionService.stringToArray(artViews.get("artViews")).contains(id.toString())){
            this.sessionService.visitArticle(id);
            response.put("message", "Visited article: " + id);
            return Response.ok().entity(response).build();
        }
        response.put("message", "Already visited article: " + id);
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }

    @POST
    @Path("/like/article/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeArticle(Map<String,String> storageEntries,
                                @PathParam("id") Integer id){
        Map<String, String> response = new HashMap<>();

        //ako je lajkovan vec, kad ponovo klikne treba da se smanji broj lajkova
        if(this.sessionService.stringToArray(storageEntries.get("artLikes")).contains(id.toString())){
            this.sessionService.likeArticle(id, -1);
            response.put("message", "Already liked this article, removing the like.");
            return Response.ok().entity(response).build();
        }
        //ako je dislajkovan prethodno
        if(this.sessionService.stringToArray(storageEntries.get("artDislikes")).contains(id.toString())){
            this.sessionService.dislikeArticle(id, -1);
        }
        //lajkuje
        this.sessionService.likeArticle(id, 1);
        response.put("message", "Liked article: " + id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("/dislike/article/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeArticle(Map<String,String> storageEntries,
                                   @PathParam("id") Integer id){
        Map<String, String> response = new HashMap<>();

        if(this.sessionService.stringToArray(storageEntries.get("artDislikes")).contains(id.toString())){
            this.sessionService.dislikeArticle(id, -1);
            response.put("message", "Already disliked this article, removing the dislike.");
            return Response.ok().entity(response).build();
        }
        if(this.sessionService.stringToArray(storageEntries.get("artLikes")).contains(id.toString())){
            this.sessionService.likeArticle(id, -1);
        }
        this.sessionService.dislikeArticle(id, 1);
        response.put("message", "Disliked article: " + id);
        return Response.ok().entity(response).build();

    }

    @POST
    @Path("/like/comment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  likeComment(Map<String,String> storageEntries,
                                 @PathParam("id") Integer id){
        Map<String, String> response = new HashMap<>();

        if(this.sessionService.stringToArray(storageEntries.get("commLikes")).contains(id.toString())){
            this.sessionService.likeComment(id, -1);
            response.put("message", "Already liked this comment, removing the like.");
            return Response.ok().entity(response).build();
        }
        if(this.sessionService.stringToArray(storageEntries.get("commDislikes")).contains(id.toString())){
            this.sessionService.dislikeComment(id, -1);
        }
        this.sessionService.likeComment(id, 1);
        response.put("message", "Liked comment: " + id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("/dislike/comment/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeComment(Map<String,String> storageEntries,
                                   @PathParam("id") Integer id){
        Map<String, String> response = new HashMap<>();

        if(this.sessionService.stringToArray(storageEntries.get("commDislikes")).contains(id.toString())){
            this.sessionService.dislikeComment(id, -1);
            response.put("message", "Already disliked this comment, removing the dislike.");
            return Response.ok().entity(response).build();
        }
        if(this.sessionService.stringToArray(storageEntries.get("commLikes")).contains(id.toString())){
            this.sessionService.likeComment(id, -1);
        }
        this.sessionService.dislikeComment(id, 1);
        response.put("message", "Disliked comment: " + id);
        return Response.ok().entity(response).build();
    }

}
