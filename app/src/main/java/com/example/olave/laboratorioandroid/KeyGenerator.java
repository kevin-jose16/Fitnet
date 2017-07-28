package com.example.olave.laboratorioandroid;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by prog on 12/06/2017.
 */

public class KeyGenerator {
    public KeyGenerator() {
    }

    public static String getSecurePassword(String username, String password) {
        String key = null;

        try {
            MessageDigest e = MessageDigest.getInstance("SHA-256");
            e.update(password.getBytes());
            byte[] bytes = e.digest(username.getBytes());
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 255) + 256, 16).substring(1));
            }

            key = sb.toString();
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
        }

        return key;
    }
}
