package com.example.jwtapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Collection;


@Entity
@Table (name = "usr")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String username;
    private String password;

    @ManyToMany
    private Collection<Role> roles;

    @ManyToMany
    private Collection<Message> messages;

    public AppUser(String username, String password, Collection<Role> roles, Collection<Message> messages) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.messages = messages;
    }
}

