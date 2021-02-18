package com.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

public class JwtUtils {
    private static final String JWT_PAYLOAD_USER_KEY = "user";

    //私钥加密token
    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire){
        return Jwts.builder()
                    .claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toString(userInfo))
                    .setId(createJTI())
                    .setExpiration(DateTime.now().plusMinutes(expire).toDate())
                    .signWith(privateKey, SignatureAlgorithm.RS256)
                    .compact();
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    //公钥解析token
    private static Jws<Claims> parseToken(String token, PublicKey publicKey){
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    //获取token中的用户信息
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType){
        Jws<Claims> claimsJws = parseToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JsonUtils.toBean(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    //获取token中的用户信息
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey){
        Jws<Claims> claimsJws = parseToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
}
