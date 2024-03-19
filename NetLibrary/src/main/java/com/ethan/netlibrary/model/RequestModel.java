package com.ethan.netlibrary.model;


import static com.ethan.netlibrary.utils.UtilsKt.getCacheToken;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

public class RequestModel {

    private final String action;
    private final String requestId;
    private final String digest;
    private final Map<String, Object> data;
    private final String packageName;
    private final String token;

    public RequestModel(String action, KeyModel data, String pkgName) {
        this.requestId = String.valueOf(System.currentTimeMillis());
        this.action = action;
        this.digest = digest(action, requestId, data);
        this.data = data.getMap();
        this.packageName = pkgName;
        this.token = getCacheToken();
    }

    public String getToken() {
        return token;
    }

    public String getAction() {
        return action;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getDigest() {
        return digest;
    }

    public String getPackageName() {
        return packageName;
    }

    public Map<String, Object> getData() {
        return data;
    }

    private String digest(String action, String requestId, KeyModel data) {
        String content = String.format("%s%s%s", action, requestId, data == null ? new Gson().toJson(KeyModel.getEmpty()) : new Gson().toJson(data.getMap()));
        return md5Hex(content);
    }


    private String md5Hex(String content) {
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

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
