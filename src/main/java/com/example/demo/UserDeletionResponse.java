package com.example.demo;

public class UserDeletionResponse {

    private String username;
    private String status;

    public UserDeletionResponse(Person person) {
        this.username = person.getUsername();
        this.status = "Deleted successfully!";
    }

    public String getUsername() {
        return this.username;
    }

    public String getStatus() {
        return this.status;
    }
}
