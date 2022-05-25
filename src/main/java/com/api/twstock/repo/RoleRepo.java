package com.api.twstock.repo;

import com.api.twstock.model.security.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles, Integer> {

}
