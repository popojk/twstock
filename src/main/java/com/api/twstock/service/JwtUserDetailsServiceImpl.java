package com.api.twstock.service;

import com.api.twstock.exception.ApiException;
import com.api.twstock.factory.JwtUserFactory;
import com.api.twstock.model.DTO.CreateUserDto;
import com.api.twstock.model.security.User;
import com.api.twstock.repo.RoleRepo;
import com.api.twstock.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException(String.format("No user found with username ''%s", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }

    public User createUser(CreateUserDto createUserDto) {
        if(userRepo.findByUsername(createUserDto.getUsername()) != null){
            throw new ApiException("Username "+ createUserDto.getUsername() + " already exists!");
        }

        if(userRepo.findByEmail(createUserDto.getEmail()) != null){
            throw new ApiException("Email "+ createUserDto.getEmail() + " already exists!");
        }

        User tempUser = new User(createUserDto.getUsername(),
                passwordEncoder.encode(createUserDto.getPassword()),
                createUserDto.getEmail());
        tempUser.getRoles().add(roleRepo.getById(2));

        Date currentTime = new Date();
        Timestamp timestamp = new Timestamp(currentTime.getTime());
        tempUser.setLastPasswordResetDate(timestamp);

        userRepo.save(tempUser);
        return tempUser;
    }

}
