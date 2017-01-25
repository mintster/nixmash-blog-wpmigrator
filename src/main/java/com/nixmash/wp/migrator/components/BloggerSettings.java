package com.nixmash.wp.migrator.components;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class BloggerSettings  implements Serializable{

    private static final long serialVersionUID = 7095502313114685518L;

    public BloggerSettings() {
    }

    @Value("${blogger.username}")
    private String username = "";

    @Value("${blogger.email}")
    private String email = "";

    @Value("${blogger.firstname}")
    private String firstName = "";

    @Value("${blogger.lastname}")
    private String lastName = "";

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "LocalUserDTO{" +
                ", username=" + username  +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                '}';
    }
}
