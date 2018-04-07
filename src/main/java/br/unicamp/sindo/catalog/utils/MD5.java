package br.unicamp.sindo.catalog.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private final static MessageDigest m;

    static {
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(String seed) {
        m.update(seed.getBytes(), 0, seed.length());
        return new BigInteger(1, m.digest()).toString(16);
    }
}
