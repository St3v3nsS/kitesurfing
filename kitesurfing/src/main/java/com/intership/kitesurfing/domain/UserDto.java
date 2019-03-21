package com.intership.kitesurfing.domain;

import com.intership.kitesurfing.domain.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Document(collection = "users")
public class UserDto {

    @Id
    private String id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;
    private boolean enabled;
    @DBRef
    private Set<Role> roles;
    @DBRef
    private Set<Location> favorites;

    public UserDto(String id,
                   @NotNull @NotEmpty String username,
                   @NotNull @NotEmpty String password,
                   @NotNull @NotEmpty String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public Set<Location> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Location> favorites) {
        this.favorites = favorites;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return username + " " + password + " " + email;
    }
}
