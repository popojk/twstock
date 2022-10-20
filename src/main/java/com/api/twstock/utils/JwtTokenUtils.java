package com.api.twstock.utils;

import com.api.twstock.exception.ApiException;
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
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    String secret;

    public Map<String, String> generateToken(AuthRequest request){
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //generate access token
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 6);
        Claims claims = Jwts.claims();
        claims.put("sub", userDetails.getUsername());
        claims.setExpiration(calendar.getTime());
        claims.setIssuer("TW stock");
        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        String accessToken = Jwts.builder().setClaims(claims).signWith(secretKey).compact();

        //generate refresh token
        calendar.add(Calendar.MONTH, 12);
        Claims refreshTokenClaims = Jwts.claims();
        refreshTokenClaims.put("sub", userDetails.getUsername());
        refreshTokenClaims.setExpiration(calendar.getTime());
        refreshTokenClaims.setIssuer("TW stock");
        String refreshToken = Jwts.builder().setClaims(refreshTokenClaims).signWith(secretKey).compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

    //使用username產生access token
    public String generateAccessTokenByUsername(String username){
        //generate access token
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 6);
        Claims claims = Jwts.claims();
        claims.put("sub", username);
        claims.setExpiration(calendar.getTime());
        claims.setIssuer("TW stock");
        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }

    //取得JWT中的值
    private Claims getClaimsFromToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        Claims claims = null;

        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            claims = parser.parseClaimsJws(token).getBody();
            LOGGER.info(token);
            LOGGER.info("驗證成功");

        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}", token);
        }

        return claims;
    }

    //從JWT中獲得用戶名
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

    //確認token效期
    public String verifyExpiration(String refreshToken) throws ApiException {
         Claims claims = getClaimsFromToken(refreshToken);
         Date expriation = claims.getExpiration();
         if(expriation.before(new Date(System.currentTimeMillis()))){
             throw new ApiException("Refresh token was expired");
         }
         return refreshToken;
    }

}
