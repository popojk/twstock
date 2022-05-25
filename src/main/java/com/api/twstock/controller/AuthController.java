package com.api.twstock.controller;

import com.api.twstock.model.DTO.CreateUserDto;
import com.api.twstock.model.security.AuthRequest;
import com.api.twstock.model.security.User;
import com.api.twstock.service.JwtUserDetailsServiceImpl;
import com.api.twstock.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @PostMapping("/create")
    public User createUser(@RequestBody CreateUserDto createUserDto) {
        return jwtUserDetailsService.createUser(createUserDto);
    }

    //

    @PostMapping("/issue")
    public ResponseEntity<Map<String, String>> issueToken(@RequestBody AuthRequest request){

        String token = jwtTokenUtils.generateToken(request);
        Map<String, String> response = Collections.singletonMap("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/parse")
    public ResponseEntity<String> parseToken(@RequestBody Map<String, String> request){
        String token = request.get("token");
        String response = jwtTokenUtils.getUserNameFromToken(token);

        return ResponseEntity.ok(response);
    }
}
