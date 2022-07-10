package com.example.demo;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String username;
    private String password;
    private String role;

    private boolean accountNonLocked;

    public Person() {
        this.role = "MERCHANT";
        this.accountNonLocked = false;
    }

    public Person(String name, String username, String password) {
        this();
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public void setAccountNonLocked(boolean accountLock) {
        this.accountNonLocked = accountLock;
    }

    public boolean isAccountNonLocked(){
        return this.accountNonLocked;
    }

}
