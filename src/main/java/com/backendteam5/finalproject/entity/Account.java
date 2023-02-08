package com.backendteam5.finalproject.entity;

import com.backendteam5.finalproject.dto.SignupRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "area_role", columnList = "area, role")})
public class Account {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 50)
    private String area;

    @Column(nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public Account(String username, String password, String area, UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.area = area;
        this.role = role;
    }
    public Account(SignupRequestDto signupRequestDto, UserRoleEnum role) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.area = signupRequestDto.getArea();
        this.role = role;
    }
}
