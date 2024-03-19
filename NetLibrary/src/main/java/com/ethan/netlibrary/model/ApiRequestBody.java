package com.ethan.netlibrary.model;

import com.ethan.netlibrary.utils.EncryptUtils;
import com.ethan.netlibrary.utils.MD5Util;
import com.google.gson.Gson;

import java.util.Objects;

public class ApiRequestBody {


    private String action;
    private String token;
    private String requestId;
    private String digest;
    private String packageName;
    private Object data;


    private ApiRequestBody() {
    }

    public class ApiRequestBodyBuilder {
        private ApiRequestBodyBuilder() {
        }

        public ApiRequestBodyBuilder action(String action) {
            ApiRequestBody.this.action = action;
            return this;
        }
        public String getActionName(){
            return action;
        }

        public ApiRequestBodyBuilder token(String token) {
            ApiRequestBody.this.token = token;
            return this;
        }

        public ApiRequestBodyBuilder requestId(String requestId) {
            ApiRequestBody.this.requestId = requestId;
            return this;
        }

        public ApiRequestBodyBuilder data(Object data) {
            ApiRequestBody.this.data = data;
            return this;
        }

        public ApiRequestBodyBuilder packageName(String packageName) {
            ApiRequestBody.this.packageName = packageName;
            return this;
        }

        public ApiRequestBody build() {

            if (data == null) {
                data = "";
            }

            String content = String.format("%s%s%s", action, requestId, data2Json());

            digest = MD5Util.md5Hex(content);

            data = Objects.requireNonNull(EncryptUtils.encodeAESCompress(
                    MD5Util.md5Hex(String.format("%s-%s-%s", action, requestId, packageName)),
                    new Gson().toJson(data))).trim();

            return ApiRequestBody.this;

        }

    }

    public String toJsonString(Object body) {
        return new Gson().toJson(body);
    }

    public String data2Json() {
        if (data == null) {
            return "";
        }
        return toJsonString(data);
    }

    public String getRequestId(){
        return requestId;
    }

    public String toEncryptString(String key) {
        return MD5Util.md5Hex(packageName).toUpperCase()
                + EncryptUtils.encodeAESCompress(key, toJsonString(this));
    }

    public static ApiRequestBodyBuilder builder() {
        ApiRequestBody body = new ApiRequestBody();
        return body.new ApiRequestBodyBuilder();
    }

}