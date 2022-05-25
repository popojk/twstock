package com.api.twstock.utils;

import com.api.twstock.model.security.AuthRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Calendar;

public class JwtTokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    String secret;

    public String generateToken(AuthRequest request){
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 10);

        Claims claims = Jwts.claims();
        claims.put("sub", userDetails.getUsername());
        claims.setExpiration(calendar.getTime());
        claims.setIssuer("TW stock");

        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = null;

        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            claims = parser.parseClaimsJws(token).getBody();

        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}", token);
        }

        return claims;
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

}
