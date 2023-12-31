package com.example.deliveryservice_courseproject.Other;

import java.math.BigInteger;
import java.security.MessageDigest;


// SHA-256
public class HashCoder {

    public static String toHash(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}
