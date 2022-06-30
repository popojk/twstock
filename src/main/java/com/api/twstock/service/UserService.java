package com.api.twstock.service;

import com.api.twstock.model.security.User;
import com.api.twstock.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public User getUserByUsername(String username){

        return userRepo.findByUsername(username);
    }

    public User getUserByLineId(String userLineId){
        return userRepo.findByUserLineId(userLineId);
    }
}
