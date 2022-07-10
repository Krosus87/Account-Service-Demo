package com.example.demo;

public class UserRegistrationResponse {

    private long id;
    private String name;
    private String username;

    private String role;

    public UserRegistrationResponse(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.username = person.getUsername();
        this.role = person.getRole();
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return this.role;
    }
}
