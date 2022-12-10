package com.sparta.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

//    @OneToMany
//    private List<User> users = new ArrayList<>();



    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
