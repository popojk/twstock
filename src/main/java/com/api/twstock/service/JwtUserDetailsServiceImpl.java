package com.api.twstock.service;

import com.api.twstock.exception.ApiException;
import com.api.twstock.factory.JwtUserFactory;
import com.api.twstock.model.DTO.CreateUserDto;
import com.api.twstock.model.security.User;
import com.api.twstock.repo.RoleRepo;
import com.api.twstock.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    UserRepo userRepo;
    RoleRepo roleRepo;
    BCryptPasswordEncoder passwordEncoder;

    public JwtUserDetailsServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(String.format("No user found with username ''%s", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }

    //get user data for login
    public User getUserData(String username, String password){
        User user = userRepo.findByUsername(username);
        return user;
    }

    //create user
    public User createUser(CreateUserDto createUserDto) {
        User tempUser = new User(createUserDto.getUsername(),
        passwordEncoder.encode(createUserDto.getPassword()), createUserDto.getEmail());
        tempUser.getRoles().add(roleRepo.getById(2));
        Date currentTime = new Date();
        Timestamp timestamp = new Timestamp(currentTime.getTime());
        tempUser.setLastPasswordResetDate(timestamp);
        userRepo.save(tempUser);
        return tempUser;
    }

    //將line id綁定帳號
    public Boolean addUserLineIdToAccount(String username, String userLineId) throws UsernameNotFoundException{
            //find user from user repo
            User tempUser = userRepo.findByUsername(username);
            if(tempUser == null){
                throw new UsernameNotFoundException("username do not exist");
            }
            //check if userlineid already exists
            if(tempUser.getUserLineId() != null){
                return false;
            } else {
                //add user line id to account
                tempUser.setUserLineId(userLineId);
                userRepo.save(tempUser);
                return true;
            }
    }

}
