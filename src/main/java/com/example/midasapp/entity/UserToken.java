package com.example.midasapp.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserToken {

    private String authToken;

    private Timestamp createdTimeStamp;

    private Timestamp expirationTimeStamp;

}
