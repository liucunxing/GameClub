package com.base.utils.common;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
@Component
public class JwtUtils {
    private static String sign = "356yrhtgbwq2";
    private static long time = 3600000;

    public static String createJwt(Map<String,String> claims){
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setSubject("userToken")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString());
        claims.forEach(builder::claim);
        return builder.signWith(SignatureAlgorithm.HS256,sign)
                .compact();
    }
    public static Claims parseJwt(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(sign).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims;
    }
    public static Claims parseJwtToClaims(String token){

        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(sign).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims;
    }
    public static Boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null){
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(sign).parseClaimsJws(token);
        }
        catch (Exception e){
            return false;
        }

        return true;
    }
}
