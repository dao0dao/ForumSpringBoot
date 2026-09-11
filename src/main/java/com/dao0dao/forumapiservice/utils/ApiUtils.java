package com.dao0dao.forumapiservice.utils;

import org.springframework.http.ResponseCookie;

import com.dao0dao.forumapiservice.model.constans.ApiConstans;

import lombok.NonNull;


public class ApiUtils {
    public static String getMethodName() {
        try {
            return Thread.currentThread().getStackTrace()[2].getMethodName();
        } catch (Exception cause) {
            return ApiConstans.UNDEFINED;
        }
    }

    public static String createCookie(@NonNull String cookieName, String cookieValue) {
        Integer maxAge = cookieValue.isEmpty() ? 0 : 24 * 60 * 60;
        return ResponseCookie.from(cookieName)
                .httpOnly(true)
                .secure(false)
                .sameSite("lax")
                .maxAge(maxAge)
                .path("/")
                .value(cookieValue)
                .build().toString();
    }

    public static String deleteCookie(String cookieName){
        return  ApiUtils.createCookie(cookieName, "");
    }
}
