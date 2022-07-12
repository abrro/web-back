package com.example.demo.filters;

import com.example.demo.entities.UserType;
import com.example.demo.services.AuthService;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private AuthService authService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        //vadjenje anotcaije iz metoda resursa ili klase resursa
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<UserType> classRoles = getRoles(resourceClass);

        Method resourceMethod = resourceInfo.getResourceMethod();
        List<UserType> methodRoles = getRoles(resourceMethod);

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            String role = this.authService.getRole(token);
            System.out.println("Logged user is in role: " + role);
            //System.out.println(classRoles);
            System.out.println(methodRoles);

            //anotacije metode overrajduju anotacije klase, pa se prvo one moraju proveriti
            if (methodRoles.isEmpty()) {
                if(!compareRoles(classRoles, role)){
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }else{
                    return;
                }
            } else {
                if(!compareRoles(methodRoles, role)){
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }else{
                    return;
                }
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
    //citanje uloga iz anotacije
    private List<UserType> getRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<UserType>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<UserType>();
            } else {
                UserType[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private boolean compareRoles(List<UserType> allowedRoles, String role) throws Exception {
        if(allowedRoles.contains(UserType.valueOf(role))){
            System.out.println(role + " has permission");
            return true;
        }else
            return false;
    }
}
