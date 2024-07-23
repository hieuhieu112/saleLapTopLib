package com.example.TTTotNghiep.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTProvider {
    private SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());

    public String generateToken (Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthen(authorities);
        System.out.println("Role: " + roles);

        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();


        return jwt;
    }
    public String getEmailFromJWT(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));

        return  email;

    }

//    private String populateAuthen(Collection<? extends GrantedAuthority> authorities) {
//        Set<String> auths = new HashSet<>();
//
//        for(GrantedAuthority auth:authorities){
//            auths.add(auth.getAuthority());
//        }
//
//        return null;
//    }

    private String populateAuthen(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority auth : authorities) {
            auths.add(auth.getAuthority());
        }

        return String.join(",", auths);
    }

}
