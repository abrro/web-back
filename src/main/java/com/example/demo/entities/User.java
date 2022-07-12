package com.example.demo.entities;

import com.example.demo.filters.UserTypeAllowed;
import javax.validation.constraints.*;

public class User {

    @Email(message = "Incorrect mail format")
    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String email;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String name;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String lastname;

    @UserTypeAllowed(anyOf = {UserType.ADMIN, UserType.CONTENT_CREATOR})
    private UserType type;

    @Min(value = 0, message = "Status can be either 0 (inactive) or 1 (active)")
    @Max(value = 1, message = "Status can be either 0 (inactive) or 1 (active)")
    private Integer status;

    @NotNull(message = "Field required")
    @NotEmpty(message = "Field required")
    @NotBlank(message = "Field cannot be whitespace")
    private String passHash;

    public User() {
    }

    public User(String email, String name, String lastname, UserType type, Integer status, String passHash) {
        this();
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.type = type;
        this.status = status;
        this.passHash = passHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }
}
