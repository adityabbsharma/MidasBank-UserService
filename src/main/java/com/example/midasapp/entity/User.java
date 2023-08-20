package com.example.midasapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "User")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @JsonIgnore
    public Long id;

    // TODO add regex here to perform serverside validations for username
    @Column(unique = true)
    @JsonProperty(value = "userName")
    public String userName;

    // TODO add regex for password for serverside validations
    @Column
    @JsonProperty(value = "password")
    public String password;

    @Column
    @JsonIgnore
    private String passwordSalt;



}
