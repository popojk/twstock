package com.api.twstock.filter;

import com.api.twstock.service.JwtUserDetailsServiceImpl;
import com.api.twstock.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUserDetailsServiceImpl jwtUserDetailsServiceimpl;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Value("${jwt.header}")
    String tokenHeader;

    @Value("${jwt.tokenHead}")
    String tokenHead;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        //取得請求頭
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader != null) {
            //取得JWT並解析出帳號
            String accessToken = authHeader.replace("Bearer ", "");
            String username = jwtTokenUtils.getUserNameFromToken(accessToken);

            if(username != null) {
                UserDetails userDetails = jwtUserDetailsServiceimpl.loadUserByUsername(username);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
