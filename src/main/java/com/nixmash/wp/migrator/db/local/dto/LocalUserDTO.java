package com.nixmash.wp.migrator.db.local.dto;

public class LocalUserDTO {

    public LocalUserDTO() {
    }

    private Long userId;
    private String username = "";
    private String email = "";
    private String password = "";
    private String firstName = "";
    private String lastName = "";
    private String userKey;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public String getUserKey() {
        return userKey;
    }
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public static Builder getBuilder(Long userId, String username, String email, String firstName, String lastName) {
        return new Builder(userId, username, email, firstName, lastName);
    }

    public static class Builder {

        private LocalUserDTO built;

        public Builder(Long userId, String username, String email, String firstName, String lastName) {
            built = new LocalUserDTO();
            built.userId = userId;
            built.username = username;
            built.email = email;
            built.firstName = firstName;
            built.lastName = lastName;
        }

        public LocalUserDTO build() {
            return built;
        }
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
