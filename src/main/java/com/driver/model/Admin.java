package com.driver.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "admin")
@Entity
@Data
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int adminId;

    String username;
    String password;
}