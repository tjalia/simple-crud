package com.tjalia.userprofile.util;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeaderUtil {

    public static HttpHeaders getContentTypeHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    public static HttpHeaders getClientIdOnlyHeaders() {
        HttpHeaders httpHeaders = getContentTypeHeaders();
        httpHeaders.set("x-client-id", "XCLIENTID");
        return httpHeaders;
    }

    public static HttpHeaders getClientSecretOnlyHeaders() {
        HttpHeaders httpHeaders = getContentTypeHeaders();
        httpHeaders.set("x-client-secret", "XCLIENTSECRET");
        return httpHeaders;
    }

    public static HttpHeaders getClientCredentialHeaders() {
        HttpHeaders httpHeaders = getContentTypeHeaders();
        httpHeaders.set("x-client-id", "XCLIENTID");
        httpHeaders.set("x-client-secret", "XCLIENTSECRET");
        return httpHeaders;
    }

    public static HttpHeaders getInvalidClientCredentialHeaders() {
        HttpHeaders httpHeaders = getContentTypeHeaders();
        httpHeaders.set("x-client-id", "IAMINVALIDID");
        httpHeaders.set("x-client-secret", "IAMINVALIDSECRET");
        return httpHeaders;
    }
}
