package com.darkdevil.security.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    private String username;
    private String password;
    private int enabled;
}
