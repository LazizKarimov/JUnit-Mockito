package edu.school21.models;

public class User {

    private Long id;

    private String login;

    private String password;

    boolean isAuthenticated;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getPassword() {
        return password;
    }
}
