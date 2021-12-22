package com.sparta.musicstoreapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
public class Token
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TokenID", nullable = false)
    private Integer id;

    @Column(name = "Token", nullable = false, length = 160)
    private String token;

    @Column(name = "Email", nullable = false, length = 35)
    private String email;

    @Column(name = "PermissionLevel", nullable = false)
    private Integer permissionLevel;

    public Token(String token, String email, Integer permissionLevel)
    {
        this.token = token;
        this.email = email;
        this.permissionLevel = permissionLevel;
    }

    public Token() {

    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}