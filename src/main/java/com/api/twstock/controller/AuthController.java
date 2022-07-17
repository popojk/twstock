package com.api.twstock.controller;

import com.api.twstock.model.DTO.CreateUserDto;
import com.api.twstock.model.security.AuthRequest;
import com.api.twstock.model.security.User;
import com.api.twstock.service.JwtUserDetailsServiceImpl;
import com.api.twstock.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AuthController {

    @Autowired
    JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody CreateUserDto createUserDto) {
        User user = jwtUserDetailsService.createUser(createUserDto);
        if(user != null){
            return login(new AuthRequest(createUserDto.getUsername(), createUserDto.getPassword()));
        }
     return ResponseEntity.status(404).build();
    }

    //To do - user login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest request){
        if(jwtUserDetailsService.getUserData(request.getUsername(), request.getPassword()) != null){
            Map<String, String> tokens = jwtTokenUtils.generateToken(request);
            String userLineId = jwtUserDetailsService.getUserData(request.getUsername(), request.getPassword()).getUserLineId();
            Map<String, Object> respResult= new LinkedHashMap<>();
            respResult.put("username", request.getUsername());
            respResult.put("access_token", tokens.get("access_token"));
            respResult.put("refresh_token", tokens.get("refresh_token"));
            respResult.put("userlineid", userLineId);
            return ResponseEntity.ok(respResult);
        }
        return ResponseEntity.status(403).body("Wrong user name or password");
    }

    @PostMapping("/token/refresh")
    public ResponseEntity refreshToken(ServletRequest request){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String refreshToken = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        if(jwtTokenUtils.verifyExpriation(refreshToken) != null){
            Map<String, Object> respResult= new LinkedHashMap<>();
            String username = jwtTokenUtils.getUserNameFromToken(refreshToken);
            String accessToken = jwtTokenUtils.generateAccessTokenByUsername(username);
            respResult.put("access_token", accessToken);
            respResult.put("refresh_token", refreshToken);
            return ResponseEntity.ok(respResult);
        }
        return ResponseEntity.status(403).body("refresh token was expired");
    }

    //for testing purpose
    @PostMapping("/issue")
    public ResponseEntity<Map<String, String>> issueToken(@RequestBody AuthRequest request){

        return ResponseEntity.ok(jwtTokenUtils.generateToken(request));
    }

    @PostMapping("/parse")
    public ResponseEntity<String> parseToken(@RequestBody Map<String, String> request){
        String token = request.get("token");
        String response = jwtTokenUtils.getUserNameFromToken(token);

        return ResponseEntity.ok(response);
    }
}
