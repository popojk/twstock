package com.api.twstock.controller;

import com.api.twstock.exception.ApiException;
import com.api.twstock.model.DTO.CreateUserDto;
import com.api.twstock.model.security.AuthRequest;
import com.api.twstock.model.security.User;
import com.api.twstock.repo.UserRepo;
import com.api.twstock.service.JwtUserDetailsServiceImpl;
import com.api.twstock.utils.JwtTokenUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    JwtUserDetailsServiceImpl jwtUserDetailsService;
    JwtTokenUtils jwtTokenUtils;
    BCryptPasswordEncoder passwordEncoder;
    UserRepo userRepo;

    public AuthController(JwtUserDetailsServiceImpl jwtUserDetailsService, JwtTokenUtils jwtTokenUtils, BCryptPasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @PostMapping("/create")
    @ApiOperation(value="建立使用者帳號")
    public ResponseEntity createUser(@RequestBody CreateUserDto createUserDto) {

        //確認帳號與Email尚未被註冊
        if(userRepo.findByUsername(createUserDto.getUsername()) != null){
            return ResponseEntity.status(401).body("帳號已存在");
        }
        if(userRepo.findByEmail(createUserDto.getEmail()) != null){
            return ResponseEntity.status(401).body("Email已被註冊");
        }

        //建立帳號並登入
        User user = jwtUserDetailsService.createUser(createUserDto);
        if(user != null){
            //如建立帳號成功即登入
            return login(new AuthRequest(createUserDto.getUsername(), createUserDto.getPassword()));
        }
     return ResponseEntity.status(404).build();
    }

    //使用者登入
    @PostMapping("/login")
    @ApiOperation(value="使用者登入")
    public ResponseEntity login(@RequestBody AuthRequest request){
        User tempUser = jwtUserDetailsService.getUserData(request.getUsername(), request.getPassword());
        if(tempUser != null){
            //確認密碼是否正確
            if(!passwordEncoder.matches(request.getPassword(), tempUser.getPassword())){
                return ResponseEntity.status(403).body("帳號或密碼錯誤");
            }
            //產生JWT
            Map<String, String> tokens = jwtTokenUtils.generateToken(request);
            String userLineId = tempUser.getUserLineId();
            //將結果寫入response body回傳前端
            Map<String, Object> respResult= new LinkedHashMap<>();
            respResult.put("username", request.getUsername());
            respResult.put("password", request.getPassword());
            respResult.put("access_token", tokens.get("access_token"));
            respResult.put("refresh_token", tokens.get("refresh_token"));
            respResult.put("userlineid", userLineId);
            return ResponseEntity.ok(respResult);
        }
        return ResponseEntity.status(403).body("帳號或密碼錯誤");
    }

    @PostMapping("/token/refresh")
    @ApiOperation(value="更新JWT")
    public ResponseEntity refreshToken(ServletRequest request){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String refreshToken = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        //確認refresh token尚未過期
        if(jwtTokenUtils.verifyExpiration(refreshToken) != null){
            Map<String, Object> respResult= new LinkedHashMap<>();
            //取得帳號
            String username = jwtTokenUtils.getUserNameFromToken(refreshToken);
            //使用帳號取得新JWT
            String accessToken = jwtTokenUtils.generateAccessTokenByUsername(username);
            //將結果回傳前端
            respResult.put("access_token", accessToken);
            respResult.put("refresh_token", refreshToken);
            return ResponseEntity.ok(respResult);
        }
        return ResponseEntity.status(403).body("refresh token was expired");
    }

    //for testing purpose
    @PostMapping("/issue")
    @ApiOperation(value="取得JWT")
    public ResponseEntity<Map<String, String>> issueToken(@RequestBody AuthRequest request){
        return ResponseEntity.ok(jwtTokenUtils.generateToken(request));
    }

    //for testing purpose
    @PostMapping("/parse")
    @ApiOperation(value="驗證JWT")
    public ResponseEntity<String> parseToken(@RequestBody Map<String, String> request){
        String token = request.get("token");
        String response = jwtTokenUtils.getUserNameFromToken(token);
        return ResponseEntity.ok(response);
    }
}
