package com.post_hub.iam_service.utils;

import org.springframework.http.ResponseCookie;

import com.post_hub.iam_service.model.constans.ApiConstans;

public class ApiUtils {
    public static String getMethodName() {
        try {
            return Thread.currentThread().getStackTrace()[2].getMethodName();
        } catch (Exception cause) {
            return ApiConstans.UNDEFINED;
        }
    }

    public static String getCookie(String cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName)
                .httpOnly(true)
                .secure(false)
                .sameSite("lax")
                .maxAge(24 * 60 * 60)
                .path("/")
                .value(cookieValue)
                .build().toString();
    }
}
