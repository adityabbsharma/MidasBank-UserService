package com.example.midasapp.service;

import com.example.midasapp.entity.CipherInstance;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

@Service
public class PasswordCryptographyService {
//    private final Cipher cipher = Cipher.getInstance("AES")  ;

    private static final String PASSWORD_HASHING_SECRET_KEY_ALOGORITHM = "PBKDF2WithHmacSHA1";
    private final CipherInstance cipherInstance;

    private Cipher cipher;

    public PasswordCryptographyService() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipherInstance = new CipherInstance(Cipher.getInstance("AES/CBC/PKCS5Padding"));
    }

    @PostConstruct
    public void constructCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        synchronized (Cipher.class)
        {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        }
    }


    private byte[] generatePasswordSalt() {
        final Random r = new SecureRandom(); // study ref: https://www.geeksforgeeks.org/random-vs-secure-random-numbers-java/
        byte[] salt = new byte[32]; // creating a 16 character equivalent salt
        r.nextBytes(salt);
        return salt;
//        return Base64.encodeBase64String(salt);
    }
    // https://stackoverflow.com/questions/10303767/encrypt-and-decrypt-in-java

    // Generate secretkey with user provided password, salt , hashing algorithm, hashing iteration
    // After generating secretkey store it in DB, Dont store password and salt in db
    public SecretKey generateSecretKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey tempSecret= SecretKeyFactory.getInstance(PASSWORD_HASHING_SECRET_KEY_ALOGORITHM).generateSecret(new PBEKeySpec(password.toCharArray(),generatePasswordSalt(),100,128));
        SecretKey secretKey = new SecretKeySpec(tempSecret.getEncoded(), "AES");
        return secretKey;
    }

    public String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
//        Cipher.getInstance("AES/CBC/PKCS5Padding").init(Cipher.ENCRYPT_MODE, secretKey);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        byte[] encryptedByte = cipherInstance.getCipher().doFinal(plainTextByte);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        return Base64.encodeBase64String(encryptedByte);
    }

    public String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        byte[] encryptedTextByte = Base64.decodeBase64(encryptedText);
//        Cipher.getInstance("AES/CBC/PKCS5Padding").init(Cipher.DECRYPT_MODE, secretKey);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//        byte[] decryptedByte = cipherInstance.getCipher().doFinal(encryptedTextByte);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        return new String(decryptedByte);
    }
}
