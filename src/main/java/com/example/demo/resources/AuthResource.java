package com.example.demo.resources;

import com.example.demo.repositories.user.UserRepository;
import com.example.demo.requests.LoginRequest;
import com.example.demo.services.AuthService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/authenticate")
public class AuthResource {

    @Inject
    private AuthService authService;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (jwt == "wrongCredentials") {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }else if(jwt == "wrongStatus"){
            response.put("message", "Access denied because of account's status");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }
}
