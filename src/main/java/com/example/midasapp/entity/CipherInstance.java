package com.example.midasapp.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;

@Data
public class CipherInstance {

    private final Cipher cipher;

    public CipherInstance(Cipher cipher) {
        this.cipher = cipher;
    }
}
