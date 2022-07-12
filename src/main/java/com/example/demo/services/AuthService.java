package com.example.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.entities.User;
import com.example.demo.entities.UserType;
import com.example.demo.repositories.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.Date;

public class AuthService {
    public AuthService() {
        System.out.println(this);
    }

    @Inject
    private UserRepository userRepository;

    public String login(String usermail, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(usermail);
        if (user == null || !user.getPassHash().equals(hashedPassword)) {
            return "wrongCredentials";
        }else if( user.getStatus() != 1){
            return "wrongStatus";
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 365*24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(usermail)
                .withClaim("role", user.getType().toString())
                .sign(algorithm);
    }

    public boolean isAuthenticated(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();

        User user = this.userRepository.findUser(username);

        if (user == null){
            return false;
        }

        return true;
    }

    public String getRole(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();
        String role = jwt.getClaim("role").asString();

        User user = this.userRepository.findUser(username);

        if (user == null){
            return null;
        }

        return role;
    }
}
