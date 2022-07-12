package com.example.demo.resources;

import com.example.demo.entities.User;
import com.example.demo.entities.UserType;
import com.example.demo.filters.Secured;
import com.example.demo.services.UserService;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Secured({UserType.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public User create(@Valid User user) {
        if(this.userService.findUser(user.getEmail()) != null){
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).
                    entity("User with this email already exists.").build());
        }
        return this.userService.addUser(user);
    }

    @GET
    @Secured({UserType.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.userService.allUsers()).build();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("username") String username) {
        return this.userService.findUser(username);
    }

    @PUT
    @Path("/{username}")
    @Secured({UserType.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public User modify(@Valid User user, @PathParam("username") String username) {
        if(!user.getEmail().equals(username) && this.userService.findUser(user.getEmail()) != null){
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).
                    entity("User with this email already exists.").build());
        }
        return this.userService.modifyUser(user, username);
    }

    @PUT
    @Path("/toggle/{username}")
    @Secured({UserType.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public void toggle(@PathParam("username") String username) {
        User user = this.userService.findUser(username);

        if(user.getType().toString().equals("ADMIN")){
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).entity("This user has administrative privileges and his status cannot be edited.").build());
        }else {
            if (user.getStatus() == 0) {
                this.userService.toggleStatus(username, 1);
            }
            if (user.getStatus() == 1) {
                this.userService.toggleStatus(username, 0);
            }
        }
    }

    @GET
    @Path("/page/{pageNum}")
    @Secured({UserType.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response allOnPage(@PathParam("pageNum") Integer pageNum) {
        return Response.ok(this.userService.allUsersOnPage(pageNum)).build();
    }

    @GET
    @Path("/count")
    @Secured({UserType.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response count() {
        return Response.ok(this.userService.userCount()).build();
    }

}
