package com.api.twstock.factory;

import com.api.twstock.model.security.JwtUser;
import com.api.twstock.model.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory(){

    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getRoles().stream().map(role -> role.getName())
                        .collect(Collectors.toList())),
                user.getLastPasswordResetDate()
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities){
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
