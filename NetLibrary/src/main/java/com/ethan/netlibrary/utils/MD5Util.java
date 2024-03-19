package com.ethan.netlibrary.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {

    public static String md5Hex(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder(new BigInteger(1, md.digest()).toString(16));

            while (result.length() < 32) {
                result.insert(0, "0");
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}