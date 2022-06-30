package com.api.twstock.repo;

import com.api.twstock.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String Email);

    User findByUserLineId(String userLineId);
}
