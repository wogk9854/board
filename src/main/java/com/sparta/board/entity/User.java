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


    //성현 role 살림
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) //열거??, EnumType에는 총 두 가지 타입이 있다., EnumType.STRING : 각 Enum 이름을 컬럼에 저장한다. ex) G, PG, PG13.., EnumType.ORDINAL : 각 Enum에 대응되는 순서를 칼럼에 저장한다. ex) 0, 1, 2..
    private UserRoleEnum role;

//    @OneToMany
//    private List<User> users = new ArrayList<>();



    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = role; //성현 role 살림
    }
}
