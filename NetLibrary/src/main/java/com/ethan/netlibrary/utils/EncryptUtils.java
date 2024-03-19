
package com.ethan.netlibrary.utils;

import android.util.Base64;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//加解密工具类
public class EncryptUtils {

    public static String encodeAES256(String encodeRules, String content) {
        return encodeAES(256, encodeRules, content);
    }

    public static String decodeAES256(String encodeRules, String content) {
        return decodeAES(256, encodeRules, content);
    }

    private static String encodeAES(int keySize, String encodeRules, String content) {
        try {
            SecretKey key = new SecretKeySpec(hex(encodeRules), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
            byte[] byteAES = cipher.doFinal(byteEncode);
            return Base64.encodeToString(byteAES, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeAESCompress(String encodeRules, String content) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        DeflaterOutputStream compressOutput = new DeflaterOutputStream(output, new Deflater(9, true));

        try {
            SecretKey key = new SecretKeySpec(hex(encodeRules), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteEncode = content.getBytes("utf-8");

            compressOutput.write(byteEncode);

            compressOutput.finish();

            byteEncode = output.toByteArray();

            byte[] byteAES = cipher.doFinal(byteEncode);

            return Base64.encodeToString(byteAES, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                compressOutput.close();
                output.close();
            } catch (IOException e) {
            }
        }
        return null;
    }


    public static String decodeAESCompress(String actioname,String encodeRules, String content) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        InflaterOutputStream compressOutput = new InflaterOutputStream(output, new Inflater(true));

        try {
            SecretKey key = new SecretKeySpec(hex(encodeRules), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byteContent = Base64.decode(content, Base64.NO_WRAP);
            byteContent = cipher.doFinal(byteContent);
            compressOutput.write(byteContent);
            compressOutput.finish();

            return output.toString("utf-8");
        } catch (Exception e) {
            ILogUtils.e("decodeAESCompress-Error-For=="+actioname,ILogUtilsKt.ETAG);
            e.printStackTrace();
        } finally {
            try {
                compressOutput.close();
                output.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    private static String decodeAES(int keySize, String encodeRules, String content) {
        try {
            SecretKey key = new SecretKeySpec(hex(encodeRules), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byteContent = Base64.decode(content, Base64.NO_WRAP);
            return new String(cipher.doFinal(byteContent), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成十六进制字符串
     *
     * @param key
     * @return
     */
    public static byte[] hex(String key) {

        try {
            byte[] secretBytes = MessageDigest.getInstance("md5").digest(key.getBytes());

            StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code.insert(0, "0");
            }

            byte[] bkeys = md5code.toString().getBytes();
            byte[] enk = new byte[24];
            System.arraycopy(bkeys, 0, enk, 0, 24);
            return enk;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}