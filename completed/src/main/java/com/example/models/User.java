package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
public class User {
    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    @Size(min = 3, max = 100, message = "Username must at least 3 characters.")
    private String userName;

    @NotNull
    @Size(min = 3, max = 100, message = "Password must at least 3 characters.")
    private String password;

    @Transient
    private String confirmPassword;

    @Email(message = "Email address is not valid.")
    @NotNull
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean isMatchingPasswords() {
        return this.password.equals(this.confirmPassword);
    }
}
