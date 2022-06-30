package com.api.twstock.model.security;

import com.api.twstock.model.entity.StockWatchlist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column(name="user_line_id")
    private String userLineId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<StockWatchlist> watchList = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Roles> roles = new ArrayList<>();

    public User(){};

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
